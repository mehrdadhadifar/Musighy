package com.hfad.musighy.adapter;

import android.content.Context;
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
import com.hfad.musighy.model.Music;

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

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewMusicFileName = itemView.findViewById(R.id.text_view_music_file_name);
            mImageViewAlbumArt = itemView.findViewById(R.id.image_view_music_img);
        }

        public void bindMusic(Music music) {
            mTextViewMusicFileName.setText(music.getTitle());
            byte[] image = getAlbumArt(music.getPath());
            Drawable albumArt;
            if (image != null) {
                ByteArrayInputStream is = new ByteArrayInputStream(image);
                albumArt = Drawable.createFromStream(is, "Image");
            } else
                albumArt = mContext.getResources().getDrawable(R.drawable.ic_no_album_art);
            mImageViewAlbumArt.setImageDrawable(albumArt);

        }
    }

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
