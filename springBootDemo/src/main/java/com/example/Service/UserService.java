package com.example.Service;

import com.example.dao.UserDao;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    public UserDao dao;

    @Cacheable(value = "user", key ="#root.targetClass.name+#root.method.name+#root.args" , unless = "#result eq null")
    public List<User> queryUsers(){
        return dao.queryAll();
    };
    @Cacheable( value = "user", key ="#root.targetClass.name+#root.args")
    public User queryUserById(int id){
        return dao.queryUserByid(id);
    }
    @CachePut(value = "user", key ="#root.targetClass.name+#root.method.name+#user.id")
    public User addUser(User user){
        int i = dao.addUser(user);
       return user;
    }
    @CacheEvict(value = "user", key="#root.targetClass.name+#id", condition = "#result  eq null ")
    public int deleteUserById(int id){
        return dao.deleteUserByid(id);
    }
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    @CachePut(value = "user", key ="#root.targetClass.name+#user.id")
    public User updateUserByid(User user){
        int i = dao.updateUserByid(user);
        return user;
    }

    public  List<User> queryUsersByPage(int page,int pageSize){
        int page1 = (page-1)*pageSize;
        return dao.queryUsersByPage(page1, pageSize);

    }


}
