package com.example.ga_23s1_comp2100_6442.model;

import android.graphics.Bitmap;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Course is a model class to model the courses data fetched from Firebase containing the important
 * attribute sto display on screen for end user
 * @author taiha
 */
public class Course implements Comparable<Course>{
    private String courseId;
    private String title;
    private String author;
    private String authorId;
    private String link;
    private String thumbnail;
    private List<String> studentsEnrolled;
    private List<String> studentsApplied;
    private List<String> Lecturers;
    private boolean isPublic;
    private String description;
    private List<String> filters;
    private List<String>  searchTerm;

    public Course(String title, String author, String authorId, String link, String thumbnail, boolean isPublic, String description, List<String> filters, List<String> searchTerm) {
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.link = link;
        this.thumbnail = thumbnail;
        this.isPublic = isPublic;
        this.description = description;
        this.filters = filters;
        this.searchTerm = searchTerm;
    }

    public Course(String title, String author, String authorId, String link, String thumbnail, boolean isPublic) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.thumbnail = thumbnail;
        this.isPublic = isPublic;
        this.authorId=authorId;
    }

    public Course(String title, String author,String authorId, String link, String thumbnail, boolean isPublic,List<String> studentsEnrolled) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.thumbnail = thumbnail;
        this.isPublic = isPublic;
        this.studentsEnrolled=studentsEnrolled;
        this.authorId=authorId;
    }


    public Course() {

    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }



    public String getAuthor() {
        return author;
    }



    public String getTitle() {
        return title;
    }



    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public List<String> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(List<String> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public List<String> getStudentsApplied() {
        return studentsApplied;
    }

    public void setStudentsApplied(List<String> studentsApplied) {
        this.studentsApplied = studentsApplied;
    }

    public List<String> getLecturers() {
        return Lecturers;
    }

    public void setLecturers(List<String> lecturers) {
        Lecturers = lecturers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }





    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public List<String> getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(List<String> searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public String toString() {
        return "Course{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", link=" + link +
                ", description='" + description + '\'' +
                ", filters=" + filters +
                ", searchTerm=" + searchTerm +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(author, course.author) && Objects.equals(title, course.title) && Objects.equals(thumbnail, course.thumbnail) && Objects.equals(link, course.link) && Objects.equals(description, course.description) && Objects.equals(filters, course.filters) && Objects.equals(searchTerm, course.searchTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, thumbnail, link, description, filters, searchTerm);
    }


    public String getLink() {
        return link;
    }

    @Override
    public int compareTo(Course course) {
        if (this.hashCode() > course.hashCode()) return 1;
        else if (this.hashCode() < course.hashCode()) return -1;
        return 0;
    }
}
