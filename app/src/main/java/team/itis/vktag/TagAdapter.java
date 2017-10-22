package team.itis.vktag;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        ((TextView) layout.findViewById(R.id.type)).setText(tag.getType());
        CardView cardView = layout.findViewById(R.id.tagCard);
        switch (tag.getType()) {
            case "friend_add":
                cardView.setBackgroundResource(R.color.colorPrimary);
                break;
            case "group_join":
                cardView.setBackgroundResource(R.color.black);
                break;
            case "like":
                cardView.setBackgroundResource(R.color.green);
                break;
            case "repost":
                cardView.setBackgroundResource(R.color.orange);
                break;
            case "open_photo":
                cardView.setBackgroundResource(R.color.red);
                break;
            case "open_wall":
                cardView.setBackgroundResource(R.color.yellow);
                break;
            case "open_market":
                cardView.setBackgroundResource(R.color.colorAccent);
                break;
            default:

                break;
        }
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