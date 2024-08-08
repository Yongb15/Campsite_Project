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

	public MpVO findUserId(String id) {
		return mpMapper.findUserId(id);
	}


	public int userSave(MpVO vo) {
		return mpMapper.userSave(vo);
	}
}
