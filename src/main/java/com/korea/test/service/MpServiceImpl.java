package com.korea.test.service;

import org.springframework.stereotype.Service;

import com.korea.test.dao.MpDAO;
import com.korea.test.vo.MpVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MpServiceImpl implements MpService{
	private final MpDAO mpDAO;
	
	@Override
	public MpVO loginCheck(String id) {
		
		return mpDAO.loginCheck(id);
	}

	@Override
	public MpVO findUserId(String id) {
		return mpDAO.findUserId(id);
	}

	@Override
	public int userSave(MpVO vo) {
		return mpDAO.userSave(vo);
	}
}
