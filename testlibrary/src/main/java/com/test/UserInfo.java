package com.test;

import com.godspeed.annotation.GenerateJSONModelCovert;
import com.godspeed.annotation.GenerateTableDao;

import java.util.List;
import java.util.Map;

@GenerateTableDao
public class UserInfo {

    @GenerateJSONModelCovert
    public UserInfo infos;
    @GenerateJSONModelCovert
    public List<Map<String,UserInfo>> maps;
    @GenerateJSONModelCovert
    public List<UserInfo> mapList;
    @GenerateJSONModelCovert
    public List<List<Map<String,UserInfo>>> listList;

}
