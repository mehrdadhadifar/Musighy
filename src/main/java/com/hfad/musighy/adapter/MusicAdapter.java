package com.hfad.musighy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hfad.musighy.R;
import com.hfad.musighy.model.Music;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private Context mContext;
    private List<Music> mMusicList;
    private MusicClicked mMusicClicked;

    public MusicAdapter(Context context, List<Music> musicList, MusicClicked musicClicked) {
        mContext = context;
        mMusicList = musicList;
        mMusicClicked = musicClicked;
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
                    mMusicClicked.musicClicked(getAdapterPosition());
                }
            });
        }


        public void bindMusic(Music music) {
            mTextViewMusicFileName.setText(music.getTitle());
            Glide.with(mContext).load(music.getAlbumArtUri()).placeholder(R.drawable.ic_no_album_art).into(mImageViewAlbumArt);

        }
    }

    public interface MusicClicked {
        public void musicClicked(int musicPosition);
    }
}
