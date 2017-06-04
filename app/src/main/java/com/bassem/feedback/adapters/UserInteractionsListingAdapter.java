package com.bassem.feedback.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bassem.feedback.R;
import com.bassem.feedback.models.LastInteractionInfoItem;
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

    public UserInteractionsListingAdapter(List<LastInteractionInfoItem> items, Context context) {
        this.mDataset = items;
        mDurationTextHelper = new DurationTextHelper(context);
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
