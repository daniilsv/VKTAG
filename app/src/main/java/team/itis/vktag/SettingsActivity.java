package team.itis.vktag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.itis.vktag.data.ApiResponse;
import team.itis.vktag.data.App;
import team.itis.vktag.data.Tag;
import team.itis.vktag.data.TagVKApi;

public class SettingsActivity extends AppCompatActivity implements Callback<ApiResponse> {
    static String hash, title, action, url;
    String[] types = {"friend_add", "group_join", "like", "repost", "open_photo", "open_wall", "open_market"};
    EditText editTitle;
    EditText editUrl;
    Spinner spinner;
    TagVKApi tagApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tagApi = App.Companion.getTagApi();
        if (tagApi != null) {
            hash = getIntent().getStringExtra("hash");
            tagApi.getTag(hash).enqueue(this);
        }
        editTitle = (EditText) findViewById(R.id.edit_title);
        editUrl = (EditText) findViewById(R.id.edit_url);
        spinner = (Spinner) findViewById(R.id.spinner_type);
        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = editTitle.getText().toString();
                url = editUrl.getText().toString();
                if (tagApi != null)
                    tagApi.updateTag(hash, title, action, url).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                            Toast.makeText(getBaseContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {

                        }
                    });
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setPrompt("Type");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                action = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    private void setData() {
        editTitle.setText(title);
        editUrl.setText(url);
        spinner.setSelection(new ArrayList<>(Arrays.asList(types)).indexOf(action));
    }

    @Override
    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
        if (response == null || response.body() == null)
            return;
        ApiResponse res = response.body();
        Tag tag = res.getTag();
        if (tag == null)
            return;
        title = tag.getTitle();
        action = tag.getType();
        url = (String) tag.getData();
        setData();
    }

    @Override
    public void onFailure(Call<ApiResponse> call, Throwable t) {
        System.out.println(toString());
    }

}
