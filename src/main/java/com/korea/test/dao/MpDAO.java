package com.korea.test.dao;

import org.springframework.stereotype.Repository;

import com.korea.test.mapper.MpMapper;
import com.korea.test.vo.MpVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MpDAO {
	private final MpMapper mpMapper;
	
	
	public MpVO loginCheck(String id) {
		return mpMapper.loginCheck(id);
	}
	
	
	public MpVO selectOne (int idx){
		return mpMapper.selectOne(idx);
	}
	
	
	public int customerInfo(MpVO vo) {
		return mpMapper.customerInfo(vo);
	}
}
