package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Data
@Schema(
        description = "PostDTO Model Information"
)
public class PostDTOV2 {
    private Long id;
    // title should not be null or empty
    // title should have at least 1 character
    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @Size(min = 1, message = "Post title should have at least 1 character.")
    private String title;

    // description should not be null or empty
    // description should have at least 10 characters
    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters.")
    private String description;

    // content should not be null or empty
    // content should have at least 1 character
    @Schema(
            description = "Blog Post Content"
    )
    @NotEmpty
    @Size(min = 1, message = "Post content should have at least 1 character.")
    private String content;
    private Set<CommentDTO> comments;

    @Schema(
            description = "Blog Post Category"
    )
    private Long categoryId;
    private List<String> tags;
}

