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
	public String myPage(Model model) {

		MpVO vo = (MpVO)session.getAttribute("id");
		System.out.println(vo);
		
		model.addAttribute("vo", vo);
		
		return "/board/modify_mypage";
	}



	@PostMapping("userSave")
	@ResponseBody
	public String userSave(@RequestBody String body) {
		ObjectMapper om = new ObjectMapper();
		Map<String, String> data = null;
		
		System.out.println(body);
		
		try {
			//{"id" : "rururu"}
			data = om.readValue(body, new TypeReference<Map<String,String>>(){
		});
			
		} catch (Exception e) {
			
		}
		
		System.out.println(data);
		System.out.println(data.get("id"));
		
		MpVO vo = mpService.findUserId(data.get("id"));
		
		vo.setNickName(data.get("nickName"));
		vo.setPhoneNumber(data.get("phoneNumber"));
		vo.setEmail(data.get("email"));
		
		System.out.println(vo);
		
		
		
		int res = mpService.userSave(vo);
		
		System.out.println(res);
		
		if(res > 0) {
			session.setAttribute("id", vo);
			
			return "{\"param\":\"success\"}";
		}
		
		return "{\"param\":\"fail\"}";
	}
}
