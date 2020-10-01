package com.hfad.musighy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.musighy.R;
import com.hfad.musighy.controller.activity.PlayMusicActivity;
import com.hfad.musighy.model.Music;
import com.hfad.musighy.model.MusicRepository;

import java.io.ByteArrayInputStream;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private Context mContext;
    private List<Music> mMusicList;

    public MusicAdapter(Context context, List<Music> musicList) {
        mContext = context;
        mMusicList = musicList;
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recycler_music, parent, false);
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        Music music = mMusicList.get(position);
        holder.bindMusic(music);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewMusicFileName;
        private ImageView mImageViewAlbumArt;

        public MusicHolder(@NonNull final View itemView) {
            super(itemView);
            mTextViewMusicFileName = itemView.findViewById(R.id.text_view_music_file_name);
            mImageViewAlbumArt = itemView.findViewById(R.id.image_view_music_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = PlayMusicActivity.newIntent(mContext, getAdapterPosition());
                    mContext.startActivity(intent);
                }
            });
        }


        public void bindMusic(Music music) {
            mTextViewMusicFileName.setText(music.getTitle());
            mImageViewAlbumArt.setImageDrawable(new MusicRepository(mContext).getAlbumArt(music));

        }
    }


}
