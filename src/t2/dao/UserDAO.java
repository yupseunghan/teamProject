package t2.dao;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.User;

public interface UserDAO {

	String getAuthority(@Param("id")String id,@Param("pw")String pw);

	String Repeat(@Param("id")String newId);

	void insertUser(@Param("id")String newId, @Param("pw")String newPw, @Param("name")String newName);

	User getUser(@Param("key")int userKey);

	int getUserNum(@Param("id")String id);

	boolean userUpdate(@Param("id")String newId, @Param("pw")String newPw, @Param("name")String newName, @Param("key")int userKey);

}
