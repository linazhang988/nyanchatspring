package com.nyanchat.nyanchat.controller;

import com.nyanchat.nyanchat.model.PostModel;
import com.nyanchat.nyanchat.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PostController.class, secure = false)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    private PostModel mockPost1, mockPost2, mockPost3;
    private String postJson;

    @Before
    public void setUp() {
        mockPost1 = new PostModel(101, "Dalmatians!", 18, 1, "01/25/1961 01:25");
        mockPost2 = new PostModel(102, "Dalmatians?!", 18, 1, "11/22/2000 11:22");
        mockPost3 = new PostModel(103, "Dalmatians? Too Many!", 19, 2, "01/25/1961 14:33");
        postJson = "{\"postId\":\"104\",\"content\":\"Dalmatians\",\"threadId\":\"18\",\"userId\":\"1\",\"timeStamp\":\"12/17/2017 00:27\"}";
    }

    @Test
    public void getAllPosts() throws Exception {
        Mockito.when(postService.getAllPosts()).thenReturn(Arrays.asList(mockPost1, mockPost2, mockPost3));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/posts");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].postId", is(101)))
                .andExpect(jsonPath("$[1].postId", is(102)))
                .andExpect(jsonPath("$[2].postId", is(103)))
                .andExpect(jsonPath("$[0].content", is("Dalmatians!")))
                .andExpect(jsonPath("$[1].content", is("Dalmatians?!")))
                .andExpect(jsonPath("$[2].content", is("Dalmatians? Too Many!")))
                .andReturn();
    }

    @Test
    public void getAllPostsEmptyList() throws Exception {
        Mockito.when(postService.getAllPosts()).thenReturn(Collections.emptyList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/posts");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void getAllPostsIsNull() throws Exception {
        Mockito.when(postService.getAllPosts()).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/posts");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getPostByPostId() throws Exception {
        Mockito.when(postService.getPostByPostId(Mockito.anyLong())).thenReturn(mockPost1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/posts/101");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId", is(101)))
                .andExpect(jsonPath("$.content", is("Dalmatians!")))
                .andExpect(jsonPath("$.threadId", is(18)))
                .andReturn();
    }

    @Test
    public void getPostByPostIdNoneFound() throws Exception {
        Mockito.when(postService.getPostByPostId(Mockito.anyLong())).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/posts/101");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllPostByUserId() throws Exception {
        Mockito.when(postService.getAllPostsByUserId(Mockito.anyLong())).thenReturn(Arrays.asList(mockPost1, mockPost2));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1/posts");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[1].userId", is(1)))
                .andExpect(jsonPath("$[0].content", is("Dalmatians!")))
                .andExpect(jsonPath("$[1].content", is("Dalmatians?!")))
                .andReturn();
    }

    @Test
    public void getAllPostByUserIdNoneFound() throws Exception {
        Mockito.when(postService.getAllPostsByUserId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1/posts");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void getAllPostsByDate() throws Exception {
        Mockito.when(postService.getAllPostsByDate(Mockito.anyString())).thenReturn(Arrays.asList(mockPost1,mockPost3));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/posts/01/25/1961");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[1].userId", is(2)))
                .andExpect(jsonPath("$[0].content", is("Dalmatians!")))
                .andExpect(jsonPath("$[1].content", is("Dalmatians? Too Many!")))
                .andExpect(jsonPath("$[0].timeStamp",is("01/25/1961 01:25")))
                .andExpect(jsonPath("$[1].timeStamp",is("01/25/1961 14:33")))
                .andReturn();

    }

    @Test
    public void getAllPostsByDateNoneFound() throws Exception {
        Mockito.when(postService.getAllPostsByDate(Mockito.anyString())).thenReturn(Collections.emptyList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/posts/01/25/2017");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void addPost() throws Exception {
        Mockito.when(postService.addPost(Mockito.any(PostModel.class))).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/posts").accept(MediaType.APPLICATION_JSON).content(postJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost/posts/104")))
                .andReturn();

    }

    @Test
    public void addPostFail() throws Exception {
        Mockito.when(postService.addPost(Mockito.any(PostModel.class))).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/posts").accept(MediaType.APPLICATION_JSON).content(postJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isIAmATeapot())
                .andReturn();
    }

    @Test
    public void deletePost() throws Exception {
        Mockito.when(postService.deletePost(Mockito.anyLong())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/posts/1");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deletePostNotFound() throws Exception {
        Mockito.when(postService.deletePost(Mockito.anyLong())).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/posts/5");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void updatePost() throws Exception {
        Mockito.when(postService.updatePost(Mockito.anyLong(),Mockito.any(PostModel.class))).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/posts/3").accept(MediaType.APPLICATION_JSON).content(postJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updatePostFailed() throws Exception {
        Mockito.when(postService.updatePost(Mockito.anyLong(),Mockito.any(PostModel.class))).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/posts/3").accept(MediaType.APPLICATION_JSON).content(postJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

}