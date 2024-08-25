package com.korea.test.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private int readhit;
	private int delInfo;
	private String name;
	private String subject;
	private String content;
	private String pwd;
	private String regdate;
}
