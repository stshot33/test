package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDAO;
import dao.MemberDAO;
import dto.CartWishDTO;

@WebServlet("/bookadd")
public class BookAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		BookDAO bookdao = new BookDAO();
		MemberDAO memberdao = new MemberDAO();
		CartWishDTO dto = new CartWishDTO();
		
		String email = (String) session.getAttribute("email");
		int heart = Integer.parseInt(request.getParameter("heart"));
		int cart = Integer.parseInt(request.getParameter("cart"));
		int id = memberdao.selectByIdValue(null, null, email);
		int value = 0;
		boolean result;
		
		// 위시리스트
		if(heart != 0 || cart == 0) {
			result = bookdao.selectByList(value, heart, id);
			
			// DTO에 책 고유번호와 아이디 고유번호를 저장
			
			dto.setCbook(heart);
			dto.setCid(id);
			
		}else {
			// 카트
			value = 1;
			result = bookdao.selectByList(value, cart, id);
			
			// DTO에 책 고유번호와 아이디 고유번호를 저장
			dto.setCbook(cart);
			dto.setCid(id);
			
		}
		
		if(result) {
			// DB에 저장하려는 값이 없으면 저장
			bookdao.bookadd(value, dto);
		} else {
			// 값이 있으면 데이터 삭제
			bookdao.bookdelete(value, dto);
		}
		
		response.sendRedirect("/jsp/bookInfo.jsp");
	}
}