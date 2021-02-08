package com.hfad.musighy.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hfad.musighy.R;
import com.hfad.musighy.adapter.MusicAdapter;
import com.hfad.musighy.controller.activity.PlayMusicActivity;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicRepository;

import java.util.List;

public class AlbumDetailsFragment extends Fragment implements MusicAdapter.MusicClicked {
    public static final String ARGS_ALBUM_NAME = "ALBUM_NAME";
    public static final String ARGS_ARTIST_NAME = "ARTIST_NAME";
    private MusicRepository mRepository;
    private String album;
    private String artist;
    private ImageView mAlbumArtImageView;
    private ImageView mBackImageView;
    private RecyclerView mRecyclerView;
    private MusicAdapter mMusicAdapter;
    private List<Music> mMusicList;


    public AlbumDetailsFragment() {
        // Required empty public constructor
    }

    public static AlbumDetailsFragment newInstance(String albumName, String artistName) {
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_ALBUM_NAME, albumName);
        args.putString(ARGS_ARTIST_NAME, artistName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = MusicRepository.getInstance(getActivity());
        initData();
    }

    private void initData() {
        album = getArguments().getString(ARGS_ALBUM_NAME);
        artist = getArguments().getString(ARGS_ARTIST_NAME);
        if (album != null)
            mMusicList = mRepository.getMusicListByAlbum(album);
        else if (artist != null)
            mMusicList = mRepository.getMusicListByArtists(artist);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_details, container, false);
        findAllViews(view);
        initUI();
        setListeners();
        return view;
    }

    private void setListeners() {
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void initUI() {
        if (album != null)
            Glide.with(getActivity()).load(mRepository.getMusicListByAlbum(album).get(0).getAlbumArtUri())
                    .placeholder(R.drawable.ic_no_album_art).into(mAlbumArtImageView);
        else
            Glide.with(getActivity()).load(mRepository.getMusicListByArtists(artist).get(0).getAlbumArtUri())
                    .placeholder(R.drawable.ic_no_album_art).into(mAlbumArtImageView);
        mMusicAdapter = new MusicAdapter(getActivity(), mMusicList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mMusicAdapter);
    }

    private void findAllViews(View view) {
        mAlbumArtImageView = view.findViewById(R.id.album_art_details);
        mBackImageView = view.findViewById(R.id.image_button_back);
        mRecyclerView = view.findViewById(R.id.album_songs_recycler_view);
    }

    @Override
    public void musicClicked(int musicPosition) {
        Intent intent = PlayMusicActivity.newIntent(getActivity(), musicPosition, album, artist);
        getActivity().startActivity(intent);
    }
}