package com.example.labmanage_server.config;

import com.jexing.cup.ImageStore;
import com.jexing.cup.exception.NeedAbsolutePathException;
import com.jexing.cup.impl.LocalImageStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component()
public class ImageUploadConfig {
    @Value("${cup.path}")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Bean
    public ImageStore imageStore() throws NeedAbsolutePathException {
        return new LocalImageStore(path);
    }
}
