package t2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.index;

public interface indexDAO {

	List<index> getBookMarkList(@Param("key")int userKey);

	void delBookMark(@Param("key")int userKey, @Param("program")String selectedProgram);

	

	void insertBookMark(@Param("uskey")int userKey, @Param("pgkey")int pgKey);

	

}
