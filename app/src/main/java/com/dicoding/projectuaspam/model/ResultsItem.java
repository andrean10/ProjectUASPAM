package com.dicoding.projectuaspam.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsItem implements Parcelable {

    public static final Creator<ResultsItem> CREATOR = new Creator<ResultsItem>() {
        @Override
        public ResultsItem createFromParcel(Parcel in) {
            return new ResultsItem(in);
        }

        @Override
        public ResultsItem[] newArray(int size) {
            return new ResultsItem[size];
        }
    };
    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("video")
    private boolean video;

    @SerializedName("title")
    private String title;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("vote_count")
    private int voteCount;

    public ResultsItem() {
    }

    private ResultsItem(Parcel in) {
        overview = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        video = in.readByte() != 0;
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        popularity = in.readDouble();
        voteAverage = in.readDouble();
        id = in.readInt();
        adult = in.readByte() != 0;
        voteCount = in.readInt();
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public boolean isVideo() {
        return video;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(releaseDate);
        dest.writeDouble(popularity);
        dest.writeDouble(voteAverage);
        dest.writeInt(id);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeInt(voteCount);
    }
}