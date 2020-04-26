package com.example.labmanage_server.service;

import com.example.labmanage_server.domain.Blog;
import com.example.labmanage_server.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServer {
    @Autowired
    private BlogMapper blogMapper;

    public void good(Integer id){
        blogMapper.Good(id);
    }
    public boolean addBlog(Blog blog){
        blog.setData(new Date());
        if(blogMapper.addBlogs(blog)>0){
            return true;
        }else{
            return false;
        }
    }
    public List<Blog> getAll(String query){
        return blogMapper.getAll(query);
    }
    public List<Blog> getByUid(Integer id){
        return blogMapper.getByUid(id);
    }
    public boolean deleteById(Integer id){
        if(blogMapper.deleteById(id)>0){
            return true;
        }else{
            return false;
        }
    }

}
