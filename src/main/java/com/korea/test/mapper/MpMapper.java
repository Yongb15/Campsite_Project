package com.korea.test.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.korea.test.vo.MpVO;

@Mapper
public interface MpMapper {
	
	//로그인
	public MpVO loginCheck(String id);
	
	//한 건 조회
	public MpVO selectOne (int idx);
	
	//회원 정보 수정 업데이트
	public int customerInfo(MpVO vo);
	
}
