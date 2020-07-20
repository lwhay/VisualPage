package com.example.visualpage.service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Map;

public interface ApiWuZhengBService {

    Map<String,Object> selectMarkGoods(String markGoodsId) throws ParseException;

    Map<String,Object> selectInvolvedGoodsInfo(String involvedGoodsInfoId) throws ParseException;

    Map<String,Object> selectMediaEnvironmentInfo (String mediaEnvironmentInfoId) throws ParseException;

    Map<String,Object> selectCaseConclusionInfo (String caseConclusionId) throws ParseException;

    Map<String,Object> selectVisitSituation (String visitId) throws ParseException;

    Map<String,Object> selectEleInfo (String eleInfoId) throws ParseException;

    Map<String,Object> selectStaOfSuspect(String staId) throws ParseException;

    Map<String,Object> selectInvolvedPersonInfo(String involvedPersonInfoId) throws ParseException;

    Map<String,Object> selectMediumInfo(String mediumInfoId);

    void down(String FILEID,String tableName,String tableIdName,String tableFileName,String tableFileContent,HttpServletResponse response);

    int update(Map<String,Object> map);

    int insertKineticSet(Map<String, Object> map);

}
