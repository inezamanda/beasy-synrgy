package com.synrgybootcamp.project.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class UploadFileUtil {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Cloudinary cloudinary = Singleton.getCloudinary();

    public String upload( MultipartFile picture) {
        logger.trace("Called CloudinaryService.upload with args: "  + ", " + " and the multipart file");
//        User user = userService.getUserID(authToken, email);
        try {
            Map uploadResult = cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.emptyMap());
            String publicId = uploadResult.get("url").toString();
            logger.info("The user "  + " successfully uploaded the file: " + publicId);
            return publicId;
        } catch (Exception ex) {
            logger.error("The user "  + " failed to load to Cloudinary the image file: " + picture.getName());
            logger.error(ex.getMessage());
            return null;
        }

    }
}
