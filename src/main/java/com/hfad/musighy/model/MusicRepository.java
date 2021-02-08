package com.hfad.musighy.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.hfad.musighy.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class MusicRepository {
    private static MusicRepository sMusicRepository;
    public static final String TAG = "Music Repository";
    private static Context mContext;
    private ArrayList<Music> mMusicList;
    private static MediaPlayer mMediaPlayer;
    private boolean shuffle;
    private boolean repeatAll;
    private boolean repeatOne;

    public static MusicRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sMusicRepository == null)
            sMusicRepository = new MusicRepository();
        return sMusicRepository;
    }

    public ArrayList<Music> getMusicList() {
        if (mMusicList == null)
            mMusicList = getMusics();
        return mMusicList;
    }

    private MusicRepository() {
    }

    private ArrayList<Music> getMusics() {
        ArrayList<Music> musicList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String info[] = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID
        };
        Cursor cursor = mContext.getContentResolver().query(uri, info,
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(0);
                String title = cursor.getString(1);
                String artist = cursor.getString(2);
                String album = cursor.getString(3);
                String duration = cursor.getString(4);
                long id = cursor.getLong(5);
                long albumId = cursor.getLong(6);

                Log.w(TAG, "album Id= " + albumId);
                Uri albumArtUri = ContentUris.withAppendedId(
//                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        Uri.parse("content://media/external/audio/albumart"),
                        albumId
                );
                Uri musicUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                );
                Log.d(TAG, "my path:" + albumArtUri);

                Music music = new Music(path, title, artist, album, duration, albumArtUri, musicUri);
                Log.d(TAG, "path:" + path + " title:" + title + " artist:" + artist + " album:" + album + " duration:" + duration);
                musicList.add(music);
            }
            cursor.close();
        }
        return musicList;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void stopAndReleaseMediaPlayer() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    public void startMediaPlayer(Uri musicUri) {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = MediaPlayer.create(mContext, musicUri);
            mMediaPlayer.start();
        } else {
            mMediaPlayer = MediaPlayer.create(mContext, musicUri);
            mMediaPlayer.start();
        }
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public boolean isRepeatAll() {
        return repeatAll;
    }

    public void setRepeatAll(boolean repeatAll) {
        this.repeatAll = repeatAll;
    }

    public boolean isRepeatOne() {
        return repeatOne;
    }

    public void setRepeatOne(boolean repeatOne) {
        this.repeatOne = repeatOne;
    }

    public List<Music> getMusicListByAlbum(String album) {
        List<Music> result = new ArrayList<>();
        for (Music music : mMusicList
        ) {
            if (music.getAlbum().equals(album))
                result.add(music);
        }
        return result;
    }

    public List<Music> getMusicListByArtists(String artist) {
        List<Music> result = new ArrayList<>();
        for (Music music : mMusicList
        ) {
            if (music.getArtist().equals(artist))
                result.add(music);
        }
        return result;
    }

    public List<Music> getAlbumList() {
        List<Music> result = new ArrayList<>();
        List<String> albumNames = new ArrayList<>();
        for (Music music : mMusicList
        ) {
            if (!albumNames.contains(music.getAlbum())) {
                result.add(music);
                albumNames.add(music.getAlbum());
            }
        }
        return result;
    }
}

