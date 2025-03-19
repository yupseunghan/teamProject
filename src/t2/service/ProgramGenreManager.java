package t2.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.ProgramGenreDAO;

public class ProgramGenreManager {
	private ProgramGenreDAO programGenreDao;
	public ProgramGenreManager() {
		
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			programGenreDao = session.getMapper(ProgramGenreDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean selectPrGr(String prName, String grName) {
		if(programGenreDao.selectPrGr(prName,grName) != null)
			return true;
		return false;
	}
	public void insertPrGr(String prName, String grName) {
		programGenreDao.insertPrGr(prName,grName);
		
	}
	public void delPrGr(String prName, String grName) {
		programGenreDao.delPrGr(prName,grName);
	}
	public boolean selectDelPrGr(String prName, String grName) {
		if(programGenreDao.selectDelPrGr(prName,grName) != null)
			return true;
		return false;
	}
}
