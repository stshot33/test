package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dto.BookDTO;
import dto.CartWishDTO;

public class BookDAO {

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
	 * 도서 작가 이름 조회
	 * @param i	도서 고유번호
	 * @return	도서 작가이름
	 */
	public String selectByPublisher(int i) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String result = null;
		
		try {
			conn = getConnection();

			String sql = "SELECT * FROM author WHERE a_value = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, i);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				result = rs.getString("a_Name");
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

		return result;
	}
	
	/**
	 * 도서 검색 결과 조회
	 * @param q	검색어
	 * @param n	0->검색기능 / 도서 고유번호
	 * @return	도서 정보
	 */
	public ArrayList<BookDTO> bookSearch(String q, int n) {
		
		ArrayList<BookDTO> list = new ArrayList<BookDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			
			conn = getConnection();
			if(n==0) {
				String sql = "SELECT * FROM book WHERE b_img LIKE ? AND b_Name LIKE ?";

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, "%L.webp%");
				pstmt.setString(2, "%" + q + "%");
			} else {
				String sql = "SELECT * FROM book WHERE b_value=?";
				
				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, n);

			}
			ResultSet rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				BookDTO bookdto = new BookDTO();

				bookdto.setBookvalue(rs.getInt("b_value"));
				bookdto.setBookimg(rs.getString("b_img"));
				bookdto.setBname(rs.getString("b_Name"));
//				bookdto.setAuthor(rs.getString(("a_value")));
				bookdto.setBgrade(rs.getInt("b_Grade"));
				bookdto.setReview(rs.getInt("review_value"));
				bookdto.setBpublisher(rs.getString("b_Publisher"));
				bookdto.setCategory(rs.getInt("ct_value"));
				bookdto.setTotal(rs.getInt("b_Total"));
				bookdto.setBinfomation(rs.getString("b_Infomation"));
				bookdto.setBprice(rs.getInt("b_Price"));

				list.add(bookdto);
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

		return list;
	}

	/**
	 * 카트 또는 위시리스트 해당 아이디와 책이 있는지 조회
	 * @param value		카트 또는 위시리스트를 구분하기 위한 값
	 * @param bookvalue	도서 고유번호
	 * @param id		아이디 고유번호
	 * @return			조회값이 있으면 false, 없으면 true
	 */
	public boolean selectByList(int value, int bookvalue, int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = true;
		int su = value;
		String ifSQL = null;

		if (su == 0) {
			ifSQL = "SELECT * FROM wish WHERE b_value=? AND member_value=?";
		} else {
			ifSQL = "SELECT * FROM cart WHERE b_value=? AND member_value=?";
		}

		try {
			conn = getConnection();

			String sql = ifSQL;

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, bookvalue);
			pstmt.setInt(2, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				result = false;
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

		return result;
	}

	/**
	 * 카트 또는 위시리스트에 해당 도서 저장
	 * @param value		카트 또는 위시리스트를 구분하기 위한 값
	 * @param cartwish	DTO에 저장된 값
	 */
	public void bookadd(int value, CartWishDTO cartwish) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int su = 0;
		String ifSQL = null;

		if (su == 0) {
			ifSQL = "INSERT INTO wish(b_value, member_value) VALUES(?, ?)";
		} else {
			ifSQL = "INSERT INTO cart(b_value, member_value) VALUES(?, ?)";
		}

		try {
			conn = getConnection();

			String sql = ifSQL;

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, cartwish.getCbook());
			pstmt.setInt(2, cartwish.getCid());

			su = pstmt.executeUpdate();
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
	}

	/**
	 * 카트 또는 위시리스트에 저장된 데이터 삭제
	 * @param value	카트 또는 위시리스트를 구분하기 위한 값
	 * @param cart	DTO에 저장된 값
	 */
	public void bookdelete(int value, CartWishDTO cart) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int su = value;
		String ifSQL = null;

		if (su == 0) {
			ifSQL = "DELETE FROM wish WHERE b_value = ? AND member_value = ?";
		} else {
			ifSQL = "DELETE FROM cart WHERE b_value = ? AND member_value = ?";
		}

		try {
			conn = getConnection();

			String sql = ifSQL;

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, cart.getCbook());
			pstmt.setInt(2, cart.getCid());

			su = pstmt.executeUpdate();
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
	}
}