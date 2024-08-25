package com.korea.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.test.vo.ReservationVO;

@Mapper
public interface ReservationMapper {

	int res_insert(ReservationVO vo);

	List<String> findReservedSeatsByDate(String date);
}
