package com.example.labmanage_server.controller;

import com.example.labmanage_server.domain.ImgDeclare;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.service.ImgServer;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("img")
public class ImageController {
    @Autowired
    private ImgServer server;

    @PostMapping("upload")
    public Msg upload(MultipartFile file){
        String upload = server.upload(file);
        return upload ==null?Msg.fail():Msg.success().add("path",upload);
    }

    @PostMapping("saveKey")
    public Msg saveKey(@RequestBody ImgDeclare imgDeclare){
        if (imgDeclare.getDid()==null||imgDeclare.getPath()==null){
            return Msg.fail("参数不能为空");
        }
        return server.insert(imgDeclare)?Msg.success():Msg.fail();
    }

    @GetMapping(value = "url")
    public Msg img(String key){
        return Msg.success().add("img",server.getImg(key));
    }

    @GetMapping("getImg/{did}")
    public Msg getImg(@PathVariable("did") Integer did){
        return Msg.success().add("imgs",server.getByDid(did));
    }

    @DeleteMapping("delete")
    public Msg delete(@RequestBody ImgDeclare imgDeclare){
        if (imgDeclare.getId()==null||imgDeclare.getPath()==null){
            return Msg.fail();
        }
        server.delete(imgDeclare);
        return Msg.success();
    }
}
