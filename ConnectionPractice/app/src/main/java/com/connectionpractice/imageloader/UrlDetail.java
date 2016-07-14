package com.connectionpractice.imageloader;

/**
 * Created by nnit on 6/13/16.
 */
public class UrlDetail{

    public UrlDetail(String url, String text, String content, Boolean post, int id) {

        this.mUrl = url;
        this.mText = text;
        this.mContent = content;
        this.mPost = post;
        this.mId = id;
    }


    public String getUrl() {
        return mUrl;
    }

    public String getText() {
        return mText;
    }

    public String getContent() {
        return mContent;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setText(String mText) {
        this.mText = mText;
    }
    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    private String mUrl;
    private String mText;
    private String mContent;


    public Boolean getPost() {
        return mPost;
    }

    public int getId() {
        return mId;
    }

    public void setPost(Boolean mPost) {
        this.mPost = mPost;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    private Boolean mPost;
    private int mId;
}