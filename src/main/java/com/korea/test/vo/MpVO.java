package com.korea.test.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class MpVO {
	/*
    idx NUMBER PRIMARY KEY,				    -- 회원번호
    id varchar(30) UNIQUE NOT null, 		-- 고객 아이디
    nickname VARCHAR(50) UNIQUE NOT NULL, 	-- 고객 이름
    Password VARCHAR(255) NOT NULL, 		-- 고객 비밀번호
    Birth DATE NOT NULL, 					-- 고객 생일
    PhoneNumber VARCHAR(20) NOT NULL,		-- 휴대폰 번호 
    Email VARCHAR(100) UNIQUE NOT NULL, 	-- 이메일
    ReservationNumber varchar(100)	        -- 예약 번호
	*/
	
	private int idx;
	private String id;
	private String nickName;
	private String password;
	private Date birth;
	private String phoneNumber;
	private String email;
	private String reservationNumber;
}
