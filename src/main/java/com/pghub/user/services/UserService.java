package com.pghub.user.services;

import com.pghub.user.dto.ImageModel;
import com.pghub.user.exception.PgHubException;
import com.pghub.user.model.RoleType;
import com.pghub.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {


    List<User> getUsersByPgIdAndRole(Integer PgId) throws PgHubException;

    String uploadFile(MultipartFile file, String folderName);

    User findById(UUID userId) throws PgHubException;

    ResponseEntity<Map> uploadImage(ImageModel imageModel, User user);


    String getSignedImageUrl(UUID userId);
}
