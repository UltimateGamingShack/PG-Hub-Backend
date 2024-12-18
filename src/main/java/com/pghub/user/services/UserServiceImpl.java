package com.pghub.user.services;

import com.pghub.user.exception.PgHubException;
import com.pghub.user.model.User;
import com.pghub.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsersByPgIdAndRole(Integer PgId) throws PgHubException{
        List<User> users = userRepository.findByPgId( PgId);
        if(users==null || users.isEmpty()){
            throw new PgHubException("Service.USER_LIST_EMPTY");
        }
        return users;
    }
}
