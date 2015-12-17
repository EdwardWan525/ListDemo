package com.smartisan.edward.listdemo.Object;

/**
 * Created by smartisan on 15-12-17.
 */
public class ListItem {

    public ListItem(int id,String title,String author,String subject){
        this.id = id;
        this.title = title;
        this.author = author;
        this.subject = subject;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private int id;
    private String title;
    private String subject;
    private String author;
}
