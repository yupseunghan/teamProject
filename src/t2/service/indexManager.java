package t2.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.indexDAO;
import t2.model.vo.index;



public class indexManager {
	private indexDAO indexDao;
	public indexManager() {
		
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			indexDao = session.getMapper(indexDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public  List<index> getBookMarkList(int userKey) {
		List<index> list = indexDao.getBookMarkList(userKey);
		return list;
	}
	public void delBookMark(int userKey, String selectedProgram) {
		indexDao.delBookMark(userKey,selectedProgram);
		
	}
	
	public void insertBookMark(int userKey, int pgKey) {
		indexDao.insertBookMark(userKey,pgKey);
		
	}
	
}
