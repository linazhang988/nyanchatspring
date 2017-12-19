package com.nyanchat.nyanchat.model;

public class PostModel {
    private long postId;
    private String content;
    private long threadId;
    private long userId;
    private String timeStamp;
    private String editedTime;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    public PostModel(){}
    public PostModel(long postId, String content, long threadId, long userId, String timeStamp) {
        this.postId = postId;
        this.content = content;
        this.threadId = threadId;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }
    @Override
    public String toString(){
        return String.format(
                "Post [postId=%d, content=%s, threadId=%d, userId=%d, timeStamp=%s",
                postId,content,threadId,userId,timeStamp
        );
    }

    public void updateContent(PostModel updatedPost){
        this.content=updatedPost.content;
        this.editedTime=updatedPost.timeStamp;
    }

    public String getEditedTime(){
        return editedTime;
    }
}
