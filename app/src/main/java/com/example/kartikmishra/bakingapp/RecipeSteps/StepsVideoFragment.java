package com.example.kartikmishra.bakingapp.RecipeSteps;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kartikmishra.bakingapp.R;
import com.example.kartikmishra.bakingapp.RecipeDetails.RecipeDetailFragmentMasterList;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsVideoFragment extends Fragment {

    private static final String TAG = "StepsVideoFragment";
    @BindView(R.id.steps_description_tv) TextView description;
    @BindView(R.id.simpleExoPlayerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.nextStepButton) Button nextStepBtn;
    @BindView(R.id.prevStepBtn) Button prevStepBtn;
    private SimpleExoPlayer mExoPlayer;
    private  int steps_number;
    private int videoUrlsListSize;
    private long position;
    private int resumeWindow;

    String videoUrl;
    private int i;


    public StepsVideoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null && savedInstanceState.containsKey("POSITION")&&
                savedInstanceState.containsKey("resumeWindow")){
            position = savedInstanceState.getLong("POSITION",C.TIME_UNSET);
            resumeWindow = savedInstanceState.getInt("resumeWindow");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps_video_fragment, container, false);

        ButterKnife.bind(this,view);
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
         steps_number = intent.getIntExtra("steps_item_position", 0);

        Log.d(TAG, "onCreateView: step size:"+RecipeDetailFragmentMasterList.videoURLs.size());
        videoUrlsListSize = RecipeDetailFragmentMasterList.videoURLs.size();

        videoUrlsListSize = videoUrlsListSize-1;



        if (steps_number == 0) {
            getActivity().setTitle("Introduction to Recipe");
        } else {

            getActivity().setTitle(RecipeDetailFragmentMasterList.shortDescription.get(steps_number));
        }
        i = steps_number;


        Log.d(TAG, "onCreateView: short:");


        description.setText(RecipeDetailFragmentMasterList.description.get(steps_number));


        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),R.drawable.questionmarktwo));


        if(RecipeDetailFragmentMasterList.recipe_number==0 && steps_number==5){
            initializePlayer(RecipeDetailFragmentMasterList.thumbnailURLs.get(5));
        }


        if(RecipeDetailFragmentMasterList.videoURLs.get(steps_number)!=null ){
            initializePlayer(RecipeDetailFragmentMasterList.videoURLs.get(steps_number));
        }



        setUpNextBtn();
        setUpPreviousBtn();


        return view;
    }


    /**
     * Initializing the exoPlayer in this method
     * @param url
     */
    private void initializePlayer(String url) {

        if(mExoPlayer==null){
            TrackSelector selector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),selector,loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(),"BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                    new DefaultDataSourceFactory(Objects.requireNonNull(getContext()),userAgent),
                    new DefaultExtractorsFactory(),null,null);

            resumeExoPlayer();
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Function to Release the exoPlayer
     */
    private void releasePlayer() {
        if(mExoPlayer!=null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer=null;
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation ==Configuration.ORIENTATION_LANDSCAPE){

            View decorView = Objects.requireNonNull(getActivity()).getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    // Hide the nav bar and status bar
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);

            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            nextStepBtn.setVisibility(View.GONE);
            prevStepBtn.setVisibility(View.GONE);
        }
        else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            nextStepBtn.setVisibility(View.VISIBLE);
            prevStepBtn.setVisibility(View.VISIBLE);
            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        resumeWindow = mPlayerView.getPlayer().getCurrentWindowIndex();
        position =Math.max(0,mPlayerView.getPlayer().getContentPosition());
        releasePlayer();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("POSITION",position);
        outState.putInt("resumeWindow",resumeWindow);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mExoPlayer==null){
            initializePlayer(RecipeDetailFragmentMasterList.videoURLs.get(steps_number));
        }
        else {
            resumeExoPlayer();
        }
    }

    /**
     * Method to resume the exoplayer  whenever the orientation changes
     */
    private void resumeExoPlayer() {
        boolean haveResumeposition = resumeWindow!=C.INDEX_UNSET;
        if(haveResumeposition){
            mPlayerView.getPlayer().seekTo(resumeWindow,position);
        }
    }


    /**
     * Setting up skip to next video button here;
     */

    public void  setUpNextBtn(){
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i+1<RecipeDetailFragmentMasterList.shortDescription.size()){
                    releasePlayer();
                    i++;

                    if(RecipeDetailFragmentMasterList.recipe_number==0 && steps_number==5){
                        initializePlayer(RecipeDetailFragmentMasterList.thumbnailURLs.get(5));
                    }
                    getActivity().setTitle(RecipeDetailFragmentMasterList.shortDescription.get(i));
                    description.setText(RecipeDetailFragmentMasterList.description.get(i));
                    initializePlayer(RecipeDetailFragmentMasterList.videoURLs.get(i));
                }
                else {
                    Toast.makeText(getContext(), "No More steps!", Toast.LENGTH_SHORT).show();

                }



            }
        });
    }

    /**
     * Setting u previous button here to go back to previous step
     */
    public void setUpPreviousBtn(){


        prevStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i-1>0){
                    releasePlayer();
                    i--;

                    getActivity().setTitle(RecipeDetailFragmentMasterList.shortDescription.get(i));
                    description.setText(RecipeDetailFragmentMasterList.description.get(i));
                    initializePlayer(RecipeDetailFragmentMasterList.videoURLs.get(i));
                }
                else {
                    Toast.makeText(getContext(), "This is the first step!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
