package com.korea.test.service;

import org.springframework.stereotype.Service;

import com.korea.test.dao.ReservationDAO;
import com.korea.test.vo.ReservationVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
	
	private final ReservationDAO reservationDAO;
	
	@Override
	public int res_insert(ReservationVO vo) {
		return reservationDAO.res_insert(vo);
	}
}
