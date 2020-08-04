package com.example.visualpage.service;

import java.text.ParseException;
import java.util.Map;

public interface ApiWuZhengService {

    Map<String,Object> selectInquestBaseInfo(String baseInfoId) throws ParseException;

    Map<String,Object> selectBodyBasic(String bodyId) throws ParseException;
    Map<String,Object> selectBodyBasicDetail(String id) throws ParseException;
    int update(Map<String,Object> map);

    Map<String,Object>  selectScoutRoomDetail(String scoutAttaId) throws ParseException;
    Map<String,Object>  selectMediumDetail(String mediumId) throws ParseException;
    Map<String,Object>  selectConfigScene(String sceneId) throws ParseException;
}
