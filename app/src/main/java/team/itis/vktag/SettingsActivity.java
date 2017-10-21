package team.itis.vktag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.itis.vktag.data.ApiResponse;
import team.itis.vktag.data.App;
import team.itis.vktag.data.Tag;
import team.itis.vktag.data.TagVKApi;

public class SettingsActivity extends AppCompatActivity implements Callback<ApiResponse> {
    static String hash, name, action, url;
    String[] types = {"Add Friends", "Like", "Repost", "Join group", "Open profile", "Open post", "Join group"};
    EditText editName;
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
        editName = (EditText) findViewById(R.id.edit_name);
        editUrl = (EditText) findViewById(R.id.edit_url);
        spinner = (Spinner) findViewById(R.id.spinner_type);
        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tagApi != null)
                    tagApi.updateTag(hash, name, action, url).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            Toast.makeText(getBaseContext(), "Changed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {

                        }
                    });
            }
        });

        editName.setText(name);
        editUrl.setText(url);


        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Type");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                action = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    @Override
    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
        if (response == null || response.body() == null)
            return;
        ApiResponse res = response.body();
        Tag tag = res.getTag();
        if (tag == null)
            return;
        name = tag.getTitle();
        action = tag.getType();
        url = (String) tag.getData();
    }

    @Override
    public void onFailure(Call<ApiResponse> call, Throwable t) {
        System.out.println(toString());
    }

}
