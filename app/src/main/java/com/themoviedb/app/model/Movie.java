package com.themoviedb.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by imran.khalid on 7/24/2016.
 */
public class Movie implements Parcelable{

    private String id;
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String thumbnail;
    @SerializedName("release_date")
    private String releaseDate;

    private String overview;
    @SerializedName("vote_average")
    private String rating;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(thumbnail);

        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeString(rating);
    }


    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        posterPath = in.readString();
        thumbnail = in.readString();

        releaseDate = in.readString();
        overview = in.readString();
        rating = in.readString();
    }
}
