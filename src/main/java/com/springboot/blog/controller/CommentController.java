package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Tag(
        name = "Comments"
)
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Create comment
    @Operation(
            summary = "Create comment",
            description = "Create a new comment for a specific post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Comment created successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid input"
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") Long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO commentResponse = this.commentService.createComment(postId, commentDTO);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    // Get all comments for a post
    @Operation(
            summary = "Get comments by post ID",
            description = "Retrieve all comments associated with a specific post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comments retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class))
            )
    )
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getComments(@PathVariable(name = "postId") Long postId) {
        return this.commentService.getComments(postId);
    }

    // Get single comment by ID
    @Operation(
            summary = "Get comment by ID",
            description = "Retrieve a specific comment by post ID and comment ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Comment not found"
    )
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "postId") Long postId,
                                     @PathVariable(name = "commentId") Long commentId) {
        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Update comment",
            description = "Update an existing comment for a specific post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment updated successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid input"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Comment not found"
    )
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "commentId") Long commentId,
                                                    @Valid @RequestBody CommentDTO comment) {
        CommentDTO commentDTO = this.commentService.updateComment(postId, commentId, comment);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete comment",
            description = "Delete an existing comment from a specific post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment deleted successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Comment not found"
    )
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId,
                                                @PathVariable(name = "commentId") Long commentId){
        this.commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>("Comment deleted successfully :))", HttpStatus.OK);
    }
}
