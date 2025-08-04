package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    // title should not be null or empty
    // title should have at least 1 character
    @NotEmpty
    @Size(min = 1, message = "Post title should have at least 1 character.")
    private String title;

    // description should not be null or empty
    // description should have at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters.")
    private String description;

    // content should not be null or empty
    // content should have at least 1 character
    @NotEmpty
    @Size(min = 1, message = "Post content should have at least 1 character.")
    private String content;
    private Set<CommentDTO> comments;
}
