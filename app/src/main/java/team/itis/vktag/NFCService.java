package team.itis.vktag;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;


public class NFCService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String tagdata = intent.getStringExtra("tagdata");
        Toast.makeText(this, tagdata, Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
