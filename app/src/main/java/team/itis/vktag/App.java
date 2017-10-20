package team.itis.vktag;

import android.app.Application;

import com.vk.sdk.VKSdk;

public final class App extends Application {
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
