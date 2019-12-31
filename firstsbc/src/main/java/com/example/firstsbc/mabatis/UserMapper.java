package com.example.firstsbc.mabatis;


import com.example.firstsbc.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public User finduserById(@Param("id") Integer id);//没@Param注解报错，类型的转换
}
