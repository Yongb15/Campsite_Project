package com.korea.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.korea.test.service.ReservationService;
import com.korea.test.vo.CustomerVO;
import com.korea.test.vo.ReservationVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

@RequestMapping("/")
public class ReservationController {

	@Autowired
	private HttpSession session;
	
	private final ReservationService reservationService;
	
	@GetMapping("/myPage_form")	// my페이지로 이동
	public String myPage(Model model) {
		CustomerVO vo = (CustomerVO)session.getAttribute("id");
		System.out.println(vo);
		
		model.addAttribute("vo", vo);
		
		return "/board/myPage";
	}
	
	@GetMapping("/main")	// 메인 페이지로 이동
	public String mian(Model model) {	
		return "main";
	}
	
	@GetMapping("/reservation_form")	// 예약 페이지 이동
	public String reservation(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("vo", new ReservationVO());
		CustomerVO vo = (CustomerVO)session.getAttribute("id");
	      
	      if (vo == null) {
	         // 로그인되지 않은 경우, 오류 메시지와 함께 main 페이지로 리다이렉트
	           redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
	            return "redirect:/main"; // 로그인 페이지로 리다이렉트
	        }
	      else {
	         String id = vo.getId();
	         System.out.println(id);
	         
	         model.addAttribute("id", id);
	      }
	      return "reservation_form";
	}
	
	@PostMapping("reservation_insert")						// 예약 테이블에 저장
	public RedirectView res_insert(String id, ReservationVO vo, RedirectAttributes redirectAttributes)		// String id는 session에서 id를 받아와 @{/reservation_insert(id=${id})}로 같이 넘김
	{
		if(vo.getDate() == "") {
	         redirectAttributes.addFlashAttribute("errorMessage", "날짜를 선택해주세요.");
	         return new RedirectView("reservation_form");
	      }
		vo.setCus_id(id);				// 그 후 setter로 값 변경
		
		System.out.println("번호 : " + vo.getCamping_idx());
		System.out.println("id : " + vo.getCus_id());
		System.out.println("날짜 : " + vo.getDate());
		System.out.println("인원 : " + vo.getNum_people()+"명");
		
		// 넘어온 데이터
		try {
			
			int res = reservationService.res_insert(vo);
			
			if(res > 0) {
				redirectAttributes.addFlashAttribute("successMessage", "예약이 완료되었습니다!");
				return new RedirectView("main");
			}
		} catch (DataIntegrityViolationException e) {					// 이미 DB에 값이 존재할 경우
			System.out.println("에러");
			if(vo.getDate() == "") {
				redirectAttributes.addFlashAttribute("errorMessage", "날짜를 선택해주세요!");
			}
			else {
				redirectAttributes.addFlashAttribute("errorMessage", "이미 예약이 되었습니다!");
			}
		} catch (Exception e) {											// 날짜를 선택 안할 경우
			if(vo.getCamping_idx() == null) {
				redirectAttributes.addFlashAttribute("errorMessage", "좌석을 지정해주세요!");
			}
			else {
				redirectAttributes.addFlashAttribute("errorMessage", "날짜를 지정해주세요!");
			}
        }
		return new RedirectView("reservation_form");
	}
	
	
	 @GetMapping("findReservedSeatsByDate")
     @ResponseBody // JSON 응답을 반환하기 위해 추가
     public List<String> findReservedSeatsByDate(@RequestParam String date) {
         return reservationService.findReservedSeatsByDate(date);
     }
}