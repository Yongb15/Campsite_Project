package com.korea.test.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.korea.test.vo.CustomerVO;

@Mapper
public interface CustomerMapper {
	
	public CustomerVO checkId(String id);
	
	public int signUpPage(CustomerVO vo);
	
	public CustomerVO loginCheck(String id);
		
	//회원 정보 조회
	public CustomerVO findUserId(String id);
		
	//회원 정보 수정 업데이트
	public int userSave(CustomerVO vo);
		
	//회원 탈퇴
	public int userDel(CustomerVO vo);
		
	//비밀번호 변경
	public int changePwd(CustomerVO vo);
}
