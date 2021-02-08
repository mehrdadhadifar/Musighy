package com.hfad.musighy.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.musighy.R;
import com.hfad.musighy.adapter.AlbumAdapter;
import com.hfad.musighy.adapter.ArtistAdapter;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicRepository;

import java.util.List;

public class ArtistFragment extends Fragment {
    private MusicRepository mRepository;
    private ArtistAdapter mArtistAdapter;
    private RecyclerView mRecyclerView;

    public ArtistFragment() {
        // Required empty public constructor
    }


    public static ArtistFragment newInstance() {
        ArtistFragment fragment = new ArtistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = MusicRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        findAllViews(view);
        updateUI();

        return view;
    }


    private void updateUI() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        List<Music> musics = mRepository.getArtistList();
        if (mArtistAdapter == null) {
            mArtistAdapter = new ArtistAdapter(getActivity(), musics);
            mRecyclerView.setAdapter(mArtistAdapter);
        } else {
            mArtistAdapter.notifyDataSetChanged();
        }
    }

    private void findAllViews(View view) {
        mRecyclerView = view.findViewById(R.id.artist_recycler_view);
    }
}