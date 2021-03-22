package com.example.labmanage_server.mapper;

import com.example.labmanage_server.domain.ImgDeclare;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgMapper {
    @Select("select * from img_declare where did = #{did}")
    List<ImgDeclare> getByDid(Integer did);

    @Delete("delete from img_declare where id = #{id}")
    int delete(Integer id);

    @Insert("INSERT INTO img_declare (`did`, `path`) VALUES (#{did}, #{path});")
    int insert(Integer did,String path);
}
