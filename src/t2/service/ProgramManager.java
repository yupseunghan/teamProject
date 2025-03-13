package t2.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.ProgramDAO;



public class ProgramManager {
	private ProgramDAO programDao;
	public ProgramManager() {
		
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			programDao = session.getMapper(ProgramDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String[] getProgramList() {
		String[] list = programDao.getProgramList();
		return list;
	}
}
