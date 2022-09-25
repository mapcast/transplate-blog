package com.transplate.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	
	private String uuid;
	
	private String postId;
	
	private String writer;
	
	private String content;
	
	private Boolean isLogin;
	
	private String writerId;
	
	private String password;
	
}
