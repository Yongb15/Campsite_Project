	package com.korea.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.korea.test.dao.BoardDAO;
import com.korea.test.vo.BoardVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	private final BoardDAO boardDAO;

	@Override
	public List<BoardVO> selectList(HashMap<String, Integer> map) {
		return boardDAO.selectList(map);
	}

	@Override
	public int getRowTotal() {
		return boardDAO.getRowTotal();
	}

	@Override
	public BoardVO selectOne(int idx) {
		return boardDAO.selectOne(idx);
	}

	@Override
	public int update_readhit(int idx) {
		return boardDAO.update_readhit(idx);
	}

	@Override
	public int insert(BoardVO vo) {
		return boardDAO.insert(vo);
	}

	@Override
	public int del_update(BoardVO vo) {
		return boardDAO.del_update(vo);
}
	
	@Override
	public List<String> selectSub(Map<String, Integer> map) {
		return boardDAO.selectSub(map);
	}
}