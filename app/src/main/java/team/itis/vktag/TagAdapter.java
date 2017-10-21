package team.itis.vktag;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import team.itis.vktag.data.Tag;

public class TagAdapter extends PagerAdapter {
    private final Activity mActivity;
    private ArrayList<Tag> mItems = new ArrayList<>();
    private int mRowResId;

    public TagAdapter(Activity activity, ArrayList<Tag> items, int rowResId) {
        mActivity = activity;
        mItems = items;
        mRowResId = rowResId;
    }


    public ArrayList<Tag> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<Tag> items) {
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        final Tag tag = mItems.get(position);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        ViewGroup layout = (ViewGroup) inflater.inflate(mRowResId, null);
        ((TextViewPlus) layout.findViewById(R.id.title)).setText(tag.getTitle());
        layout.setTranslationY(-1 * layout.getHeight() * position);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mActivity, SettingsActivity.class);
                i.putExtra("hash", tag.getHash());
                mActivity.startActivity(i);
            }
        });
        collection.addView(layout);
        return layout;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItems.get(position).getTitle();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}