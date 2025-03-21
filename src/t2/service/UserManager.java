package t2.service;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextField;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.UserDAO;
import t2.model.vo.User;



public class UserManager {
	private UserDAO userDao;
	public UserManager() {
		
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			userDao = session.getMapper(UserDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean getAuthority(String id,String pw) {
		String authority = userDao.getAuthority(id,pw);
		if(authority.equals("ADMIN")) return true;
		return false;
	}
	public boolean idRepeat(String newId) {
		String id = userDao.Repeat(newId);
		if(id==null) 
			return false;
		return true;
	}
	public void insertUser(String newId, String newPw,String newName) {
		userDao.insertUser(newId,newPw,newName);
	}
	public int getUserNum(String id) {
		int num = userDao.getUserNum(id);
		return num;
	}
	public User getUser(int userKey) {
		User user = userDao.getUser(userKey);
		return user;
	}
	public boolean updateUser(String newId, String newPw, String newName, int userKey) {
		if(userDao.userUpdate(newId,newPw,newName,userKey)) {
			return true;
		}
		return false;
	}
	
}
