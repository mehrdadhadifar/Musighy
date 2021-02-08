package com.hfad.musighy.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.musighy.R;
import com.hfad.musighy.adapter.MusicAdapter;
import com.hfad.musighy.controller.activity.PlayMusicActivity;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicRepository;

import java.util.List;


public class SongFragment extends Fragment implements MusicAdapter.MusicClicked {
    public static final String ARGS_ALBUM_NAME = "ALBUM_NAME";
    public static final String ARGS_ARTIST_NAME = "ARTIST_NAME";
    private String album;
    private String artist;
    private MusicRepository mRepository;
    private RecyclerView mRecyclerView;
    private MusicAdapter mMusicAdapter;
    private List<Music> musics;


    public SongFragment() {
        // Required empty public constructor
    }

    public static SongFragment newInstance(String album, String artist) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_ALBUM_NAME, album);
        args.putString(ARGS_ARTIST_NAME, artist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        mRepository = MusicRepository.getInstance(getActivity());
        album = getArguments().getString(ARGS_ALBUM_NAME);
        artist = getArguments().getString(ARGS_ARTIST_NAME);
        if (album == null && artist == null)
            musics = mRepository.getMusicList();
        else if (album != null)
            musics = mRepository.getMusicListByAlbum(album);
        else if (artist != null)
            musics = mRepository.getMusicListByArtists(artist);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        findAllViews(view);
        updateUI();
        return view;
    }

    private void updateUI() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mMusicAdapter == null) {
            mMusicAdapter = new MusicAdapter(getActivity(), musics, this);
            mRecyclerView.setAdapter(mMusicAdapter);
        } else {
            mMusicAdapter.notifyDataSetChanged();
        }
    }

    private void findAllViews(View view) {
        mRecyclerView = view.findViewById(R.id.music_recycler_view);
    }

    @Override
    public void musicClicked(int musicPosition) {
        Intent intent = PlayMusicActivity.newIntent(getActivity(), musicPosition, album, artist);
        getActivity().startActivity(intent);
    }
}