package com.linka39.genyundemo.entity;

import javax.persistence.*;

/**
 * @author linka39
 */
@Entity
@Table(name="t_article2")
public class Article {

    private Integer id;
    private String name;
    private String content;
    private String includeDate;
    private String share_user;
    private String share_url;
    private String password;
    private String share_date;
    private boolean is_index;
    private Integer state;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getincludeDate() {
        return includeDate;
    }

    public void setincludeDate(String includeDate) {
        this.includeDate = includeDate;
    }

    public String getShare_user() {
        return share_user;
    }

    public void setShare_user(String share_user) {
        this.share_user = share_user;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShare_date() {
        return share_date;
    }

    public void setShare_date(String share_date) {
        this.share_date = share_date;
    }


    public boolean isIs_index() {
        return is_index;
    }

    public void setIs_index(boolean is_index) {
        this.is_index = is_index;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", includeDate='" + includeDate + '\'' +
                ", share_user='" + share_user + '\'' +
                ", share_url='" + share_url + '\'' +
                ", password='" + password + '\'' +
                ", share_date='" + share_date + '\'' +
                ", is_index=" + is_index +
                ", state=" + state +
                '}';
    }
}
