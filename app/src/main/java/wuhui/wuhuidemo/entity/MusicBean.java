package wuhui.wuhuidemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuhui on 2017/4/12.
 */

public class MusicBean implements Parcelable {
    private String musicTitle;
    private String musicPath;
    private String musicAuthor;
    private String musicAlbum;

    public MusicBean(String musicTitle, String musicPath, String musicAuthor, String musicAlbum) {
        this.musicPath = musicPath;
        this.musicTitle = musicTitle;
        this.musicAuthor = musicAuthor;
        this.musicAlbum = musicAlbum;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public String getMusicAuthor() {
        return musicAuthor;
    }

    public void setMusicAuthor(String musicAuthor) {
        this.musicAuthor = musicAuthor;
    }

    public String getMusicAlbum() {
        return musicAlbum;
    }

    public void setMusicAlbum(String musicAlbum) {
        this.musicAlbum = musicAlbum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(musicPath);
        dest.writeString(musicTitle);
        dest.writeString(musicAuthor);
        dest.writeString(musicAlbum);
    }


    protected MusicBean(Parcel in) {
        this.musicPath = in.readString();
        this.musicTitle = in.readString();
        this.musicAuthor = in.readString();
        this.musicAlbum = in.readString();
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };

    @Override
    public String toString() {
        return musicAuthor + "  " + musicTitle + "   " + musicAlbum;
    }
}
