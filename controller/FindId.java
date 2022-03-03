package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import check.Check.RegularExpression;
import dao.MemberDAO;

@WebServlet("/findId")
public class FindId extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		MemberDAO memberDAO = MemberDAO.getInstance();
		RegularExpression check = new RegularExpression();
		HttpSession session = request.getSession();

		String email = request.getParameter("email");
		int value = memberDAO.selectByIdValue(null, null, email);
		String id = null;
		
		if (check.mailcheck(email) && memberDAO.selectBystatus(email) == 0) {
			if (value != 0) {
				response.setStatus(HttpServletResponse.SC_FOUND);
				id = idchange(memberDAO.selectByinfo(0, value));
				session.setAttribute("id", id);
				response.sendRedirect("/jsp/findId2.jsp");
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.sendRedirect("/jsp/findId.jsp");
			}
		}else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendRedirect("/jsp/findId.jsp");
		}
	}
	
	private String idchange(String id) {
		String str1 = id.substring(0, 2);
		String str2 = id.substring(2, id.length());
		String result = null;
		
		str2 = str2.replaceAll("[a-z[0-9]]", "*");
		
		result = str1 + str2;
		
		return result;
	}
}