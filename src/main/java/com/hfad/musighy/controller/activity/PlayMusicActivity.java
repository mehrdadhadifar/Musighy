package com.hfad.musighy.controller.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.hfad.musighy.controller.fragment.PlayMusicFragment;

public class PlayMusicActivity extends SingleFragmentActivity {

    private static final String EXTRA_MUSIC_POSITION = "MUSIC_POSITION";
    public static final String EXTRA_ALBUM_NAME = "ALBUM_NAME";
    public static final String EXTRA_ARTIST_NAME = "ARTIST_NAME";

    public static Intent newIntent(Context context, int position, String album, String artist) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        intent.putExtra(EXTRA_MUSIC_POSITION, position);
        intent.putExtra(EXTRA_ALBUM_NAME, album);
        intent.putExtra(EXTRA_ARTIST_NAME, artist);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        int position = getIntent().getIntExtra(EXTRA_MUSIC_POSITION, -1);
        return PlayMusicFragment.newInstance(position, getIntent().getStringExtra(EXTRA_ALBUM_NAME)
                , getIntent().getStringExtra(EXTRA_ARTIST_NAME));
    }

}