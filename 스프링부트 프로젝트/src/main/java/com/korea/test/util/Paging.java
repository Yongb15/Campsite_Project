package com.korea.test.util;

public class Paging {

	public static String getPaging(String pageURL, int nowPage, int rowTotal, int blockList, int blockPage) {
		//pageURL : 목적지
		//nowPage : 현재페이지
		//rowTotal : 총 게시글 수
		//blockList : 한페이지에 보여질 게시글 수
		//blockPage : 한페이지에 보여질 메뉴의 수
		
		int totalPage; //전체페이지 수
		int startPage; //시작페이지 번호
		int endPage; //마지막 페이지 번호
		
		boolean isPrevPage;
		boolean isNextPage;
		
		StringBuffer sb;
		
		isPrevPage = isNextPage = false;
		
		//입력된 전체 자원을 통해 전체 페이지 수를 구한다.
		totalPage = rowTotal/blockList;
		if(rowTotal % blockList != 0) totalPage++;
		
		//만약 잘못된 연산과 움직임으로 인하여 현재 페이지 수가 전체 페이지수를 넘을 경우
		//강제로 현재페이지 값을 전체 페이지값으로 변경
		if(nowPage > totalPage) nowPage = totalPage;
		
		//시작 페이지와 마지막 페이지를 구한다.
		startPage = (int)(((nowPage-1)/blockPage)*blockPage+1);
		endPage = startPage + blockPage - 1;
		
		//마지막 페이지 수가 전체페이지수보다 크면 마지막페이지 값을 변경
		if(endPage > totalPage)endPage = totalPage;
		
		//마지막페이지가 전체페이지보다 작을 경우 다음페이징이 적용될 수 잇도록 값 변경하기
		if(endPage < totalPage)isNextPage = true;
		//시작페이지의 값이 1보다 작으면 이전페이징을 적용할 수 있도록 값을 설정
		if(startPage > 1)isPrevPage = true;
		
		//html태그 형태로 코드를 저장
		sb = new StringBuffer();
		//---그룹페이지 처리 이전-----------------------
		if(isPrevPage) {
			sb.append(startPage -1);
		}
		//----페이지목록출력-------------------------------
		sb.append(" ");
		for(int i = startPage; i<=endPage; i++) {
			if(i > totalPage) break;
			if(i == nowPage) {//현재페이지 일때
				sb.append("&nbsp;<b><font color='#ff0000'>");
				sb.append(i);
				sb.append("</font></b>");
			} else {//선택되지 않은 페이지
				sb.append("&nbsp;<a href='"+pageURL+"?page=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a>");
			}
		}
		
		sb.append("&nbsp; ");
		
		//----다음 버튼-----------------------------------
		if(isNextPage) {
			sb.append(endPage + 1);
		} 
		
		return sb.toString();
			
		
	}
}
