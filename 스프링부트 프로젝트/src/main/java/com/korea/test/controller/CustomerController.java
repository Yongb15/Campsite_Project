package com.korea.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
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
import com.korea.test.service.CustomerService;
import com.korea.test.service.CustomerServiceImpl;
import com.korea.test.vo.CustomerVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CustomerController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	private final CustomerService customerService;
	private final CustomerServiceImpl customerServiceImpl;
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("vo", new CustomerVO());
		return "signup";
	}
	
	@PostMapping("/signup_page")
    public RedirectView signUpPage(CustomerVO vo) {
		int res = customerServiceImpl.signUpPage(vo);
		
		if(res > 0) {
			return new RedirectView("/main");
		}
		return null;
	} 

	@PostMapping("/check_id")
	@ResponseBody
	public String check_id(@RequestBody String id, CustomerVO vo) {
		ObjectMapper om = new ObjectMapper();
		
		Map<String, String> data = null;			// key 값을 통해서 value를 얻어옴
		
		try {
			data = om.readValue(id, new TypeReference<Map<String, String>>() {});
		} catch (Exception e) {
			
		}
		
		id = data.get("id");	
		
		vo = customerService.checkId(id);
		
		if(vo == null) {
			return "{\"param\":\"yes\"}";
		}
		session.setAttribute("id", vo);
		return "{\"param\":\"no\"}";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("vo", new CustomerVO());
		return "login";
	}
	
	@RequestMapping("/loginCheck")
	@ResponseBody
	public String loginCheck(@RequestBody String body) {
		ObjectMapper om = new ObjectMapper();
		
		Map<String, String> data = null;			// key 값을 통해서 value를 얻어옴
		
		try {
			data = om.readValue(body, new TypeReference<Map<String, String>>() {});
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String id = data.get("id");
		String password = data.get("password");
		
		System.out.println(id);
		
		CustomerVO vo = customerService.loginCheck(id);
		
		if(vo == null || !vo.getPassword().equals(password)) {
			return "{\"param\":\"no\"}";
		}
		session.setAttribute("id", vo);
		return "{\"param\":\"yes\"}";
	}
	
	@GetMapping("/logout")
	public RedirectView logout() {
		session.removeAttribute("id");
		return new RedirectView("/main");
	}
	
	@GetMapping("/checkLoginStatus")
	@ResponseBody
	public Map<String, Boolean> checkLoginStatus() {
	        Map<String, Boolean> response = new HashMap<>();
	        boolean isLoggedIn = session.getAttribute("id") != null;
	        response.put("loggedIn", isLoggedIn);
	        return response;
	}
	
	/*------------------------------------------------------- 애지님 구현*/
	
	//회원정보 페이지
	@RequestMapping("/myPage")
	public String myForm(Model model, @RequestParam(name="id",required = false, defaultValue = "1")String id) {
		
		session = request.getSession(true);
		
		CustomerVO sessionId = (CustomerVO)session.getAttribute("id");
		System.out.println("Session object class: " + (session != null ? session.getClass().getName() : "null"));
		
		CustomerVO list = customerService.findUserId(sessionId.getId());
		
		model.addAttribute("list", list);
		model.addAttribute("sessionId", sessionId);
		
		return "myPage";
	}
	
	//회원정보 수정페이지 보기
	@RequestMapping("/modify_mypage")
	public String modiPage(Model model, @RequestParam(name="id",required = false, defaultValue = "1")String id) {

		session = request.getSession(true);
			
		CustomerVO sessionId = (CustomerVO)session.getAttribute("id");
		System.out.println(sessionId);
		
		CustomerVO list = customerService.findUserId(sessionId.getId());			
		model.addAttribute("list", list);
		model.addAttribute("sessionId", sessionId);
			
		return "/modify_mypage";
	}	
		
	//수정된 회원정보 저장
	@PostMapping("userSave")
	@ResponseBody
	public String userSave(@RequestBody String body, Model model, CustomerVO vo) {
		
		ObjectMapper om = new ObjectMapper();
		Map<String, String> data = null;
		
		try {
			//{"id" : "rururu"}
			data = om.readValue(body, new TypeReference<Map<String,String>>(){
				
			});
				
		} catch (Exception e) {
				
		}
			
		//Map에 들어있는 키를 가져온다(data)
		vo = customerService.findUserId(data.get("id"));
		
		vo.setNickName(data.get("nickName"));
		vo.setEmail(data.get("email"));
		vo.setPhoneNumber(data.get("phoneNumber"));
		vo.setPassword(data.get("password"));
			
		model.addAttribute("vo", vo);
			
		//db에 넣어준다
		int res = customerService.userSave(vo);
		
		if(res > 0) {
			//수정된 정보를  session에 덮어씌움
			session.setAttribute("id", vo);
			
			return "{\"param\":\"success\"}";
		}
		
		return "{\"param\":\"fail\"}";
		}
		
	//회원 탈퇴 페이지
	@RequestMapping("delete_form")
	public String delete_form(@ModelAttribute("vo") CustomerVO vo) {
		
		return "/user_withdraw";
	}
		
	//회원 탈퇴하기
	@RequestMapping("userDelete")
	public String user_delete(@RequestParam(name="id",required = false, defaultValue = "1") String id) {
		
		CustomerVO sessionId = (CustomerVO)session.getAttribute("id");
			
		customerService.userDel(sessionId);
			
		//세션 삭제하기
		session.invalidate();
		
		return "redirect:/main";
		
	}
		
	//비밀번호 변경 페이지
	@RequestMapping("newPassword")
	public String newPwd(Model model, @RequestParam(name="id",required = false, defaultValue = "1")String id) {
		
		session = request.getSession(true);
			
		CustomerVO sessionId = (CustomerVO)session.getAttribute("id");
		System.out.println(sessionId);
			
		CustomerVO list = customerService.findUserId(sessionId.getId());
			
		model.addAttribute("list", list);
		model.addAttribute("sessionId", sessionId);
			
		return "/password_change";
	}
		
	//비밀번호 변경하기
	@PostMapping("/changePwd")
	@ResponseBody
	public String changePwd(@RequestBody String body, Model model) {
			
		ObjectMapper om = new ObjectMapper();
		Map<String, String> data = null;
			
		try {
				data = om.readValue(body, new TypeReference<Map<String,String>>(){	
			});
	
			} catch (Exception e) {
				
			}
	
		System.out.println(data);
			
		CustomerVO vo = customerService.findUserId(data.get("id"));
		System.out.println(vo);
			
		vo.setPassword(data.get("newPassword"));
		System.out.println("vo : " + vo);
			
		model.addAttribute("vo", vo);
			
		//db에 넣어주기(업데이트)
		int res = customerService.changePwd(vo);
		System.out.println(res);
			
		if(res > 0) {
			//수정된 정보를  session에 덮어씌움
			session.setAttribute("id", vo);
				
			return "{\"param\":\"success\"}";
		}
			
		return "{\"param\":\"fail\"}";
	}
}
