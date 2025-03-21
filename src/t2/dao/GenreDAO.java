package t2.dao;

import org.apache.ibatis.annotations.Param;

public interface GenreDAO {

	void insertGenre(@Param("name")String grNameText);

	void updateGenre(@Param("old")String grNameText, @Param("new")String grName2Text);

	String checkGenre(@Param("name")String grNameText);

	void delGenre(@Param("name")String grNameText);

	String[] getGenreName();

}
