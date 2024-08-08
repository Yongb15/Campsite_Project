package com.korea.test.service;

import com.korea.test.vo.MpVO;

public interface MpService {
	public MpVO loginCheck(String id);
	
	public MpVO findUserId(String id);
	
	public int userSave(MpVO vo);
}
