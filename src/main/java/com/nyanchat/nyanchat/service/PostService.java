package com.nyanchat.nyanchat.service;

import com.nyanchat.nyanchat.model.PostModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private static List<PostModel> allPosts = new ArrayList<>();

    static {
        PostModel mockPost1 = new PostModel(101, "Dalmatians!", 18, 1, "01/25/1961 01:25");
        PostModel mockPost2 = new PostModel(102, "Dalmatians?!", 18, 1, "11/22/2000 11:22");
        PostModel mockPost3 = new PostModel(103, "Dalmatians? Too Many!", 19, 2, "01/25/1961 14:33");

        allPosts.add(mockPost1);
        allPosts.add(mockPost2);
        allPosts.add(mockPost3);
    }

    public List<PostModel> getAllPosts() {
        return allPosts;
    }

    public PostModel getPostByPostId(long postId) {
        for (PostModel post : allPosts) {
            if (post.getPostId() == postId) return post;
        }
        return null;
    }

    public List<PostModel> getAllPostsByDate(String date) {
        List<PostModel> allPostsByDate = new ArrayList<>();
        for (PostModel post : allPosts) {
            if (post.getTimeStamp().contains(date)) allPostsByDate.add(post);
        }
        return allPostsByDate;
    }

    public List<PostModel> getAllPostsByUserId(long userId) {
        List<PostModel> allPostByUser = new ArrayList<>();
        for (PostModel post : allPosts) {
            if (post.getUserId() == userId) allPostByUser.add(post);
        }
        return allPostByUser;
    }

    public boolean addPost(PostModel postModel) {
        allPosts.add(postModel);
        PostModel model = getPostByPostId(postModel.getPostId());
        return (model != null);
    }

    public boolean deletePost(long postId) {
        return allPosts.remove(getPostByPostId(postId));
    }

    public boolean updatePost(long postId, PostModel updatedPost) {
        PostModel post = getPostByPostId(postId);
        if(post==null)
            return false;
        post.updateContent(updatedPost);
        return post.getEditedTime().equals(updatedPost.getTimeStamp()) && post.getContent().equals(updatedPost.getContent());
    }
}
