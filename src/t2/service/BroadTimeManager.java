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
	SqlSession session;
	SqlSessionFactory sessionFactory;
	public BroadTimeManager() {
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			
			broadTimeDao = session.getMapper(BroadTimeDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<BroadTime> getBroadTimeList(String channel, String week) {
		session.clearCache();
		List<BroadTime> list = broadTimeDao.getBroadTimeList(channel,week);
		return list;
	}


	public BroadTime select(int vw_key, String day, String selectedST) {
		BroadTime bt = broadTimeDao.selectBt(vw_key,day,selectedST);
		return bt;
	}


	public void insertBt(int vw_key, String day, String startTime, String endTime) {
		broadTimeDao.insertBt(vw_key,day,startTime,endTime);
	}


	public void delete(int bt_num) {
		broadTimeDao.deleteBt(bt_num);
		
	}
}
