package com.korea.test.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController { 
	@Autowired
	HttpSession session;
	
	@RequestMapping("/main")
	public String mian(Model model) {	
		return "main";
	}
	
	@RequestMapping("/reservation_form")
	public String reservation(Model model) {	
		return "reservation_form";
	}
}