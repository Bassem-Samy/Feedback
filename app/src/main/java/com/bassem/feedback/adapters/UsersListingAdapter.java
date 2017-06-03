package com.bassem.feedback.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bassem.feedback.R;
import com.bassem.feedback.models.UserFeedbackInfoItem;
import com.bassem.feedback.models.UserFeedbackInfoItemType;
import com.bassem.feedback.utils.Constants;
import com.bassem.feedback.utils.DurationTextHelper;
import com.bassem.feedback.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bassem Samy on 6/2/2017.
 */

public class UsersListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<UserFeedbackInfoItem> mDataset;
    OnFeedbackInfoItemClick mListener;
    Context mContext;
    DurationTextHelper mDurationTextHelper;


    public UsersListingAdapter(List<UserFeedbackInfoItem> items, OnFeedbackInfoItemClick listener, Context context) {
        this.mDataset = items;
        this.mListener = listener;
        this.mContext = context;
        this.mDurationTextHelper = new DurationTextHelper(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == UserFeedbackInfoItemType.RECORD.getValue()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_feedback_info_item, parent, false);
            return new RecordViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_section_item, parent, false);
            return new SectionViewHolder((view));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserFeedbackInfoItem item = mDataset.get(position);
        if (item != null) {
            initializeViewHolders(item, holder);
        }
    }

    private void initializeViewHolders(UserFeedbackInfoItem item, RecyclerView.ViewHolder holder) {
        switch (item.getType()) {
            case RECORD: {
                RecordViewHolder viewHolder = (RecordViewHolder) holder;
                viewHolder.userNameTextView.setText(item.getName());
                ImageLoader.loadImage(mContext, item.getAvatar(), R.drawable.user, ((RecordViewHolder) holder).userImageView);
                if (item.getLastFeedbackTimeDifference() < Constants.LAST_INTERACTION_THRESHOLD) {
                    viewHolder.giveFeedbackLinearLayout.setVisibility(View.GONE);
                } else {
                    viewHolder.giveFeedbackLinearLayout.setVisibility(View.VISIBLE);
                }
                if (item.getLastFeedbackSent() == null || item.getLastFeedbackSent().isEmpty()) {
                    //   item.setLastFeedbackSent(mDurationTextHelper.getDurationTextResourceId(item.getLastFeedbackTimeDifference()));
                    item.setLastFeedbackSent(mDurationTextHelper.getDurationTextResourceId(item));
                }
                viewHolder.timeDifferenceTextView.setText(item.getLastFeedbackSent());
                break;
            }
            case SECTION: {
                SectionViewHolder viewHolder = (SectionViewHolder) holder;
                viewHolder.sectionTitleTextView.setText(item.getTitle());
                break;
            }
        }
    }


    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position).getType().getValue();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_section_title)
        TextView sectionTitleTextView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_user)
        ImageView userImageView;
        @BindView(R.id.txt_time_difference)
        TextView timeDifferenceTextView;
        @BindView(R.id.txt_user_name)
        TextView userNameTextView;

        @BindView(R.id.lnr_give_feedback)
        LinearLayout giveFeedbackLinearLayout;

        public RecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.lnr_give_feedback)
        void onGiveFeedbackClicked() {
            int position = this.getAdapterPosition();
            Log.e("FeedbackName", mDataset.get(position).getName());
        }

        @OnClick(R.id.lnr_user_info)
        void onUserInfoClicked() {
            int position = this.getAdapterPosition();
            Log.e("userid", mDataset.get(position).getId());
        }
    }

    public interface OnFeedbackInfoItemClick {
        void onUserClicked();

        void onInteractClicked();
    }
}
