package com.hfad.musighy.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.hfad.musighy.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class MusicRepository {

    public static final String TAG = "MI";
    private Context mContext;
    private ArrayList<Music> mMusicList;

/*    public static MusicRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sMusicRepository == null)
            sMusicRepository = new MusicRepository();
        return sMusicRepository;
    }*/

    public ArrayList<Music> getMusicList() {
        return mMusicList;
    }

    public MusicRepository(Context context) {
        mContext = context.getApplicationContext();
        if (mMusicList == null)
            mMusicList = getMusics(mContext);
    }

    private ArrayList<Music> getMusics(Context context) {
        ArrayList<Music> musicList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String info[] = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media._ID
        };
        Cursor cursor = context.getContentResolver().query(uri, info,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String path = cursor.getString(0);
                String title = cursor.getString(1);
                String artist = cursor.getString(2);
                String album = cursor.getString(3);
                String duration = cursor.getString(4);
                long id = cursor.getLong(5);
                Uri myUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                );
                Log.d(TAG, "my path:" + myUri);

                Music music = new Music(path, title, artist, album, duration);
                Log.d(TAG, "path:" + path + " title:" + title + " artist:" + artist + " album:" + album + " duration:" + duration);
                musicList.add(music);
            }
            cursor.close();
        }
        return musicList;
    }

    public Drawable getAlbumArt(Music music) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(music.getPath());
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        Drawable albumArt;
        if (art != null) {
            ByteArrayInputStream is = new ByteArrayInputStream(art);
            albumArt = Drawable.createFromStream(is, "Image");
        } else
            albumArt = mContext.getResources().getDrawable(R.drawable.ic_no_album_art);
        return albumArt;
    }
}

