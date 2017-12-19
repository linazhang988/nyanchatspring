package com.nyanchat.nyanchat.controller;

import com.nyanchat.nyanchat.model.PostModel;
import com.nyanchat.nyanchat.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value=PostController.class, secure=false);
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    private PostModel mockPost1, mockPost2;
    private String postJson;
    @Before
    public void setUp(){
        mockPost1 =new PostModel(101,"Dalmatians!",18,1, "01/25/1961 01:25");
        mockPost2 = new PostModel( 102, "Dalmations?!", 18, 1, "11/22/2000 11:22");
        postJson="{\"postId:\"101\",\"content\":\"Dalmatians\",\"threadId\":\"18\",\"userId\":\"1\"}";
    }
    @Test
    public void getAllPosts() throws Exception {
        Mockito.when(postService.getAllPosts()).thenReturn(Arrays.asList(mockPost1, mockPost2));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students/Student1/courses/Course1");
        MvcResult result=mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].getPostId()", (101)))
                .andReturn();
    }

    @Test
    public void getPostByPostId() throws Exception {
    }

    @Test
    public void getAllPostByUserId() throws Exception {
    }

    @Test
    public void getPostByUserId() throws Exception {
    }

    @Test
    public void addPost() throws Exception {
    }

    @Test
    public void deletePost() throws Exception {
    }

    @Test
    public void updatePost() throws Exception {
    }

}