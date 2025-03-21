package t2.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.ChannelProDAO;
import t2.model.vo.ChannelPro;



public class ChannelProManager {
	private ChannelProDAO channelProDao;
	public ChannelProManager() {
		
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			channelProDao = session.getMapper(ChannelProDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public ChannelPro selectCp(String selectedPR, String selectedTV) {
		ChannelPro cp =channelProDao.selectCp(selectedPR,selectedTV);
		return cp;
	}
	public ChannelPro insertCp(String selectedPR, String selectedTV) {
		channelProDao.insertCp(selectedPR,selectedTV);
		return selectCp(selectedPR, selectedTV);
	}
	
}
