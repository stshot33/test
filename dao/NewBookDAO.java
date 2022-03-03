package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dto.NewBook;

public class NewBookDAO {
	public Connection getConnection() {
		Connection conn = null;

		try {
			InitialContext ic = new InitialContext();

			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/ridi");

			conn = ds.getConnection();

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * 신간도서 조회
	 * @param i	1->방금나온신간 2->지금많이읽는책 3->리디의발견 4->베스트셀러 5->금주의신간 6->인스타그램 추천도서
	 * @return	해당 조건에 따른 도서 정보
	 */
	public ArrayList<NewBook> NewBookList(int i) {
		ArrayList<NewBook> newlist = new ArrayList<NewBook>();
		BookDAO bookDAO = new BookDAO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String SQL = null;
		
		if(i==1) {
			// 방금 나온 신간
			SQL = "SELECT * FROM book ORDER BY b_addDate DESC";
		} else if(i==2) {
			// 사람들이 지금 많이 읽고 있는 책
			SQL = "SELECT * FROM book ORDER BY b_Purchase DESC, b_sellDate DESC";
		} else if(i==3) {
			// 오늘, 리디의 발견
			SQL = "SELECT * FROM book ORDER BY rand() LIMIT 12";
		} else if(i==4) {
			// 베스트 셀러
			SQL = "SELECT * FROM book ORDER BY b_Purchase DESC";
		} else if(i==5) {
			// 금주의 신간
			SQL = "SELECT * FROM book WHERE b_addDate BETWEEN DATE_ADD(NOW(),INTERVAL -1 WEEK ) AND NOW() ORDER BY b_addDate DESC";
		} else if(i==6) {
			// 인스타그램 추천도서
			SQL = "SELECT * FROM book ORDER BY b_Grade DESC, b_GradeCount DESC";
		}

		try {

			conn = getConnection();
			String sql = SQL;

			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				NewBook book = new NewBook();

				book.setImg(rs.getString("b_img"));
				book.setName(rs.getString("b_Name"));
				book.setPublisher(bookDAO.selectByPublisher(rs.getInt("a_value")));
				book.setValue(rs.getInt("b_value"));
				newlist.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return newlist;
	}
}