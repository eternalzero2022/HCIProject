package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.service.ImageService;
import com.njuse.battlerankbackend.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    OssUtil ossUtil;

    @Override
    public String upload(MultipartFile file) {
        try {
            return ossUtil.upload(file.getOriginalFilename(), file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw SelfDefineException.fileUploadFail();
        }
    }
}
