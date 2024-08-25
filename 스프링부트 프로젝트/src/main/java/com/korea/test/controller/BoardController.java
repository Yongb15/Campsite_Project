package com.korea.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korea.test.common.Common;
import com.korea.test.common.Common.Board;
import com.korea.test.service.BoardService;
import com.korea.test.util.Paging;
import com.korea.test.vo.BoardVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	HttpServletRequest request;
	
	@GetMapping("notices_board")
	public String list(Model model, @RequestParam(name="page",required=false, defaultValue="1") int page) {
		
		int start = (page-1) * Common.Board.BLOCKLIST+1;
		int end = start + Common.Board.BLOCKLIST-1;
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		
		//페이지 번호에 따른 전체 게시글 조회
		List<BoardVO> list = boardService.selectList(map);
		
		model.addAttribute("list",list);
		
		session.removeAttribute("show");
		
		//전체 게시물 수 구하기
		int rowTotal = boardService.getRowTotal();
		
		//페이지 메뉴 생성하기
		String pageMenu = Paging.getPaging("board_list", 
											page, 
											rowTotal, 
											Board.BLOCKLIST, 
											Board.BLOCKPAGE);
		
		model.addAttribute("pageMenu",pageMenu);
		
		return "/notices_board";
	}
	
	@GetMapping("events_board")
	public String events_board(Model model, @RequestParam(name="page",required=false, defaultValue="1") int page) {
		
		int start = (page-1) * Common.Board.BLOCKLIST+1;
		int end = start + Common.Board.BLOCKLIST-1;
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		
		//페이지 번호에 따른 전체 게시글 조회
		List<BoardVO> list = boardService.selectList(map);
		
		model.addAttribute("list",list);
		
		session.removeAttribute("show");
		
		//전체 게시물 수 구하기
		int rowTotal = boardService.getRowTotal();
		
		//페이지 메뉴 생성하기
		String pageMenu = Paging.getPaging("board_list", 
											page, 
											rowTotal, 
											Board.BLOCKLIST, 
											Board.BLOCKPAGE);
		
		model.addAttribute("pageMenu",pageMenu);
		
		return "/events_board";
	}
	
	@GetMapping("view")
	public String view(Model model,int idx, @RequestParam(name = "page",required=false, defaultValue="1") int page) {
		BoardVO vo = boardService.selectOne(idx);
		
		model.addAttribute("vo",vo);
		
		//조회수 증가하기
		
		//1. 특정 key를 가진 세션을 호출
		String show = (String) session.getAttribute("show");
		//2. value가 null이면 넘어온 idx의 readhit를 1증가시키고
		//세션 key에 value를 세팅
		if(show == null) {
			int res = boardService.update_readhit(idx);
			session.setAttribute("show", "r");
		}
		
		return "/board_view";
	}	
	
	@GetMapping("views")
	public String views(Model model,int idx, @RequestParam(name = "page",required=false, defaultValue="1") int page) {
		BoardVO vo = boardService.selectOne(idx);
		
		model.addAttribute("vo",vo);
		
		//조회수 증가하기
		
		//1. 특정 key를 가진 세션을 호출
		String show = (String) session.getAttribute("show");
		//2. value가 null이면 넘어온 idx의 readhit를 1증가시키고
		//세션 key에 value를 세팅
		if(show == null) {
			int res = boardService.update_readhit(idx);
			session.setAttribute("show", "r");
		}
		
		return "/board_views";
	}	
	
	@GetMapping("insert_form")
	public String insert_form(Model model,@RequestParam(name = "page",required=false,defaultValue="1")int page) {
		model.addAttribute("vo",new BoardVO());
		
		//반환값에다가 내가 보내줄 view의 경로
		return "/insert_form";
	}
	
	 @PostMapping("/insert")
     @ResponseBody
     public String insert(@RequestBody String body, @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
         ObjectMapper om = new ObjectMapper();
         Map<String, String> data;

         // JSON 바디 파싱
         try {
             data = om.readValue(body, new TypeReference<Map<String, String>>() {});
         } catch (Exception e) {
             e.printStackTrace(); // 예외 로그
             return "{\"param\":\"error\"}";
         }

         // 필드 추출
         String subject = data.get("subject");
         String name = data.get("name");
         String content = data.get("content");
         String pwd = data.get("pwd");

         // 값 로그
         System.out.println("Received data: ");
         System.out.println("Subject: " + subject);
         System.out.println("Name: " + name);
         System.out.println("Content: " + content);
         System.out.println("Password: " + pwd);

         // 입력값 검증 및 데이터베이스 저장
         try {
             // BoardVO 객체 생성 및 데이터 세팅
             BoardVO newVo = new BoardVO();
             newVo.setSubject(subject);
             newVo.setName(name);
             newVo.setContent(content);
             newVo.setPwd(pwd);

             // 데이터베이스에 데이터 저장 후, 올바르게 저장되었는지 검증
             try {
                 // 데이터베이스에 데이터 저장
                 int res = boardService.insert(newVo);
              
                 if (res==0) {
                     return "{\"param\":\"no\"}";
                 } 
                 return "{\"param\":\"yes\"}";
             } catch (Exception e) {
                 e.printStackTrace();
                 return "{\"param\":\"error\"}";
             }
         } catch (Exception e) {
             e.printStackTrace();
             return "{\"param\":\"error\"}";
         }
     }

	@PostMapping("del")
	@ResponseBody
	public String del(@RequestBody String body) {
		ObjectMapper om = new ObjectMapper();
		Map<String, String> data = null;
		
		try {
			data = om.readValue(body, new TypeReference<Map<String, String>>() {});
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int intIdx = Integer.parseInt(data.get("idx"));
		
		BoardVO vo = boardService.selectOne(intIdx);
		
		vo.setSubject("deleted");
		vo.setName("unknown");
		int res = boardService.del_update(vo);
		
		if(res > 0) {
			return "{\"param\":\"success\"}";
		}
		return "{\"param\":\"fail\"}";
		
	}
	
	@GetMapping("getSubjects")
	@ResponseBody
	public Map<String, Object> getSubjects(@RequestParam(name="page", required=false, defaultValue="1") int page) {
	    int start = (page - 1) * Common.Board.BLOCKLIST + 1;
	    int end = start + Common.Board.BLOCKLIST - 1;

	    HashMap<String, Integer> map = new HashMap<>();
	    map.put("start", start);
	    map.put("end", end);

	    // 제목만 가져오는 서비스 메소드 호출
	    List<String> subjects = boardService.selectSub(map);

	    // 전체 게시물 수 구하기
	    int rowTotal = boardService.getRowTotal();

	    // 페이지 메뉴 생성하기
	    String pageMenu = Paging.getPaging("events_board", 
	                                        page, 
	                                        rowTotal, 
	                                        Common.Board.BLOCKLIST, 
	                                        Common.Board.BLOCKPAGE);

	    Map<String, Object> response = new HashMap<>();
	    response.put("subjects", subjects);
	    response.put("rowTotal", rowTotal);
	    response.put("pageMenu", pageMenu);

	    return response;
	}
}
