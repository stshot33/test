package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dto.Member;

public class MemberDAO {
	private static MemberDAO instance;

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

	// 싱글톤
	public static MemberDAO getInstance() {
		// 스레드 동기화
		if (instance == null) {
			synchronized (MemberDAO.class) {
				instance = new MemberDAO();
			}
		}
		return instance;
	}

	/**
	 * 회원 등록
	 * @param memberDTO	회원정보
	 */
	public void insert(Member memberDTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "INSERT INTO memberinfo(member_Id, member_Pw, member_Email, member_Name, member_Year, member_Sex, member_SignDate, member_LoginDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberDTO.getId());
			pstmt.setString(2, memberDTO.getPw());
			pstmt.setString(3, memberDTO.getEmail());
			pstmt.setString(4, memberDTO.getName());
			pstmt.setInt(5, memberDTO.getYear());
			pstmt.setString(6, memberDTO.getSex());
			pstmt.setTimestamp(7, Timestamp.valueOf(memberDTO.getSignDate()));
			pstmt.setTimestamp(8, Timestamp.valueOf(memberDTO.getLoginDate()));

			pstmt.executeUpdate();
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
	 * 아이디, 이메일 조회
	 * @param IDorEmail	아이디 또는 이메일
	 * @return			조회값이 있으면 true, 없으면 false
	 */
	public boolean selectByexist(String IDorEmail) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		String SQL = null;

		if (IDorEmail.contains("@")) {
			SQL = "SELECT * FROM memberinfo WHERE member_Email = ?";
		} else {
			SQL = "SELECT * FROM memberinfo WHERE member_Id = ?";
		}

		try {
			conn = getConnection();

			String sql = SQL;

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, IDorEmail);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				result = true;
			}

			rs.close();

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
	 * 아이디 또는 이메일 출력
	 * @param num	아이디 또는 이메일을 구분하기 위한 값
	 * @param value	아이디 고유값
	 * @return		해당 아이디 또는 이메일의 값 출력
	 */
	public String selectByinfo(int num, int value) {
		// 아이디 출력 -> 0
		// 이메일 출력 -> 1
		Connection conn = null;
		PreparedStatement pstmt = null;
		String result = null;
		String sql = null;

		try {
			conn = getConnection();

			sql = "SELECT * FROM memberinfo WHERE member_value = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, value);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				if (num == 0) {
					result = rs.getString("member_Id");
				} else {
					result = rs.getString("member_Email");
				}
			}

			rs.close();

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
	 * 로그인 또는 이메일 확인 후 고유값 전달 
	 * @param id	아이디
	 * @param pw	비밀번호
	 * @param email	이메일
	 * @return		회원 고유값
	 */
	public int selectByIdValue(String id, String pw, String email) {
		// 입력할 값이 없을 땐 null을 입력해줌
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String SQL = null;

		if (email != null) {
			SQL = "SELECT * FROM memberinfo WHERE member_Email = ?";
			result = 1;
		} else {
			SQL = "SELECT member_value FROM memberinfo WHERE member_Id = ? AND member_Pw = ?";
		}

		try {
			conn = getConnection();

			String sql = SQL;

			pstmt = conn.prepareStatement(sql);

			if (result == 1) {
				pstmt.setString(1, email);
			} else {
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
			}

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("member_value");
			}

			rs.close();

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
	 * 탈퇴상태 확인
	 * @param IDorEmail	아이디 또는 이메일
	 * @return			0이면 회원상태 1이면 탈퇴상태
	 */
	public int selectBystatus(String IDorEmail) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String SQL = null;

		if (IDorEmail.contains("@")) {
			SQL = "SELECT * FROM memberinfo WHERE member_Email = ?";
		} else {
			SQL = "SELECT * FROM memberinfo WHERE member_Id = ?";
		}

		try {
			conn = getConnection();

			String sql = SQL;

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, IDorEmail);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("member_status");
			}

			rs.close();

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
	 * 로그인 날짜 갱신
	 * @param date	로그인하는 날짜
	 * @param value	회원 고유값
	 */
	public void loginupdate(LocalDateTime date, int value) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "UPDATE memberinfo SET member_LoginDate = ? WHERE member_value = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setTimestamp(1, Timestamp.valueOf(date));
			pstmt.setInt(2, value);

			pstmt.executeUpdate();
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
	 * 비밀번호 재설정
	 * @param pw	변경할 비밀번호
	 * @param value	회원 고유값
	 */
	public void changepw(String pw, int value) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "UPDATE memberinfo SET member_Pw = ? WHERE member_value = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, pw);
			pstmt.setInt(2, value);

			pstmt.executeUpdate();
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
	 * 회원 탈퇴 기능
	 */
	public void Withdrawal() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "UPDATE memberinfo SET member_Status = 1";

			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();
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