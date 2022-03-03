package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewBookDAO;
import dto.NewBook;

@WebServlet("/main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewBookDAO newbook = new NewBookDAO();
		ArrayList<NewBook> newBook = new ArrayList<NewBook>();
		ArrayList<NewBook> newWeek = new ArrayList<NewBook>();
		ArrayList<NewBook> bestseller = new ArrayList<NewBook>();
		ArrayList<NewBook> nowList = new ArrayList<NewBook>();
		ArrayList<NewBook> todayList = new ArrayList<NewBook>();
		ArrayList<NewBook> instaList = new ArrayList<NewBook>();
		
		newBook = newbook.NewBookList(1);
		nowList = newbook.NewBookList(2);
		todayList = newbook.NewBookList(3);
		bestseller = newbook.NewBookList(4);
		newWeek = newbook.NewBookList(5);
		instaList = newbook.NewBookList(6);

		request.setAttribute("newBook", newBook);
		request.setAttribute("newWeek", newWeek);
		request.setAttribute("best", bestseller);
		request.setAttribute("now", nowList);
		request.setAttribute("today", todayList);
		request.setAttribute("insta", instaList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/index.jsp");
		rd.forward(request, response);
	}
}