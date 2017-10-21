package team.itis.vktag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!VKSdk.wakeUpSession(this)) {
            VKSdk.login(this, "likes", "friends", "wall", "groups");
        }
    }

    @Override
    protected void onResume() {

        if (VKSdk.wakeUpSession(this)) {

            Intent intent = new Intent(MainActivity.this, WorkActivity.class);
            startActivity(intent);
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                System.out.println("Success!!!!");
                sPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("userId", res.userId);
                ed.apply();
                Intent intent = new Intent(MainActivity.this, WorkActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                System.out.println("Fail!!!!");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void loginVk(View v) {
        VKSdk.login(this, "likes", "friends", "wall", "groups");
    }

    public void openUrl(String url) {
        Uri address = Uri.parse(url);
        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openlinkIntent);
    }


}
