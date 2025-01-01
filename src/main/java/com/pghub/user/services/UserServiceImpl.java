package com.pghub.user.services;

import com.cloudinary.Cloudinary;
import com.pghub.user.dto.ImageModel;
import com.pghub.user.exception.PgHubException;
import com.pghub.user.model.Role;
import com.pghub.user.model.RoleType;
import com.pghub.user.model.User;
import com.pghub.user.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
            return publicId;
            //return cloudinary.url().secure(true).generate(publicId);

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
    public String uploadImage(ImageModel imageModel, User user) {
        try {
//            if (imageModel.getName().isEmpty()) {
//                return ResponseEntity.badRequest().build();
//            }
            if (imageModel.getFile().isEmpty()) {
                throw new PgHubException("Service.IMAGE_EMPTY");
//                return ResponseEntity.badRequest().build();
            }
//            Image image = new Image();
//            image.setName(imageModel.getName());
            user.setUserImage(uploadFile(imageModel.getFile(), "PG"+user.getPgId().toString()));
            if(user.getUserImage() == null) {
                throw new PgHubException("Service.IMAGE_UPLOAD_ERROR");
//                return ResponseEntity.badRequest().build();
            }
            userRepository.save(user);
//            imageRepository.save(image);
//            return ResponseEntity.ok().body(Map.of("url", user.getUserImage()));
            return user.getUserImage();
            // Here, return success message instead of image url
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String getSignedImageUrl(UUID userId) throws PgHubException{
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new PgHubException("Service.USER_NOT_FOUND"));

            String publicId = user.getUserImage(); // Get the public_id from the user entity
            if(publicId == null || publicId.isEmpty()){
                throw new PgHubException("Service.USER_IMAGE_NOT_FOUND");
            }
            // Calculate expiration timestamp in seconds
            String signedUrl = cloudinary.url()
                    .secure(true)
                    .signed(true)
                    .resourceType("image")
                    .publicId(publicId)
                    .generate();

            return signedUrl;

        }
        catch(PgHubException pge){
                throw pge;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null; // Return an error message or handle exception as needed
        }
    }
public String getUsernameById(UUID userId){
        return userRepository.findUsernameById(userId);
}
    public List<User> getUsersByPgIdAndRoleName(String pgId, String roleName) throws PgHubException {
        if(pgId == null || roleName==null || pgId.isEmpty() || roleName.isEmpty()){
            throw new PgHubException("Service.PG_ID_OR_ROLE_EMPTY");
        }
        RoleType role = RoleType.ROLE_USER;
        if(roleName.equals("admin")){
            role = RoleType.ROLE_ADMIN;
        }else if(roleName.equals("cook")){
            role = RoleType.ROLE_COOK;
        }else if(roleName.equals("user")){
            role = RoleType.ROLE_USER;
        }else{
            throw new PgHubException("Service.INVALID_ROLE");
        }
        return userRepository.findUsersByPgIdAndRoleName(pgId, role);
    }
}
