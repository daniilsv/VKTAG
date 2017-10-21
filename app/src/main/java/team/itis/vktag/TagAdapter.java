package team.itis.vktag;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import team.itis.vktag.data.Tag;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ItemViewHolder> {
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
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mRowResId, parent, false);
        return new ItemViewHolder(mActivity, view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.setItem(mItems.get(position));
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Activity mActivity;
        private Tag mItem;

        ItemViewHolder(Activity activity, View itemView) {
            super(itemView);
            mActivity = activity;
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(mActivity, ItemActivity.class);
//            intent.putExtra("item_type", mItem.getHash());
//            mActivity.startActivity(intent);
        }

        void setItem(Tag item) {
            mItem = item;
            itemView.setOnClickListener(this);
            ((TextViewPlus) itemView.findViewById(R.id.title)).setText(mItem.getTitle());
        }
    }
}