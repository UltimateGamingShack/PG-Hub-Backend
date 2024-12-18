package com.pghub.user.services;

import com.pghub.user.exception.PgHubException;
import com.pghub.user.model.RoleType;
import com.pghub.user.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {


    List<User> getUsersByPgIdAndRole(Integer PgId) throws PgHubException;
}
