import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// 1.로그인 2.회원가입 3.회원목록보기 4.회원정보수정 5.회원탈퇴 6.종료

		Scanner sc = new Scanner(System.in);
		System.out.println("====회원관리 시스템====");
		MemberDAO dao = new MemberDAO();
		while (true) {
			System.out.print("1.로그인 2.회원가입 3.회원목록보기 4.회원정보수정 5.회원탈퇴 6.종료 >>> ");
			int input = sc.nextInt();

			if (input == 1) {
				System.out.println("====로그인====");

				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();

				String nick = dao.login(id, pw);
				
				if(nick != null) {
					System.out.println(nick + "님 환영합니다!");
					
					if(id.equals("admin")) {
						System.out.println("1.회원정보수정 2.회원삭제");
						input = sc.nextInt();
						if(input == 1) {
							System.out.println("====관리자 회원정보수정====");
							System.out.println("아이디 입력 : ");
							String change_id = sc.next();
							System.out.println("변경할 닉네임 입력");
							String change_nick = sc.next();
							
							int cnt = dao.adminUpdate(change_id, change_nick);
							
							if(cnt > 0) {
								System.out.println("회원정보 수정 완료");
							}else {
								System.out.println("회원정보 수정 실패");
							}
							
						}else if(input == 2) {
							System.out.println("====관리자 회원삭제====");
							
							// 문제 1. 회원의 아이디만 콘솔에 전부 출력하세요
							// 출력예시
							// 1. pbk
							// 2. hodoo
							// 3. lsh
							
							ArrayList<MemberDTO> list = dao.selectAll();
							int cnt = 1;
							for(int i = 0; i < list.size(); i++) {
								
								MemberDTO m = list.get(i);
								
								if(!m.getId().equals("admin")) {
									System.out.println(cnt + "." + m.getId());
									cnt++;
								}
							}		
							System.out.println("삭제할 아이디 입력 : ");
							String removeId = sc.next();
							int result = dao.deleteId(removeId);
							if(result > 0) {
								System.out.println("아이디 삭제 완료");
							}else {
								System.out.println("아이디 삭제 실패");
							}
						}
					}
					
					
					
				}else {
					System.out.println("로그인 실패...");
				}

			} else if (input == 2) {

				System.out.println("====회원가입====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();
				System.out.print("닉네임 입력 : ");
				String nick = sc.next();
				int cnt = dao.join(id, pw, nick);
				
				if(cnt > 0) {
					System.out.println("회원가입 성공");
				}else {
					System.out.println("회원가입 실패");
				}
				// JDBC
				// 0. JDBC를 사용하는 프로젝트에 Driver 파일넣기
				// 1. Driver 로딩 (Oracle Driver) -> 동적로딩
				// 내가 사용하는 DBMS의 드라이버 로딩

				

			} else if (input == 3) {
				// 회원 목록 보기
				System.out.println("====회원목록보기====");
				ArrayList<MemberDTO> list = dao.selectAll();
				
				for(int i = 0; i < list.size(); i++) {
					
					MemberDTO m = list.get(i);
					
					System.out.print(m.getId() + " - ");
					System.out.print(m.getPw() + " - ");
					System.out.println(m.getNick());
				}
				
				
			} else if (input == 4) {
				// 회원 정보 수정
				// 0. 프로젝트에 Driver 파일 넣기
				// 1. 드라이버 로딩
				// 2. Connection 연결
				// 3. sql문 작성 및 실행
				// update 테이블명 set 컬럼명 = 바꾸고 싶은값 where 조건
				// executeQuery -> 테이블에 데이터가 변함 없을 때
				// ResultSet로 반환
				// executeUpdate -> 테이블의 내용이 변경될 때
				// int 타입으로 반환 -> 실행된 sql문의 수
				// 4. finally
				// conn 객체, psmt, rs
				// 닫아주는 순서 : rs -> psmt -> conn

				// id -> pbk 인 회원의 닉네임을
				// '킹병관' 으로 바꾸어 주세요
				System.out.println("====회원정보를 수정해주세요====");
				System.out.println("아이디 입력 : ");
				String id = sc.next();
				int cnt = dao.update(id);
				
				if(cnt > 0) {
					System.out.println("회원정보 수정 완료");
				}else {
					System.out.println("회원정보 수정 실패");
				}

		

				

			} else if (input == 5) {
				System.out.println("====회원탈퇴====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();
				
				int cnt = dao.delete(id, pw);
				
				if (cnt > 0) {
					System.out.println("회원삭제 완료");
				}else {
					System.out.println("회원삭제 실패");
				}
				
				
			} else if (input == 6) {
				System.out.println("프로그램을 종료합니다...");
				break;
			} else {
				System.out.println("정확한 숫자를 다시 입력해주세요.");
			}

		}

	}

}
