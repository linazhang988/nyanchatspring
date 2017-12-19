package com.nyanchat.nyanchat.controller;

import com.nyanchat.nyanchat.model.PostModel;
import com.nyanchat.nyanchat.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/posts") //all posts
    public ResponseEntity<List<PostModel>> getAllPosts() {
        List<PostModel> allPosts = postService.getAllPosts();
        if (allPosts == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (allPosts.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}") //postBy PostId
    public ResponseEntity<PostModel> getPostByPostId(@PathVariable long postId) {
        PostModel post = postService.getPostByPostId(postId);
        if (post == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/posts") //get all posts userId
    public ResponseEntity<List<PostModel>> getAllPostByUserId(@PathVariable long userId) {
        List<PostModel> postsByUserId = postService.getAllPostsByUserId(userId);
        if (postsByUserId.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(postsByUserId, HttpStatus.OK);
    }

    @GetMapping("/posts/{month}/{day}/{year}") //post by userId
    public ResponseEntity<List<PostModel>> getAllPostsByDate(@PathVariable String month, @PathVariable String day, @PathVariable String year) {
        String date = month + '/' + day + '/' + year;
        List<PostModel> postsByDate = postService.getAllPostsByDate(date);
        if (postsByDate.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(postsByDate, HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity<URI> addPost(@RequestBody PostModel postModel) {
        if (!postService.addPost(postModel))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        System.out.println(postModel.getPostId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{postId}").buildAndExpand(postModel.getPostId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable long postId) {
        if (!postService.deletePost(postId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable long postId, @RequestBody PostModel updatedPost) {
        if (!postService.updatePost(postId, updatedPost))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
