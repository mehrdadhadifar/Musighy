package com.hfad.musighy.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hfad.musighy.controller.fragment.AlbumFragment;
import com.hfad.musighy.controller.fragment.ArtistFragment;
import com.hfad.musighy.R;
import com.hfad.musighy.controller.fragment.SongFragment;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicRepository;

import java.util.ArrayList;
import java.util.List;

public class MusicPagerActivity extends AppCompatActivity {
    public static final int PERMISSION_REQUEST_CODE = 1;
    private MusicRepository mRepository;
    private TabLayout mMusicTabLayout;
    private ViewPager2 mMusicViewPager;
    private MusicViewPagerAdapter mAdapter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MusicPagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_pager);
        mRepository=MusicRepository.getInstance(this);
        findAllViews();
        handelPermission();
    }

    private void handelPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "WELCOME", Toast.LENGTH_LONG).show();
            updateUI();
        }
    }

    private void updateUI() {
        mRepository.getMusicList();
        mAdapter = new MusicViewPagerAdapter(this);
        mAdapter.addFragments(SongFragment.newInstance(null,null));
        mAdapter.addFragments(AlbumFragment.newInstance());
        mAdapter.addFragments(ArtistFragment.newInstance());
        mMusicViewPager.setAdapter(mAdapter);
        new TabLayoutMediator(mMusicTabLayout, mMusicViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0)
                    tab.setText("Songs");
                else if (position == 1)
                    tab.setText("Albums");
                else if (position == 2)
                    tab.setText("Artists");
            }
        }).attach();
    }


    private void findAllViews() {
        mMusicTabLayout = findViewById(R.id.tab_layout_music);
        mMusicViewPager = findViewById(R.id.view_pager_music);
    }

    public class MusicViewPagerAdapter extends FragmentStateAdapter {
        private List<Fragment> mFragmentList;

        public MusicViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            mFragmentList = new ArrayList<>();
        }

        public void addFragments(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateUI();
            } else {
                finish();
            }
        }
    }
}