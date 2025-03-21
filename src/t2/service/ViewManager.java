package t2.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.ViewDAO;
import t2.model.vo.View;



public class ViewManager {
	private ViewDAO viewDao;
	public ViewManager() {
		
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			viewDao = session.getMapper(ViewDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public View select(int cp_num, String selectedDay, String selectedState) {
		View view = viewDao.selectView(cp_num,selectedDay,selectedState);
		return view;
	}
	public View insert(int cp_num, String selectedState, String selectedDay) {
		viewDao.insertView(cp_num,selectedDay,selectedState);
		return select(cp_num,selectedDay,selectedState);
	}
	
}
