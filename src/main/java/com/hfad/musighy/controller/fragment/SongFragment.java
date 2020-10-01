package com.hfad.musighy.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.musighy.R;
import com.hfad.musighy.adapter.MusicAdapter;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicsInfo;

import java.util.List;


public class SongFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MusicAdapter mMusicAdapter;

    public SongFragment() {
        // Required empty public constructor
    }

    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        List<Music> musics = new MusicsInfo().getMusics(getActivity());
        if (mMusicAdapter == null) {
            mMusicAdapter = new MusicAdapter(getActivity(),musics);
            mRecyclerView.setAdapter(mMusicAdapter);
        } else {
            mMusicAdapter.notifyDataSetChanged();
        }
    }

    private void findAllViews(View view) {
        mRecyclerView=view.findViewById(R.id.music_recycler_view);
    }
}