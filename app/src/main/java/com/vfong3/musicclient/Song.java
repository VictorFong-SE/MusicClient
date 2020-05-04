package com.vfong3.musicclient;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable
{
    public static final Creator CREATOR = new Creator()
    {
        public Song createFromParcel(Parcel in)
        {
            return new Song(in);
        }
        public Song[] newArray(int size)
        {
            return new Song[size];
        }
    };

    private long id;
    private String title;
    private String artist;
    private Bitmap cover;
    private String url;

    public Song(long id, String title, String artist, Bitmap cover, String url)
    {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.cover = cover;
        this.url = url;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public Bitmap getCover()
    {
        return cover;
    }

    public void setCover(Bitmap cover)
    {
        this.cover = cover;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Song(Parcel in)
    {
        this.id = in.readLong();
        this.title = in.readString();
        this.artist = in.readString();
        this.cover = in.readParcelable(Bitmap.class.getClassLoader());
        this.url = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeParcelable(this.cover, flags);
        dest.writeString(this.url);
    }

    @Override
    public String toString()
    {
        return "Song{" +
                "id='" + this.id + '\'' +
                ", title='" + this.title + '\'' +
                ", artist='" + this.artist + '\'' +
                ", url='" + this.url + '\'' +
                '}';
    }
}
