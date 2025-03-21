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
	public int getPgKey(String selectedProgram) {
		int pgKey = programDao.getPgKey(selectedProgram);
		return pgKey;
	}
	public void insertProgram(String pRNameText, int ageKey) {
		programDao.insertProgram(pRNameText,ageKey);
	}
	public boolean searchName(String pRNameText) {
		 if(pRNameText.equals(programDao.searchName(pRNameText)))
			 return true;	
		return false;
	}
	public void updateProgram(String pRNameText, String selectedAge) {
		programDao.updateProgram(pRNameText,selectedAge);
	}
	public void updateProgramAll(String pRNameText, String pRName2Text, String selectedAge) {
		programDao.updateProgramAll(pRNameText,pRName2Text,selectedAge);
		
	}
	public void delProgram(String pRNameText) {
		programDao.delProgram(pRNameText);
		
	}

}
