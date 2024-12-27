package com.pghub.user.services;

import com.cloudinary.Cloudinary;
import com.pghub.user.dto.ImageModel;
import com.pghub.user.exception.PgHubException;
import com.pghub.user.model.User;
import com.pghub.user.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Resource
    private Cloudinary cloudinary;

    @Override
    public List<User> getUsersByPgIdAndRole(Integer PgId) throws PgHubException{
        List<User> users = userRepository.findByPgId( PgId);
        if(users==null || users.isEmpty()){
            throw new PgHubException("Service.USER_LIST_EMPTY");
        }
        return users;
    }


    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

// ImageModel is an entity with image name, ID and url, but since we are using URL directly from userRepo, I am using user entity to only get the url

    @Override
    public User findById(UUID userId) throws PgHubException{
        return userRepository.findById(userId).orElseThrow(() -> new PgHubException("Service.USER_NOT_FOUND"));
    }

    @Override
    public ResponseEntity<Map> uploadImage(ImageModel imageModel, User user) {
        try {
//            if (imageModel.getName().isEmpty()) {
//                return ResponseEntity.badRequest().build();
//            }
            if (imageModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
//            Image image = new Image();
//            image.setName(imageModel.getName());
            user.setUserImage(uploadFile(imageModel.getFile(), "folder_1"));
            if(user.getUserImage() == null) {
                return ResponseEntity.badRequest().build();
            }
//            imageRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", user.getUserImage()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
