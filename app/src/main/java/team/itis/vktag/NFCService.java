package team.itis.vktag;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.vk.sdk.VKSdk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.itis.vktag.data.ApiResponse;
import team.itis.vktag.data.App;
import team.itis.vktag.data.Tag;
import team.itis.vktag.data.TagVKApi;


public class NFCService extends Service implements Callback<ApiResponse> {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        VKSdk.customInitialize(this, 6226949, "5.68");
        String tagdata = intent.getStringExtra("tagdata");
        System.out.println(tagdata);
        TagVKApi tagApi = App.Companion.getTagApi();
        if (tagApi != null)
            tagApi.getTag(tagdata).enqueue(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
        if (response == null || response.body() == null)
            return;
        ApiResponse res = response.body();
        Tag tag = res.getTag();
        if (tag == null)
            return;
        if (tag.getHash() == null) {
            //TODO:СОЗДАНИЕ МЕТКИ
            return;
        }
        processTag(tag);
    }

    private void processTag(Tag tag) {
        if (tag.getType() == null || tag.getData() == null) {
            Toast.makeText(this, "bad tag", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!VKSdk.wakeUpSession(this)) {
            Toast.makeText(this, "bad token", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (tag.getType()) {
            case "friend_add":
                VKApiMethods.addFriend((String) tag.getData());
                Toast.makeText(this, "friend_add " + tag.getData(), Toast.LENGTH_SHORT).show();
                break;
            case "group_join":
                VKApiMethods.joinGroup((String) tag.getData());
                Toast.makeText(this, "group_join " + tag.getData(), Toast.LENGTH_SHORT).show();
                break;
            case "like_repost":
                //VKApiMethods.joinGroup((String) tag.getData());
                Toast.makeText(this, "like_repost " + tag.getData(), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, tag.getType(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onFailure(Call<ApiResponse> call, Throwable t) {
        System.out.println(toString());
    }
}
