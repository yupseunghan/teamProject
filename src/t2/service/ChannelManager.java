package t2.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.ChannelDAO;
import t2.model.vo.Channel;



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
	public boolean selectChannel(String tVNameText) {
		Channel channel = channelDao.selectChannel(tVNameText);
		if(channel==null)
			return false;
		return true;
	}
	public void insertChannel(String tVNameText) {
		channelDao.insertChannel(tVNameText);
		
	}
	public void updateChannel(String tVNameText, String tVName2Text) {
		channelDao.updateChannel(tVNameText,tVName2Text);		
	}
	public void delChannel(String tvNameText) {
		channelDao.delChannel(tvNameText);
	}
}
