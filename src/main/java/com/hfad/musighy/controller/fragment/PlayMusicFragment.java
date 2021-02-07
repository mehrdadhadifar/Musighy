package com.hfad.musighy.controller.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hfad.musighy.R;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicRepository;

import java.util.ArrayList;
import java.util.Random;

public class PlayMusicFragment extends Fragment {
    public static final String ARGS_MUSIC_POSITION = "ARGS_MUSIC_POSITION";
    public static final String TAG = "Play Music Fragment";
    private MusicRepository mRepository;
    private TextView mTextViewName;
    private TextView mTextViewArtist;
    private TextView mTextViewDurationPlayed;
    private TextView mTextViewDurationTotal;
    private ImageView mImageViewCover;
    private ImageView mImageViewShuffle;
    private ImageView mImageViewPrevious;
    private ImageView mImageViewNext;
    private ImageView mImageViewRepeat;
    private ImageView mImageButtonBack;
    private FloatingActionButton mButtonPlay;
    private SeekBar mSeekBar;
    private int mPosition = -1;
    private ArrayList<Music> mMusicList;
    private Handler mHandler = new Handler();

    public PlayMusicFragment() {
        // Required empty public constructor
    }


    public static PlayMusicFragment newInstance(int position) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_MUSIC_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = MusicRepository.getInstance(getActivity());
        mMusicList = mRepository.getMusicList();
        mPosition = getArguments().getInt(ARGS_MUSIC_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        findAllViews(view);
        updateUI();
        return view;
    }


    private void setOnClickListeners() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mRepository.getMediaPlayer() != null && fromUser) {
                    mRepository.getMediaPlayer().seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRepository.getMediaPlayer().isPlaying()) {
                    mButtonPlay.setImageResource(R.drawable.ic_play_arrow_white_18dp);
                    mRepository.getMediaPlayer().pause();
                } else {
                    mButtonPlay.setImageResource(R.drawable.ic_pause_white_18dp);
                    mRepository.getMediaPlayer().start();
                }
                manageSeekBar();
            }
        });
        mImageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextMusic();
            }
        });
        mImageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition == 0)
                    return;
                if (!mRepository.isRepeatOne())
                    mPosition = (mPosition - 1) % mMusicList.size();
                updateUI();
            }
        });
        mRepository.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mButtonPlay.setImageResource(R.drawable.ic_play_arrow_white_18dp);
                playNextMusic();
            }
        });
        mImageViewShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRepository.isShuffle()) {
                    mImageViewShuffle.setImageResource(R.drawable.ic_shuffle_off_white_18dp);
                } else {
                    mImageViewShuffle.setImageResource(R.drawable.ic_shuffle_on_white_18dp);
                }
                mRepository.setShuffle(!mRepository.isShuffle());
            }
        });
        mImageViewRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRepository.isRepeatAll()) {
                    mRepository.setRepeatAll(false);
                    mRepository.setRepeatOne(true);
                    mImageViewRepeat.setImageResource(R.drawable.ic_repeat_one_on_white_18dp);
                } else if (mRepository.isRepeatOne()) {
                    mRepository.setRepeatAll(false);
                    mRepository.setRepeatOne(false);
                    mImageViewRepeat.setImageResource(R.drawable.ic_repeat_white_off_18dp);
                } else {
                    mRepository.setRepeatAll(true);
                    mRepository.setRepeatOne(false);
                    mImageViewRepeat.setImageResource(R.drawable.ic_repeat_white_on_18dp);
                }
            }
        });
        mImageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void playNextMusic() {
        if (!mRepository.isRepeatOne() && !mRepository.isShuffle()) {
            if ((mPosition + 1) % mMusicList.size() == 0 && !mRepository.isRepeatAll())
                return;
            mPosition = (mPosition + 1) % mMusicList.size();
        } else if (mRepository.isShuffle() && !mRepository.isRepeatOne()) {
            mPosition = new Random().nextInt(mMusicList.size() - 1);
            Log.d(TAG, "playNextMusic: " + mPosition);
        }
        updateUI();
    }

    private void updateUI() {
        mButtonPlay.setImageResource(R.drawable.ic_pause_white_18dp);
        mRepository.startMediaPlayer(mMusicList.get(mPosition).getMusicUri());
        mTextViewName.setText(mMusicList.get(mPosition).getTitle());
        mTextViewArtist.setText(mMusicList.get(mPosition).getArtist());
        if (mRepository.isShuffle())
            mImageViewShuffle.setImageResource(R.drawable.ic_shuffle_on_white_18dp);
        if (mRepository.isRepeatAll())
            mImageViewRepeat.setImageResource(R.drawable.ic_repeat_white_on_18dp);
        if (mRepository.isRepeatOne())
            mImageViewRepeat.setImageResource(R.drawable.ic_repeat_one_on_white_18dp);
        Glide.with(getActivity()).load(mMusicList.get(mPosition).getAlbumArtUri()).placeholder(R.drawable.ic_no_album_art).into(mImageViewCover);
        manageSeekBar();
        setOnClickListeners();
    }


    private void manageSeekBar() {
        mSeekBar.setMax(mRepository.getMediaPlayer().getDuration() / 1000);
        mTextViewDurationTotal.setText(getTime(mRepository.getMediaPlayer().getDuration() / 1000));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRepository.getMediaPlayer() != null) {
                    int mCurrentPosition = mRepository.getMediaPlayer().getCurrentPosition() / 1000;
                    mSeekBar.setProgress(mCurrentPosition);
                    mTextViewDurationPlayed.setText(getTime(mCurrentPosition));
                }
                mHandler.postDelayed(this, 1000);
            }
        });
    }

    private String getTime(int mCurrentPosition) {
        String result;
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        if (seconds.length() == 1)
            result = minutes + ":0" + seconds;
        else
            result = minutes + ":" + seconds;
        return result;
    }


    private void findAllViews(View view) {
        mTextViewName = view.findViewById(R.id.text_view_music_name);
        mTextViewArtist = view.findViewById(R.id.text_view_artist_name);
        mTextViewDurationPlayed = view.findViewById(R.id.duration_played);
        mTextViewDurationTotal = view.findViewById(R.id.duration_total);
        mImageViewCover = view.findViewById(R.id.image_view_main_player);
        mImageButtonBack = view.findViewById(R.id.image_button_back);
        mImageViewNext = view.findViewById(R.id.next_player);
        mImageViewPrevious = view.findViewById(R.id.previous_player);
        mImageViewRepeat = view.findViewById(R.id.repeat_player);
        mImageViewShuffle = view.findViewById(R.id.shuffle_player);
        mButtonPlay = view.findViewById(R.id.play_pause_player);
        mSeekBar = view.findViewById(R.id.seek_bar_player);
    }
}