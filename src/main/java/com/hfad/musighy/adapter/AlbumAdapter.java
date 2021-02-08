package com.hfad.musighy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hfad.musighy.R;
import com.hfad.musighy.controller.activity.AlbumDetailsActivity;
import com.hfad.musighy.model.Music;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private Context mContext;
    private List<Music> mAlbumList;

    public AlbumAdapter(Context context, List<Music> albumList) {
        mContext = context;
        mAlbumList = albumList;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recycler_album, parent, false);
        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        Music music = mAlbumList.get(position);
        holder.bindAlbum(music);
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewAlbumFileName;
        private ImageView mImageViewAlbumArt;


        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewAlbumFileName = itemView.findViewById(R.id.album_name_text_view);
            mImageViewAlbumArt = itemView.findViewById(R.id.album_art_image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = AlbumDetailsActivity.newIntent(mContext, mAlbumList.get(getAdapterPosition()).getAlbum());
                    mContext.startActivity(intent);
                }
            });

        }

        private void bindAlbum(Music music) {
            mTextViewAlbumFileName.setText(music.getAlbum());
            Glide.with(mContext).load(music.getAlbumArtUri()).placeholder(R.drawable.ic_no_album_art).into(mImageViewAlbumArt);
        }
    }

}
