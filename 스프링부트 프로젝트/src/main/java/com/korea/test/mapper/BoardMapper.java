package com.korea.test.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.korea.test.vo.BoardVO;

@Mapper
public interface BoardMapper {
public List<BoardVO> selectList(HashMap<String, Integer> map);
	
	//전체게시물조회
	public int getRowTotal();
	
	public BoardVO selectOne(int idx);
	
	public int update_readhit(int idx);
	
	public int insert(BoardVO vo);
	
	public int del_update(BoardVO vo);
	
	public List<String> selectSub(Map<String, Integer> map);
}
