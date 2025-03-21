package t2.dao;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.ProgramGenre;

public interface ProgramGenreDAO {

	ProgramGenre selectPrGr(@Param("prName")String prName, @Param("grName")String grName);

	void insertPrGr (@Param("prName")String prName, @Param("grName")String grName);

	void delPrGr(@Param("prName")String prName, @Param("grName")String grName);

	Object selectDelPrGr(@Param("prName")String prName, @Param("grName")String grName);

}
