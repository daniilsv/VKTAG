package team.itis.vktag;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.vk.sdk.VKSdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.itis.vktag.data.ApiResponse;
import team.itis.vktag.data.App;
import team.itis.vktag.data.Tag;
import team.itis.vktag.data.TagVKApi;


public class NFCService extends Service implements Callback<ApiResponse> {

    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";

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
        if (tag.getId() == 0) {
            //TODO:СОЗДАНИЕ МЕТКИ
            return;
        }
        processTag(tag);
    }

    private void processTag(Tag tag) {
        if (tag.getType() == null || tag.getData() == null) {
            return;
        }
        if (!VKSdk.wakeUpSession(this)) {
            return;
        }
        String url = ((String) tag.getData()).replaceAll("https://vk\\.com/(.*\\?.=)*(.+)", "$2");
        url = new ArrayList<>(Arrays.asList(url.split("%2F"))).get(0);
        switch (tag.getType()) {
            case "friend_add":
                VKApiMethods.addFriend((String) tag.getData());
                break;
            case "group_join":
                VKApiMethods.joinGroup((String) tag.getData());
                break;
            case "like":
                VKApiMethods.setLike(url);
                break;
            case "repost":
                VKApiMethods.repost(url);
                break;
            case "open_photo":
                openUrl("vkontakte://photo/" + url);
                break;
            case "open_wall":
                openUrl("vkontakte://wall/" + url);
                break;
            case "open_market":
                openUrl("vkontakte://market/" + url);
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

    public void openUrl(String url) {
        Uri address = Uri.parse(url);
        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
        List<ResolveInfo> resInfo = this.getPackageManager().queryIntentActivities(openlinkIntent, 0);

        if (resInfo.isEmpty()) return;

        for (ResolveInfo info : resInfo) {
            if (info.activityInfo == null) continue;
            if (VK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)) {
                openlinkIntent.setPackage(info.activityInfo.packageName);
                break;
            }
        }
        startActivity(openlinkIntent);
    }
}
