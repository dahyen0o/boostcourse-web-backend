package org.edwith.webbe.guestbook.dao;

import org.edwith.webbe.guestbook.dto.Guestbook;
import org.edwith.webbe.guestbook.util.DBUtil;

//import kr.or.connect.jdbcexam.dto.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestbookDao {
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb";
	private static String dbUser = "root";
	private static String dbpasswd = "0722";
	
    public List<Guestbook> getGuestbooks(){
        List<Guestbook> list = new ArrayList<>();

        // 코드를 작성하세요.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String sql = "SELECT id, name, content, regdate FROM guestbook order by id";
		try (Connection conn = DBUtil.getConnection(dburl, dbUser, dbpasswd);
			PreparedStatement ps = conn.prepareStatement(sql)) { // try(~~): ~~을 알아서 close 해줌
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long id = rs.getLong("id");
					String name = rs.getString("name");
					String content = rs.getString("content");
					Date regdate = rs.getDate("regdate");
					Guestbook guest = new Guestbook(id, name, content, regdate);
					list.add(guest); // list에 반복할때마다 Role인스턴스를 생성하여 list에 추가한다.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

        return list;
    }

    public void addGuestbook(Guestbook guestbook){
        // 코드를 작성하세요.

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO guestbook (id, name, content, regdate) VALUES (id, ?, ?, now())";

		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, guestbook.getName());
			ps.setString(2, guestbook.getContent());
			//ps.setDate(3, (java.sql.Date) guestbook.getRegdate());

			ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
    }
}
