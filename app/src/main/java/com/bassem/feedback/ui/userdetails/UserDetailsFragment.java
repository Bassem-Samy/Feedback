package com.bassem.feedback.ui.userdetails;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.style.UpdateLayout;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bassem.feedback.R;
import com.bassem.feedback.models.UserFeedbackInfoItem;
import com.bassem.feedback.models.datamodels.User;
import com.bassem.feedback.utils.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.app.ActivityCompat.postponeEnterTransition;
import static android.support.v4.app.ActivityCompat.startPostponedEnterTransition;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UserDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailsFragment extends Fragment {
    public static final String TAG = "user_details_fragment";
    private static final String ARG_USER = "user";
    private User mUser;

    @BindView(R.id.lnr_user_info)
    LinearLayout userInfoLinearLayout;
    @BindView(R.id.user_details_img)
    ImageView userImageView;
    @BindView(R.id.txt_user_name)
    TextView userNameTextView;
    @BindView(R.id.lnr_feedback_given)
    LinearLayout feedbackGivenLinearLayout;
    @BindView(R.id.txt_to_user_name)
    TextView toUserNameTextView;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user to display it's details
     * @return A new instance of fragment UserDetailsFragment.
     */
    public static UserDetailsFragment newInstance(User user) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARG_USER);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelable(ARG_USER) != null) {
                mUser = savedInstanceState.getParcelable(ARG_USER);
            }
        }
        populateData();
        userInfoLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                                                                 @Override
                                                                                 public void onGlobalLayout() {
                                                                                     userInfoLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                                                                     if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                                                                         startCircularReveal();
                                                                                     } else {
                                                                                         userInfoLinearLayout.setVisibility(View.VISIBLE);
                                                                                     }
                                                                                 }
                                                                             }
        );
    }

    private void populateData() {
        ImageLoader.loadImage(getContext(), mUser.getAvatar(), R.drawable.user, userImageView);
        userNameTextView.setText(mUser.getName());
        toUserNameTextView.setText(mUser.getName());

    }

    void startCircularReveal() {
        userInfoLinearLayout.setVisibility(View.VISIBLE);
        Animator circularReveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(
                    userInfoLinearLayout,
                    0,
                    0,
                    0,
                    (float) Math.hypot(userInfoLinearLayout.getWidth(), userInfoLinearLayout.getHeight()));

            circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());

            // Finally start the animation
            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    animateFeedbackGivenLinearLayout();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.start();
        }
    }

    private void animateFeedbackGivenLinearLayout() {
        feedbackGivenLinearLayout.setVisibility(View.VISIBLE);
        Animator circularReveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(
                    feedbackGivenLinearLayout,
                    0,
                    0,
                    0,
                    (float) Math.hypot(feedbackGivenLinearLayout.getWidth(), feedbackGivenLinearLayout.getHeight()));

            circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
            circularReveal.start();
        }
    }

    public void updateUserToDisplay(UserFeedbackInfoItem item) {
        this.mUser = item;
        populateData();
        startCircularReveal();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_USER, mUser);
    }
}
