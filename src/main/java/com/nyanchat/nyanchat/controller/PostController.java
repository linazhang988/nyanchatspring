package com.nyanchat.nyanchat.controller;

import com.nyanchat.nyanchat.model.PostModel;
import com.nyanchat.nyanchat.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/posts") //all posts
    public ResponseEntity<List<PostModel>> getAllPosts(){
        List<PostModel> allPosts=postService.getAllPosts();
        if(allPosts==null||allPosts.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(allPosts,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}") //postBy PostId
    public ResponseEntity<PostModel> getPostByPostId(@PathVariable long postId){
        PostModel post = postService.getPostByPostId(postId);
        if(post==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    @GetMapping("/posts/{userId}") //get all posts userId
    public ResponseEntity<List<PostModel>> getAllPostByUserId(@PathVariable long userId){
        List<PostModel> postsByUserId = postService.getAllPostsByUserId(userId);
        if(postsByUserId.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postsByUserId,HttpStatus.OK);
    }
    @GetMapping("/posts/{userId}/{postId}") //post by userId
    public ResponseEntity<PostModel> getPostByUserId(@PathVariable long userId){
        PostModel post= postService.getPostByUserId(userId);
        if(post==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    @PostMapping("/posts")
    public ResponseEntity<Void> addPost(PostModel postModel){
        if(!postService.addPost(postModel))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable long postId){
        if(!postService.deletePost(postId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable long postId, PostModel updatedPost){
        if(!postService.updatePost(postId,updatedPost))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
