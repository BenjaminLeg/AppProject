package com.example.benjamin.moviesapp.elements;

import io.realm.RealmObject;

public class Movie extends RealmObject {
    private String id ;
    private String title;
    private String release_date;
    private String original_language;
    private String overview;
    private String posterSrc;
    private String voteAvg;
    private String homePageUrl;

    public Movie() {
    }

    public Movie(String id, String title, String release_date, String original_language, String overview, String poster_path, String vote_average){
        this.id=id;
        this.title=title;
        this.release_date=release_date;
        this.original_language=original_language;
        this.overview=overview;
        this.posterSrc=poster_path;
        this.voteAvg=vote_average;
    }

    public void setId(String id) {    this.id = id;    }

    public void setTitle(String title) {   this.title = title;    }

    public void setRelease_date(String release_date) {   this.release_date = release_date;    }

    public void setOriginal_language(String original_language) {    this.original_language = original_language;    }

    public void setOverview(String overview) {    this.overview = overview;    }

    public void setPosterSrc(String posterSrc) {    this.posterSrc = posterSrc;   }

    public void setVoteAvg(String voteAvg) {   this.voteAvg = voteAvg;   }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return posterSrc;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

}
