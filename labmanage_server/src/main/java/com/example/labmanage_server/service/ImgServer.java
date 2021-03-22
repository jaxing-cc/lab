package com.example.labmanage_server.service;

import com.example.labmanage_server.domain.ImgDeclare;
import com.example.labmanage_server.mapper.ImgMapper;
import com.jexing.cup.ImageStore;
import com.jexing.cup.exception.FileTypeNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImgServer {
    @Autowired
    private ImgMapper mapper;

    @Autowired
    private ImageStore store;

    public List<ImgDeclare> getByDid(Integer did){
        return mapper.getByDid(did);
    }

    public void delete(ImgDeclare imgDeclare){
        store.delete(imgDeclare.getPath());
        mapper.delete(imgDeclare.getId());
    }
    public String upload(MultipartFile file){
        String key = null;
        try {
            key = store.putObj(file.getInputStream(),file.getContentType());
        } catch (FileTypeNotSupportedException | IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    public boolean insert(ImgDeclare imgDeclare) {
        return mapper.insert(imgDeclare.getDid(),imgDeclare.getPath())>0;
    }

    public String getImg(String key){
        return store.getObjForBase64(key);
    }
}
