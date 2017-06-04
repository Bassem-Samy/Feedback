package com.bassem.feedback.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bassem.feedback.R;
import com.bassem.feedback.models.LastInteractionInfoItem;
import com.bassem.feedback.utils.Constants;
import com.bassem.feedback.utils.DurationTextHelper;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bassem Samy on 6/4/2017.
 */

public class UserInteractionsListingAdapter extends RecyclerView.Adapter<UserInteractionsListingAdapter.ViewHolder> {

    private List<LastInteractionInfoItem> mDataset;
    private DurationTextHelper mDurationTextHelper;
    int severColor;
    int mediumSeverityColor;
    int normalSeverityColor;

    public UserInteractionsListingAdapter(List<LastInteractionInfoItem> items, Context context) {
        this.mDataset = items;
        mDurationTextHelper = new DurationTextHelper(context);
        severColor = ContextCompat.getColor(context, Constants.SEVER_COLOR_RESOURCE_ID);
        mediumSeverityColor = ContextCompat.getColor(context, Constants.MEDIUM_SEVERITY_COLOR_RESOURCE_ID);
        normalSeverityColor = ContextCompat.getColor(context, Constants.NORMAL_SEVERITY_COLOR_RESOURCE_ID);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mDataset.get(position).isPrepared()) {
            mDataset.get(position).prepareTimeDifference(new DateTime());
        }
        holder.feedbackSentTextView.setText(mDurationTextHelper.getLastInteractionDuration(mDataset.get(position)));
        switch (mDataset.get(position).getSeverityType()) {
            case MEDIUM: {
                holder.feedbackSentTextView.setTextColor(mediumSeverityColor);
                break;
            }
            case NORMAL: {
                holder.feedbackSentTextView.setTextColor(normalSeverityColor);
                break;
            }
            case SEVER: {
                holder.feedbackSentTextView.setTextColor(severColor);
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

    public void setDataset(List<LastInteractionInfoItem> lastInteractions) {
        this.mDataset = lastInteractions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_feedback_sent)
        TextView feedbackSentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
