package t2.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import t2.dao.ChannelDAO;



public class ChannelManager {
	private ChannelDAO channelDao;
	public ChannelManager() {
		
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			channelDao = session.getMapper(ChannelDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String[] getChannelList() {
		String[] list = channelDao.getChannelList();
		return list;
	}
}
