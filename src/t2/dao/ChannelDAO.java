package t2.dao;

import org.apache.ibatis.annotations.Param;

import t2.model.vo.Channel;

public interface ChannelDAO {

	String[] getChannelList();

	Channel selectChannel(@Param("name")String tVNameText);

	void insertChannel(@Param("name")String tVNameText);

	void updateChannel(@Param("old")String tVNameText, @Param("new")String tVName2Text);

	void delChannel(@Param("name")String tvNameText);

}
