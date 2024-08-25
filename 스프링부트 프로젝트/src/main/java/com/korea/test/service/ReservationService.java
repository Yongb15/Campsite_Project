package com.korea.test.service;

import java.util.List;

import com.korea.test.vo.ReservationVO;

public interface ReservationService {

	int res_insert(ReservationVO vo);
	
	List<String> findReservedSeatsByDate(String date);

}
