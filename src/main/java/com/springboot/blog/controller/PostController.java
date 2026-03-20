package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "Posts"
)
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create a new post",
            description = "Create a new post and return the created post data"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Post successfully CREATED",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostDTO.class)
            )
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts")
    // create blog post
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDto) {

        // Create the post through the post service
        PostDTO postResponse = this.postService.createPost(postDto);

        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all posts",
            description = "Retrieve all posts with pagination and sorting support"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostResponse.class)
            )
    )
    @GetMapping("/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return this.postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Get post by ID",
            description = "Retrieve a single post by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostDTO.class)
            )
    )
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPostByIdV1(@PathVariable(name = "id") Long id) {

        return ResponseEntity.ok(this.postService.getPostById(id));
    }

    @Operation(
            summary = "Update post",
            description = "Update an existing post by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post updated successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostDTO.class)
            )
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") Long id) {
        PostDTO postResponse = this.postService.updatePost(postDTO, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete post",
            description = "Delete an existing post by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post deleted successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            )
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        this.postService.deletePostById(id);

        return new ResponseEntity<>("The Post Entity is successfully deleted.", HttpStatus.OK);
    }

    @Operation(
            summary = "Get posts by category ID",
            description = "Retrieve all posts belonging to a specific category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PostDTO.class))
            )
    )
    @GetMapping("/posts/category/{id}")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable(name = "id") Long categoryId) {
        List<PostDTO> postDtos = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
}
