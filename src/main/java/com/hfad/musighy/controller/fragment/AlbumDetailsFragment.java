package com.hfad.musighy.controller.fragment;

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
import com.hfad.musighy.model.MusicRepository;

public class AlbumDetailsFragment extends Fragment {
    private MusicRepository mRepository;
    private String album;
    private ImageView albumArtImageView;
    private RecyclerView mRecyclerView;
    private MusicAdapter mMusicAdapter;

    public static final String ARGS_ALBUM_NAME = "ALBUM_NAME";

    public AlbumDetailsFragment() {
        // Required empty public constructor
    }

    public static AlbumDetailsFragment newInstance(String albumName) {
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_ALBUM_NAME, albumName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = MusicRepository.getInstance(getActivity());
        album = getArguments().getString(ARGS_ALBUM_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_details, container, false);
        albumArtImageView = view.findViewById(R.id.album_art_details);
        mRecyclerView = view.findViewById(R.id.album_songs_recycler_view);

        Glide.with(getActivity()).load(mRepository.getMusicListByAlbum(album).get(0).getAlbumArtUri())
                .placeholder(R.drawable.ic_no_album_art).into(albumArtImageView);
        mMusicAdapter = new MusicAdapter(getActivity(), mRepository.getMusicListByAlbum(album));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mMusicAdapter);

        return view;
    }
}