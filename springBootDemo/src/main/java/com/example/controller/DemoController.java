package com.example.controller;

import com.example.Service.UserService;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
public class DemoController {
    @Autowired
    public UserService service;
    @GetMapping(value = "getUsers")
    public List<User>  dataSource(){
        return service.queryUsers();
    }
    @GetMapping(value = "getUser/{id}")
    public User getUserByid(@PathVariable("id") int id){
        return service.queryUserById(id);
    }
    @PostMapping(value = "addUser")
    public User addUser(@RequestBody User user){
        System.out.println(user);
        return service.addUser(user);
    }
    @DeleteMapping(value = "deleteUserById")
    public int deleteUserByid(@RequestParam("userId")  int id){
        return service.deleteUserById(id);
    }
    @PutMapping(value = "updateUserById")
    public User updateUserById(@RequestBody User user){
        return service.updateUserByid(user);
    }

     @PostMapping(value="queryUsersByPage")
    public List<User> queryUserByPage(@RequestBody Map<String,Integer> params ){

        return service.queryUsersByPage(params.get("page"), params.get("pageSize"));
}


}
