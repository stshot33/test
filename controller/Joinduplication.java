package controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import check.Check.*;
import dao.MemberDAO;
import dto.Member;

@WebServlet("/join/duplication")
public class Joinduplication extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		BlankDelete blank = new BlankDelete();
		RegularExpression infocheck = new RegularExpression();
		
		int problem = 0;
		
		// 데이터 가져옴
		String id = request.getParameter("id");
		String pw = request.getParameter("pwd");
		String pwCheck = request.getParameter("pwdCheck");
		String email = request.getParameter("mail");
		String name = request.getParameter("name");
		int year = request.getParameter("year") == null ? 0 : Integer.parseInt(request.getParameter("year"));
		String sex = request.getParameter("gender") == null ? "x" : request.getParameter("gender");
		LocalDateTime signDate = LocalDateTime.now();
		LocalDateTime loginDate = signDate;

		// 공백 제거
		if(id != null) blank.StringDelete(id);  
		if(pw != null) blank.StringDelete(pw);
		if(pwCheck != null) blank.StringDelete(pwCheck);
		if(email != null) blank.StringDelete(email);
		if(name != null) blank.StringDelete(name);

		// 인스턴스 생성
		MemberDAO memberDAO = MemberDAO.getInstance();

		// 아이디 중복 체크 및 상태 확인, 형식 확인
		if (memberDAO.selectByexist(id) || memberDAO.selectBystatus(id) != 0 || !infocheck.idcheck(id)) {
			problem = 1;
		} 
		
		// 비밀번호와 비밀번호 확인이 동일한지 확인 및 형식 확인
		if (!pw.equals(pwCheck) || !infocheck.pwcheck(pw) || pw.equals(id)) {
			problem = 1;
		}
		
		// 이메일 중복 체크 및 상태 확인, 형식 확인
		if (memberDAO.selectByexist(email) || memberDAO.selectBystatus(email) != 0 || !infocheck.mailcheck(email)) {
			problem = 1;
		}
		
		// 이름 형식 확인
		if (!infocheck.namecheck(name)) {
			problem = 1;
			
		}
		
		if(problem == 0) {
			// DTO에 데이터 저장
			Member member = new Member();
			member.setId(id);
			member.setPw(pw);
			member.setPwCheck(pwCheck);
			member.setEmail(email);
			member.setName(name);
			member.setYear(year);
			member.setSex(sex);
			member.setSignDate(signDate);
			member.setLoginDate(loginDate);
	
			response.setStatus(HttpServletResponse.SC_OK);
			memberDAO.insert(member); // 회원정보 DB 저장
			response.sendRedirect("http://localhost/main/main.jsp"); // 회원가입 완료 시 메인 페이지로 이동
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendRedirect("http://localhost/jsp/join2.jsp");
		}
	}
}