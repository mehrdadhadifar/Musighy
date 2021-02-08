package com.hfad.musighy.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hfad.musighy.R;
import com.hfad.musighy.controller.fragment.AlbumDetailsFragment;

public class AlbumDetailsActivity extends SingleFragmentActivity {

    public static final String EXTRA_ALBUM_NAME = "ALBUM_NAME";

    public static Intent newIntent(Context context, String albumName) {
        Intent intent = new Intent(context, AlbumDetailsActivity.class);
        intent.putExtra(EXTRA_ALBUM_NAME, albumName);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return AlbumDetailsFragment.newInstance(getIntent().getStringExtra(EXTRA_ALBUM_NAME));
    }
}