package t2.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.BroadTimeDAO;
import t2.model.vo.BroadTime;



public class BroadTimeManager {
	private BroadTimeDAO broadTimeDao;
	public BroadTimeManager() {
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			broadTimeDao = session.getMapper(BroadTimeDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<BroadTime> getBroadTimeList(String channel, String week) {
		List<BroadTime> list = broadTimeDao.getBroadTimeList(channel,week);
		return list;
	}
}
