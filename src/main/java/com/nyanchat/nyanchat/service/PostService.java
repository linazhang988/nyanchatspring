package com.nyanchat.nyanchat.service;

import com.nyanchat.nyanchat.model.PostModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PostService {
    private List<PostModel> allPosts = new ArrayList<>();

    public List<PostModel> getAllPosts(){
        return allPosts;
    }
    public PostModel getPostByPostId(long postId){
        for(PostModel post:allPosts){
            if(post.getPostId()==postId) return post;
        }
        return null;
    }
    public PostModel getPostByUserId(long userId){
        for(PostModel post:allPosts){
            if(post.getUserId()==userId) return post;
        }
        return null;
    }
    public List<PostModel> getAllPostsByUserId(long userId){
        List<PostModel> allPostByUser=new ArrayList<>();
        for(PostModel post:allPosts){
            if(post.getUserId()==userId) allPostByUser.add(post);
        }
        return allPostByUser;
    }
    public boolean addPost(PostModel postModel){
        allPosts.add(postModel);
        PostModel model= getPostByPostId(postModel.getPostId());
        return (model!=null);
    }
    public boolean deletePost(long postId){
        return allPosts.remove(getPostByPostId(postId));
    }
    public boolean updatePost(long postId, PostModel updatedPost){
        PostModel post=getPostByPostId(postId);
        post.updateContent(updatedPost);
        return post.getEditedTime().equals(updatedPost.getTimeStamp()) && post.getContent().equals(updatedPost.getContent());
    }
}
