package team.itis.vktag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.itis.vktag.data.ApiResponse;
import team.itis.vktag.data.App;
import team.itis.vktag.data.Tag;
import team.itis.vktag.data.TagVKApi;

public class WorkActivity extends AppCompatActivity implements Callback<ApiResponse> {

    TagAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Tag> items = new ArrayList<>();
    TextViewPlus tagsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        adapter = new TagAdapter(this, items, R.layout.tag_card);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tagsCount = (TextViewPlus) findViewById(R.id.tags_count);
    }

    @Override
    protected void onResume() {
        TagVKApi tagApi = App.Companion.getTagApi();
        if (tagApi != null)
            tagApi.getAdminTags().enqueue(this);
        super.onResume();
    }

    @Override
    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
        items = (ArrayList<Tag>) response.body().getTags();
        adapter.setItems(items);
        recyclerView.swapAdapter(adapter, false);
        int size = items.size();
        if (size == 0) {
            tagsCount.setText("you don't have any tags yet");
        } else {
            tagsCount.setText("you have " + size + " tag" + (size == 1 ? "" : "s"));
        }
    }

    @Override
    public void onFailure(Call<ApiResponse> call, Throwable t) {

    }
}
