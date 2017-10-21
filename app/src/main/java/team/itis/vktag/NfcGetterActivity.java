package team.itis.vktag;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class NfcGetterActivity extends AppCompatActivity {
    NfcAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = NfcAdapter.getDefaultAdapter(this);
        if (adapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(getIntent().getAction());

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction()) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            performTagOperations(getIntent());
        }
    }

    private void performTagOperations(Intent intent) {
        Parcelable[] msgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefRecord firstRecord = ((NdefMessage) msgs[0]).getRecords()[0];

        String tagData = new String(firstRecord.getPayload());
        Toast.makeText(this, tagData, Toast.LENGTH_LONG).show();
        Intent serviceIntent = new Intent(NfcGetterActivity.this, NFCService.class);
        serviceIntent.setAction("start");
        serviceIntent.putExtra("tagdata", tagData);
        startService(serviceIntent);
        finish();
    }

}
