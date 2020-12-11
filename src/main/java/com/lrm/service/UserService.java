package com.lrm.service;

import com.lrm.dao.UserRepository;
import com.lrm.po.User;
import com.lrm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by limi on 2017/10/15.
 */
@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Transactional
    public void saveAll(User user){
        userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Transactional
    public boolean findbyusername(String username){
        User user = userRepository.findByUsername(username);
        if(user!=null) return false;
        return true;
    }
}
