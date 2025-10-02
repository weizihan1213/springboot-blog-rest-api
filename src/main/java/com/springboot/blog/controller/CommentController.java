package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") Long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO commentResponse = this.commentService.createComment(postId, commentDTO);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getComments(@PathVariable(name = "postId") Long postId) {
        return this.commentService.getComments(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "postId") Long postId,
                                     @PathVariable(name = "commentId") Long commentId) {
        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "commentId") Long commentId,
                                                    @Valid @RequestBody CommentDTO comment) {
        CommentDTO commentDTO = this.commentService.updateComment(postId, commentId, comment);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId,
                                                @PathVariable(name = "commentId") Long commentId){
        this.commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>("Comment deleted successfully :))", HttpStatus.OK);
    }
}
