package com.andresalcantar.jwsecretaryreports.models;

import com.andresalcantar.jwsecretaryreports.enums.PublisherType;

/**
 * Created by andres.alcantar on 23/12/2016.
 */

public class MonthReport {
    private Publisher publisher;
    private PublisherType type;
    private int year;
    private int month;

    private int publications;
    private int videos;
    private double hours;
    private int returnVisits;
    private int bibleStudies;
    private String commentaries;

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public PublisherType getType() {
        return type;
    }

    public void setType(PublisherType type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getPublications() {
        return publications;
    }

    public void setPublications(int publications) {
        this.publications = publications;
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public int getReturnVisits() {
        return returnVisits;
    }

    public void setReturnVisits(int returnVisits) {
        this.returnVisits = returnVisits;
    }

    public int getBibleStudies() {
        return bibleStudies;
    }

    public void setBibleStudies(int bibleStudies) {
        this.bibleStudies = bibleStudies;
    }

    public String getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(String commentaries) {
        this.commentaries = commentaries;
    }
}
