package com.korea.test.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ReservationVO {
	private int idx;					// 기본키 넘버
	private String Date;				// 날짜		// 유니크
	private int num_people;				// 인원 수	// 유니크
	private String camping_idx;			// 캠핑장 번호
	private int price;
	private String res_date;			// 현재 예약한 시간
	private String cus_id;					// 사용자 id
}
