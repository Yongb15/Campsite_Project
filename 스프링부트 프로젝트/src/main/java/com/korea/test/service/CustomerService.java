package com.korea.test.service;

import org.springframework.stereotype.Service;

import com.korea.test.vo.CustomerVO;

import lombok.RequiredArgsConstructor;

public interface CustomerService {
	public CustomerVO checkId(String id); 
	
	public CustomerVO loginCheck(String id);
	
	public CustomerVO findUserId(String id);
	
	public int userSave(CustomerVO vo);
	
	public int userDel(CustomerVO vo);
	
	public int changePwd(CustomerVO vo);
}
