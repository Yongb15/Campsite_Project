package com.korea.test.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.korea.test.mapper.CustomerMapper;
import com.korea.test.vo.CustomerVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerDAO {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	private final CustomerMapper customerMapper;
	
	public CustomerVO loginCheck(String id) {
		return customerMapper.loginCheck(id);
	}
	
	public CustomerVO checkId(String id) {
		return customerMapper.checkId(id);
	}
	
	public int signUpPage(CustomerVO vo) {
		return customerMapper.signUpPage(vo);
	}
	
	public CustomerVO findUserId(String id){
		return customerMapper.findUserId(id);
	}
	
	
	public int userSave(CustomerVO vo) {
		return customerMapper.userSave(vo);
	}
	
	public int userDel(CustomerVO vo) {
		return customerMapper.userDel(vo);
	}
	
	public int changePwd(CustomerVO vo) {
		return customerMapper.changePwd(vo);
	}

}
