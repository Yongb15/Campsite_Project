package com.korea.test.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.korea.test.vo.ReservationVO;

@Mapper
public interface ReservationMapper {

	int res_insert(ReservationVO vo);

}
