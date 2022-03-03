package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import check.Check.BlankDelete;
import check.Check.RegularExpression;
import dao.MemberDAO;

@WebServlet("/resetpw2")
public class ResetPw2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDAO memberdao = MemberDAO.getInstance();
		BlankDelete blank = new BlankDelete();
		RegularExpression pwdcheck = new RegularExpression();
		
		String pwd = request.getParameter("pwd");
		String pwd2 = request.getParameter("pwd2");
		int value = Integer.parseInt(request.getParameter("value"));
		
		blank.StringDelete(pwd);
		
		if(pwd == pwd2 || pwdcheck.pwcheck(pwd)) {
		memberdao.changepw(pwd, value);
		response.sendRedirect("/jsp/login.jsp");
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendRedirect("/jsp/resetpw2.jsp");
		}
		
		
	}

}
