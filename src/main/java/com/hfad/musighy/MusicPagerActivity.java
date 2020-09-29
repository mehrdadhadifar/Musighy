package com.hfad.musighy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MusicPagerActivity extends AppCompatActivity {
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
        findAllViews();
        updateUI();
    }

    private void updateUI() {
        mAdapter = new MusicViewPagerAdapter(this);
        mAdapter.addFragments(SongFragment.newInstance());
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
        private List<String> mTitles;

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
}