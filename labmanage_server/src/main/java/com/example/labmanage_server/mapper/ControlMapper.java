package com.example.labmanage_server.mapper;


import com.example.labmanage_server.domain.Control;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlMapper {

    @Select("select * from controller where rank=#{rank}")
    List<Control> getControl(Integer rank);
}
