import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberDAO {

	// DAO -> DataBase Access Object
	// ������ ���̽��� �����ϱ� ���� ��ü�� ���� �� �ִ� Ŭ����
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;

	// ����̹� �ε��� Ŀ�ؼ� ��ü�� �������� �޼ҵ�
	private void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";
			try {
				conn = DriverManager.getConnection(db_url, db_id, db_pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// DataBase�� ������ �����ִ� �޼ҵ�
	private void close() {
		try {
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
				if (rs != null) {
					rs.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	Scanner sc = new Scanner(System.in);

	// �α��� ���
	public String login(String id, String pw) {

		String nick = null;
		try {

			// 2. Connection ����
			// Connection�� �����ϱ� ���ؼ���
			// DB�� ���Ӱ����� url, id, pw�� �ʿ��ϴ�
			String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";
			conn = DriverManager.getConnection(db_url, db_id, db_pw);

			// 3. SQL�� �ۼ� �� ����
			String sql = "select * from bigmember where id = ? and pw = ?";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();

			if (rs.next()) {
				nick = rs.getString("nick");

			} else {
				nick = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();

		}
		return nick;

	}

	// ȸ�� ���� ����� �Ű� �ּ���
	// �޼ҵ� �̸� -> join

	public int join(String id, String pw, String nick) {
		int cnt = 0;
		try {
			getConnection();

			// 3. SQL�� �ۼ� �� ����
			String sql = "insert into bigmember values(?, ?, ?)";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			psmt.setString(3, nick);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();

		}
		return cnt;

	}

	// ȸ�� ���� ���� update
	public int update(String id) {
		int cnt = 0;
		try {
			getConnection();

			// 3. SQL�� �ۼ� �� ����
			String sql = "update bigmember set nick = ? where id = ? ";

			psmt = conn.prepareStatement(sql);
			System.out.print("����� �г����� �Է����ּ���>>");
			psmt.setString(2, id);
			String nick = sc.next();
			psmt.setString(1, nick);

			cnt = psmt.executeUpdate(); // executeUpdate = sql���๮���� ������ ������

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();

		}
		return cnt;

	}

	public ArrayList<MemberDTO> selectAll() {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();

		getConnection();

		String sql = "select * from bigMember";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String nick = rs.getString("nick");
				MemberDTO m = new MemberDTO(id, pw, nick);
				list.add(m);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;

	}

	public int delete(String id, String pw) {
		int cnt = 0;
		getConnection();

		try {

			String sql = "delete from bigmember where id = ? and pw = ?";

			psmt = conn.prepareStatement(sql);
			psmt.setString(2, pw);
			psmt.setString(1, id);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();

		}
		return cnt;

	}

	public int adminUpdate(String change_id, String change_nick) {
		int cnt = 0;

		getConnection();

		try {
			String sql = "update bigmember set nick = ? where id = ?";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, change_nick);
			psmt.setString(2, change_id);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();

		}
		return cnt;
	}

	public int deleteId(String removeId) {
		int result = 0;

		getConnection();

		String sql = "delete from bigmember where id = ?";
		try {
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, removeId);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();

		}
		return result;

	}
}
