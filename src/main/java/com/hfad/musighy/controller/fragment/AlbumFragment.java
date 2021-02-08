package com.hfad.musighy.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.musighy.R;
import com.hfad.musighy.adapter.AlbumAdapter;
import com.hfad.musighy.adapter.MusicAdapter;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicRepository;

import java.util.List;


public class AlbumFragment extends Fragment {
    private MusicRepository mRepository;
    private AlbumAdapter mAlbumAdapter;
    private RecyclerView mRecyclerView;


    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();
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
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        findAllViews(view);
        updateUI();

        return view;
    }

    private void updateUI() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        List<Music> musics = mRepository.getAlbumList();
        if (mAlbumAdapter == null) {
            mAlbumAdapter = new AlbumAdapter(getActivity(), musics);
            mRecyclerView.setAdapter(mAlbumAdapter);
        } else {
            mAlbumAdapter.notifyDataSetChanged();
        }
    }

    private void findAllViews(View view) {
        mRecyclerView = view.findViewById(R.id.album_recycler_view);
    }
}