package com.example.dao;

import com.example.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
   List<User> queryAll();
   User queryUserByid(@Param("id") int id);
   int addUser( User user);
   int deleteUserByid(@Param("id") int id);
   int updateUserByid( User user);
   List<User> queryUsersByPage(@Param("page") int page, @Param("pageSize") int pageSize);
}
