package com.bassem.feedback.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.Date;
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
    static final String TRANSITION_NAME = "transition_";
    int severColor;
    int mediumSeverityColor;
    int normalSeverityColor;

    public UsersListingAdapter(List<UserFeedbackInfoItem> items, OnFeedbackInfoItemClick listener, Context context) {

        this.mDataset = items;
        this.mListener = listener;
        this.mContext = context;
        this.mDurationTextHelper = new DurationTextHelper(context);
        severColor = ContextCompat.getColor(context, Constants.SEVER_COLOR_RESOURCE_ID);
        mediumSeverityColor = ContextCompat.getColor(context, Constants.MEDIUM_SEVERITY_COLOR_RESOURCE_ID);
        normalSeverityColor = ContextCompat.getColor(context, Constants.NORMAL_SEVERITY_COLOR_RESOURCE_ID);
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
            initializeViewHolders(item, position, holder);
        }
    }

    private void initializeViewHolders(UserFeedbackInfoItem item, int position, RecyclerView.ViewHolder holder) {
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
                if (item.getLastFeedbackSent() == null || item.getLastFeedbackSent().isEmpty())
                    item.setLastFeedbackSent(mDurationTextHelper.getLastInteractionDuration(item.getlastInteractionItem()));
                viewHolder.timeDifferenceTextView.setText(item.getLastFeedbackSent());
                viewHolder.timeDifferenceTextView.setTextColor(severColor);
                if (item.getlastInteractionItem() != null) {
                    switch (item.getlastInteractionItem().getSeverityType()) {
                        case MEDIUM: {
                            viewHolder.timeDifferenceTextView.setTextColor(mediumSeverityColor);
                            break;
                        }
                        case NORMAL: {
                            viewHolder.timeDifferenceTextView.setTextColor(normalSeverityColor);
                            break;
                        }
                    }
                }

                break;
            }
            case SECTION: {
                SectionViewHolder viewHolder = (SectionViewHolder) holder;
                viewHolder.sectionTitleTextView.setText(item.getTitle());
                viewHolder.noItemsHereTextView.setVisibility(View.GONE);
                // checks if this section has no item then show empty message
                if (position == getItemCount() - 1) {
                    viewHolder.noItemsHereTextView.setVisibility(View.VISIBLE);
                } else if (position == 0) {
                    if (getItemCount() > 1) {
                        if (mDataset.get(1).getType() == UserFeedbackInfoItemType.SECTION) {
                            viewHolder.noItemsHereTextView.setVisibility(View.VISIBLE);
                        }
                    }
                }
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

    void interactWithItem(int position) {
        UserFeedbackInfoItem item = mDataset.get(position);
        mDataset.remove(item);
        notifyItemRemoved(position);
        item.updateInteraction(new Date());
        item.setLastFeedbackSent(mDurationTextHelper.getLastInteractionDuration(item.getlastInteractionItem()));
        mDataset.add(item);
        notifyItemInserted(mDataset.size() - 1);
        if (mListener != null) {
            mListener.onFeedbackGiven(item);
        }
    }

    public void setItems(List<UserFeedbackInfoItem> items) {
        mDataset = items;
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_section_title)
        TextView sectionTitleTextView;
        @BindView(R.id.txt_no_items_here)
        TextView noItemsHereTextView;

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
            interactWithItem(position);

        }

        @OnClick(R.id.lnr_user_info)
        void onUserInfoClicked() {
            int position = this.getAdapterPosition();
            Log.e("userid", mDataset.get(position).getId());
            if (mListener != null) {
                mListener.onUserClicked(position);
            }
        }
    }

    public UserFeedbackInfoItem getItemByPosition(int position) {
        if (mDataset != null && mDataset.size() > position) {
            return mDataset.get(position);
        }
        return null;
    }

    public interface OnFeedbackInfoItemClick {
        void onUserClicked(int position);

        void onFeedbackGiven(UserFeedbackInfoItem item);
    }

    public ArrayList<UserFeedbackInfoItem> getDataset() {
        return new ArrayList<>(mDataset);
    }
}
