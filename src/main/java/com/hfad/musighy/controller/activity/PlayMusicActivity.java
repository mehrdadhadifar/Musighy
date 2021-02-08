package com.hfad.musighy.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hfad.musighy.controller.fragment.PlayMusicFragment;

public class PlayMusicActivity extends SingleFragmentActivity {

    private static final String EXTRA_MUSIC_POSITION = "MUSIC_POSITION";

    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        intent.putExtra(EXTRA_MUSIC_POSITION, position);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        int position = getIntent().getIntExtra(EXTRA_MUSIC_POSITION,-1);
        return PlayMusicFragment.newInstance(position);
    }

}