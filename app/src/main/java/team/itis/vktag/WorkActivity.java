package team.itis.vktag;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.itis.vktag.data.ApiResponse;
import team.itis.vktag.data.App;
import team.itis.vktag.data.Tag;
import team.itis.vktag.data.TagVKApi;

public class WorkActivity extends AppCompatActivity implements Callback<ApiResponse> {

    TagAdapter adapter;
    ArrayList<Tag> items = new ArrayList<>();
    TextViewPlus tagsCount;
    VerticalViewPager pagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        adapter = new TagAdapter(this, items, R.layout.tag_card);
        pagerView = (VerticalViewPager) findViewById(R.id.pager);
        pagerView.setAdapter(new TagAdapter(this, items, R.layout.tag_card));
        tagsCount = (TextViewPlus) findViewById(R.id.tags_count);
        pagerView.setPageTransformer(true, new DepthPageTransformer());
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
        pagerView.setAdapter(new TagAdapter(this, items, R.layout.tag_card));
        pagerView.destroyDrawingCache();
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

    class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationY(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationY(pageHeight * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
