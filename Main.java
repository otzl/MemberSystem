import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// 1.�α��� 2.ȸ������ 3.ȸ����Ϻ��� 4.ȸ���������� 5.ȸ��Ż�� 6.����

		Scanner sc = new Scanner(System.in);
		System.out.println("====ȸ������ �ý���====");
		MemberDAO dao = new MemberDAO();
		while (true) {
			System.out.print("1.�α��� 2.ȸ������ 3.ȸ����Ϻ��� 4.ȸ���������� 5.ȸ��Ż�� 6.���� >>> ");
			int input = sc.nextInt();

			if (input == 1) {
				System.out.println("====�α���====");

				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();

				String nick = dao.login(id, pw);
				
				if(nick != null) {
					System.out.println(nick + "�� ȯ���մϴ�!");
					
					if(id.equals("admin")) {
						System.out.println("1.ȸ���������� 2.ȸ������");
						input = sc.nextInt();
						if(input == 1) {
							System.out.println("====������ ȸ����������");
							System.out.println("���̵� �Է� : ");
							String change_id = sc.next();
							System.out.println("������ �г��� �Է�");
							String change_nick = sc.next();
							
							int cnt = dao.adminUpdate(change_id, change_nick);
							
							if(cnt > 0) {
								System.out.println("ȸ������ ���� �Ϸ�");
							}else {
								System.out.println("ȸ������ ���� ����");
							}
						
							
							
							
						}else if(input == 2) {
							
						}
					}
					
					
					
				}else {
					System.out.println("�α��� ����...");
				}

			} else if (input == 2) {

				System.out.println("====ȸ������====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				System.out.print("�г��� �Է� : ");
				String nick = sc.next();
				int cnt = dao.join(id, pw, nick);
				
				if(cnt > 0) {
					System.out.println("ȸ������ ����");
				}else {
					System.out.println("ȸ������ ����");
				}
				// JDBC
				// 0. JDBC�� ����ϴ� ������Ʈ�� Driver ���ϳֱ�
				// 1. Driver �ε� (Oracle Driver) -> �����ε�
				// ���� ����ϴ� DBMS�� ����̹� �ε�

				

			} else if (input == 3) {
				// ȸ�� ��� ����
				System.out.println("====ȸ����Ϻ���====");
				ArrayList<MemberDTO> list = dao.selectAll();
				
				for(int i = 0; i < list.size(); i++) {
					
					MemberDTO m = list.get(i);
					
					System.out.print(m.getId() + " - ");
					System.out.print(m.getPw() + " - ");
					System.out.println(m.getNick());
				}
				
				
			} else if (input == 4) {
				// ȸ�� ���� ����
				// 0. ������Ʈ�� Driver ���� �ֱ�
				// 1. ����̹� �ε�
				// 2. Connection ����
				// 3. sql�� �ۼ� �� ����
				// update ���̺�� set �÷��� = �ٲٰ� ������ where ����
				// executeQuery -> ���̺� �����Ͱ� ���� ���� ��
				// ResultSet�� ��ȯ
				// executeUpdate -> ���̺��� ������ ����� ��
				// int Ÿ������ ��ȯ -> ����� sql���� ��
				// 4. finally
				// conn ��ü, psmt, rs
				// �ݾ��ִ� ���� : rs -> psmt -> conn

				// id -> pbk �� ȸ���� �г�����
				// 'ŷ����' ���� �ٲپ� �ּ���
				System.out.println("====ȸ�������� �������ּ���====");
				System.out.println("���̵� �Է� : ");
				String id = sc.next();
				int cnt = dao.update(id);
				
				if(cnt > 0) {
					System.out.println("ȸ������ ���� �Ϸ�");
				}else {
					System.out.println("ȸ������ ���� ����");
				}

		

				

			} else if (input == 5) {
				System.out.println("====ȸ��Ż��====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				
				int cnt = dao.delete(id, pw);
				
				if (cnt > 0) {
					System.out.println("ȸ������ �Ϸ�");
				}else {
					System.out.println("ȸ������ ����");
				}
				
				
			} else if (input == 6) {
				System.out.println("���α׷��� �����մϴ�...");
				break;
			} else {
				System.out.println("��Ȯ�� ���ڸ� �ٽ� �Է����ּ���.");
			}

		}

	}

}
