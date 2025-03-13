package t2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.index;

public interface indexDAO {

	List<index> getBookMarkList(@Param("key")int userKey);

}
