package t2.dao;

import org.apache.ibatis.annotations.Param;

public interface ProgramDAO {

	String[] getProgramList();

	int getPgKey(@Param("program")String selectedProgram);

	void insertProgram(@Param("program")String pRNameText, @Param("ageKey")int ageKey);

	String searchName(@Param("program")String pRNameText);

	void updateProgram(@Param("program")String pRNameText, @Param("age")String selectedAge);

	void updateProgramAll(@Param("old")String pRNameText,@Param("new") String pRName2Text, @Param("age")String selectedAge);

	void delProgram(@Param("program")String pRNameText);

}
