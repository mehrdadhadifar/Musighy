package com.hfad.musighy.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.musighy.R;

public class PlayMusicFragment extends Fragment {

    public PlayMusicFragment() {
        // Required empty public constructor
    }


    public static PlayMusicFragment newInstance() {
        PlayMusicFragment fragment = new PlayMusicFragment();
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
        View view=inflater.inflate(R.layout.fragment_play_music, container, false);


        return view;
    }
}