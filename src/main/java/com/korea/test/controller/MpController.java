package com.korea.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korea.test.service.MpService;
import com.korea.test.vo.MpVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MpController {
	private final MpService mpService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpSession session;



	@RequestMapping("/login_form")
	public String login_form(@ModelAttribute("vo") MpVO vo) {

		return "/board/login_form";

	}
	
	// 로그아웃
	@GetMapping("/logout")
	public RedirectView logout() {
		session.removeAttribute("id");
		return new RedirectView("/main");
	}

	//로그인
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestBody String body) {

		ObjectMapper om = new ObjectMapper();

		Map<String, String> data = null;
		
		try {

			data = om.readValue(body, new TypeReference<Map<String,String>>(){});

		} catch (Exception e) {

		}

		String id = data.get("id");
		String pwd = data.get("password");

		MpVO vo = mpService.loginCheck(id);

		if(vo == null) {
			return "{\"param\":\"no_id\"}";
		}

		if(!vo.getPassword().equals(pwd)) {
			return "{\"param\":\"no_pwd\"}";
		}

		session.setAttribute("id", vo);

		return "{\"param\":\"clear\"}";
		
	}



	@RequestMapping("/modify_mypage")
	public String myPage(@ModelAttribute("vo") MpVO vo) {
		return "/board/modify_mypage";
	}



	@RequestMapping("/modiMypage")
	public RedirectView myPage(Model model, @RequestParam(required = false, defaultValue = "1") int idx) {
		model.addAttribute("idx", idx);

		MpVO vo = mpService.selectOne(idx);
		vo.setId("id");
		model.addAttribute("vo", vo);

		//db에 추가하기
		int res = mpService.customerInfo(vo);

		if(res > 0) {
			return new RedirectView("/main");
		}
		return null;
	}


	@RequestMapping("/customerInfo")
	@ResponseBody
	public String cus_update(@RequestBody String body) {

		ObjectMapper om = new ObjectMapper();
		Map<String, String> data = null;

		try {

			data = om.readValue(body, new TypeReference<Map<String,String>>(){

			});

		} catch (Exception e) {

		}

		int Iidx = Integer.parseInt(data.get("idx"));

		MpVO vo = mpService.selectOne(Iidx);

		int res = mpService.customerInfo(vo);

		if(res > 0) {
			return "{\"param\" : \"success\"}";
		}

		return "{\"param\":\"fail\"}";
	}
}
