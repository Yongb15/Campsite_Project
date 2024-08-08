package com.korea.test.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.korea.test.vo.MpVO;

@Mapper
public interface MpMapper {
	
	//로그인
	public MpVO loginCheck(String id);

	public MpVO findUserId(String id);

	public int userSave(MpVO vo);
	
}
