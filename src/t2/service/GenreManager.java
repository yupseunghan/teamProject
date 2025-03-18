package t2.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import t2.dao.GenreDAO;


public class GenreManager {
	private GenreDAO genreDao;
	public GenreManager() {
		
		String resource = "t2/config/mybatis-config.xml";
		
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			genreDao = session.getMapper(GenreDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean insertGenre(String grNameText) {
		try {
		genreDao.insertGenre(grNameText);
		return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updateGnere(String grNameText, String grName2Text) {
		try{genreDao.updateGenre(grNameText, grName2Text); return true;}
		catch(Exception e) {
			return false;
		}
	}
	public boolean checkGenre(String grNameText) {
		String str =genreDao.checkGenre(grNameText);
		if(str==null) return false;
		return true;
	}
	public void delGenre(String grNameText) {
		genreDao.delGenre(grNameText);
	}
	public String[] getGenreList() {
		return genreDao.getGenreName();
	}
}
