package com.hfad.musighy.controller.fragment;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hfad.musighy.R;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicRepository;

import java.util.ArrayList;

public class PlayMusicFragment extends Fragment {
    public static final String ARGS_MUSIC_POSITION = "ARGS_MUSIC_POSITION";
    public static final String TAG = "PMF";
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
    private Uri mUri;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private Thread playThread;
    private Thread previousThread;
    private Thread nextThread;

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
        mMusicList = new MusicRepository(getActivity()).getMusicList();
        mPosition = getArguments().getInt(ARGS_MUSIC_POSITION);
    }

    @Override
    public void onResume() {
        super.onResume();
//        playThread();
//        nextThread();
//        previousThread();
    }

    /*  private void previousThread() {
          previousThread = new Thread() {
              @Override
              public void run() {
                  super.run();
                  mImageViewPrevious.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if (mMediaPlayer.isPlaying()) {
                              mButtonPlay.setImageResource(R.drawable.ic_play_arrow_white_18dp);
                              mMediaPlayer.pause();
                              manageSeekBar();
                          } else {
                              mButtonPlay.setImageResource(R.drawable.ic_pause_white_18dp);
                              mMediaPlayer.start();
                              manageSeekBar();
                          }
                      }
                  });
              }
          };
          previousThread.start();
      }

      private void nextThread() {
          nextThread = new Thread() {
              @Override
              public void run() {
                  super.run();
                  mImageViewNext.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if (mMediaPlayer.isPlaying()) {
                              mButtonPlay.setImageResource(R.drawable.ic_play_arrow_white_18dp);
                              mMediaPlayer.pause();
                              manageSeekBar();
                          } else {
                              mButtonPlay.setImageResource(R.drawable.ic_pause_white_18dp);
                              mMediaPlayer.start();
                              manageSeekBar();
                          }
                      }
                  });
              }
          };
          nextThread.start();
      }

      private void playThread() {
          playThread = new Thread() {
              @Override
              public void run() {
                  super.run();
                  mButtonPlay.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if (mMediaPlayer.isPlaying()) {
                              mButtonPlay.setImageResource(R.drawable.ic_play_arrow_white_18dp);
                              mMediaPlayer.pause();
                              manageSeekBar();
                          } else {
                              mButtonPlay.setImageResource(R.drawable.ic_pause_white_18dp);
                              mMediaPlayer.start();
                              manageSeekBar();
                          }
                      }
                  });
              }
          };
          playThread.start();
      }
  */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        findAllViews(view);
        updateUI();
        setOnClickListeners();
        return view;
    }


    private void setOnClickListeners() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer.seekTo(progress * 1000);
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
                if (mMediaPlayer.isPlaying()) {
                    mButtonPlay.setImageResource(R.drawable.ic_play_arrow_white_18dp);
                    mMediaPlayer.pause();
                    manageSeekBar();
                } else {
                    mButtonPlay.setImageResource(R.drawable.ic_pause_white_18dp);
                    mMediaPlayer.start();
                    manageSeekBar();
                }
            }
        });
        mImageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = (mPosition + 1) % mMusicList.size();
                updateUI();
            }
        });
        mImageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition == 0)
                    return;
                mPosition = (mPosition - 1) % mMusicList.size();
                updateUI();
            }
        });
    }

    private void updateUI() {
        if (mMusicList != null) {
            mButtonPlay.setImageResource(R.drawable.ic_pause_white_18dp);
            mUri = Uri.parse(mMusicList.get(mPosition).getPath());
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            Log.d(TAG, "media player not null");
        }
        mMediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), mUri);
        mMediaPlayer.start();
        Log.d(TAG, "media player is null");
        mTextViewName.setText(mMusicList.get(mPosition).getTitle());
        mTextViewArtist.setText(mMusicList.get(mPosition).getArtist());
        mImageViewCover.setImageDrawable(new MusicRepository(getActivity()).getAlbumArt(mMusicList.get(mPosition)));
        manageSeekBar();
    }


    private void manageSeekBar() {
        mSeekBar.setMax(mMediaPlayer.getDuration() / 1000);
        mTextViewDurationTotal.setText(getTime(mMediaPlayer.getDuration() / 1000));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer != null) {
                    int mCurrentPosition = mMediaPlayer.getCurrentPosition() / 1000;
                    mSeekBar.setProgress(mCurrentPosition);
                    mTextViewDurationPlayed.setText(getTime(mCurrentPosition));
                }
                mHandler.postDelayed(this, 1000);
            }
        });
    }

    private String getTime(int mCurrentPosition) {
        String result = "";
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