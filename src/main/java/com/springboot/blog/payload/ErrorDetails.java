package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private String timestamp;
    private String message;
    private String details;
}
