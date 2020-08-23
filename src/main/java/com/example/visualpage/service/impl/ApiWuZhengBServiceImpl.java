package com.example.visualpage.service.impl;

import com.example.visualpage.service.ApiWuZhengBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiWuZhengBServiceImpl implements ApiWuZhengBService {

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondJdbcTemplate;

    public Map<String, Object> secondQueryForMap(String sql,Object... params){
        try{
            return secondJdbcTemplate.queryForMap(sql,params);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 痕迹物品
     * @param markGoodsId
     * @return
     * @throws ParseException
     */
    @Override
    public Map<String, Object> selectMarkGoods(String markGoodsId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlMarkGoods = "select * from mark_goods where MARK_GOODS_ID=?";
        Map<String, Object> markGoods = secondQueryForMap(sqlMarkGoods, markGoodsId);
        if(markGoods!=null){
            String goodsTypeId = markGoods.get("GOODS_TYPE_ID")==null?"":markGoods.get("GOODS_TYPE_ID").toString();
            Map<String, Object> goodsType = secondQueryForMap("select * from GOODS_TYPE where GOODS_TYPE_ID=?",goodsTypeId);
            resultMap.put("goodsType",goodsType);
            String sampleMaterialId = markGoods.get("SAMPLE_MATERIAL_ID")==null?"":markGoods.get("SAMPLE_MATERIAL_ID").toString();
            Map<String, Object> sampleMaterial = secondQueryForMap("select * from SAMPLE_MATERIAL where SAMPLE_MATERIAL_ID=?",sampleMaterialId);
            resultMap.put("sampleMaterial",sampleMaterial);
            String extractMethodId = markGoods.get("EXTRACT_METHOD_ID")==null?"":markGoods.get("EXTRACT_METHOD_ID").toString();
            Map<String, Object> extractMethod = secondQueryForMap("select * from EXTRACT_METHOD where EXTRACT_METHOD_ID=?",extractMethodId);
            resultMap.put("extractMethod",extractMethod);
        }
        if(markGoods!=null){
            if(markGoods.get("EXTRACT_TIME")!=null){
                markGoods.put("EXTRACT_TIME",timeStamp2Date(markGoods.get("EXTRACT_TIME")));
            }
            if(markGoods.get("CREATE_TIME")!=null){
                markGoods.put("CREATE_TIME",timeStamp2Date(markGoods.get("CREATE_TIME")));
            }
            if(markGoods.get("IDEN_TIME")!=null){
                markGoods.put("IDEN_TIME",timeStamp2Date(markGoods.get("IDEN_TIME")));
            }
        }
        resultMap.put("markGoods",markGoods);
        List<Map<String, Object>> fullPhoto = secondJdbcTemplate.queryForList("SELECT * FROM FULL_PHOTO WHERE MARK_GOODS_ID=?",markGoodsId);
        resultMap.put("fullPhoto",fullPhoto);
        List<Map<String, Object>> positionPhoto = secondJdbcTemplate.queryForList("SELECT * FROM POSITION_PHOTO WHERE MARK_GOODS_ID=?",markGoodsId);
        resultMap.put("positionPhoto",positionPhoto);
        String markGoodsTestInfoSql = "SELECT t1.*,t2.MARK_GOODS_UNIT_NAME,t3.LEFT_OVER_POSITION_NAME,t4.INSPECTION_TYPE_NAME FROM MARK_GOODS_TEST_INFO t1 "
                + " LEFT JOIN MARK_GOODS_UNIT t2 ON t2.MARK_GOODS_UNIT_ID=t1.MARK_GOODS_UNIT_ID "
                + " LEFT JOIN LEFT_OVER_POSITION t3 ON t3.LEFT_OVER_POSITION_ID=t1.LEFT_OVER_POSITION_ID "
                + " LEFT JOIN INSPECTION_TYPE t4 ON t4.INSPECTION_TYPE_ID=t1.INSPECTION_TYPE_ID "
                + " WHERE t1.MARK_GOODS_ID=? ";
        List<Map<String, Object>> markGoodsTestInfo = secondJdbcTemplate.queryForList(markGoodsTestInfoSql,markGoodsId);
        resultMap.put("markGoodsTestInfo",markGoodsTestInfo);
        if(markGoodsTestInfo!=null&&markGoodsTestInfo.size()>0){
            for (Map<String,Object> map:markGoodsTestInfo) {
                String involvedPersonInfoId = map.get("INVOLVED_PERSON_INFO_ID")==null?"":map.get("INVOLVED_PERSON_INFO_ID").toString();
                List<Map<String, Object>> involvedPersonInfo = secondJdbcTemplate.queryForList("select * from INVOLVED_PERSON_INFO where INVOLVED_PERSON_INFO_ID=?",involvedPersonInfoId);
               if(involvedPersonInfo!=null&&involvedPersonInfo.size()>0){
                   for (Map<String, Object> m:involvedPersonInfo) {
                       if(m.get("INVESTIGATION_TIME")!=null){
                           m.put("INVESTIGATION_TIME",timeStamp2Date(m.get("INVESTIGATION_TIME")));
                       }
                       if(m.get("CREATE_TIME")!=null){
                           m.put("CREATE_TIME",timeStamp2Date(m.get("CREATE_TIME")));
                       }
                       if(m.get("UPDATE_TIME")!=null){
                           m.put("UPDATE_TIME",timeStamp2Date(m.get("UPDATE_TIME")));
                       }
                   }
               }
                map.put("involvedPersonInfo",involvedPersonInfo);
            }
        }
        return resultMap;
    }

    /**
     * 涉案物品信息
     * @param involvedGoodsInfoId
     * @return
     */
    @Override
    public Map<String, Object> selectInvolvedGoodsInfo(String involvedGoodsInfoId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlInvolvedGoodsInfo = "select * from INVOLVED_GOODS_INFO where INVOLVED_GOODS_INFO_ID=?";
        Map<String, Object> involvedGoodsInfo = secondQueryForMap(sqlInvolvedGoodsInfo, involvedGoodsInfoId);
        if(involvedGoodsInfo!=null){
            if(involvedGoodsInfo.get("PURCHASE_TIME")!=null){
                involvedGoodsInfo.put("PURCHASE_TIME",timeStamp2Date(involvedGoodsInfo.get("PURCHASE_TIME")));
            }
            if(involvedGoodsInfo.get("ARRIVAL_TIME")!=null){
                involvedGoodsInfo.put("ARRIVAL_TIME",timeStamp2Date(involvedGoodsInfo.get("ARRIVAL_TIME")));
            }
            if(involvedGoodsInfo.get("INVESTIGATION_TIME")!=null){
                involvedGoodsInfo.put("INVESTIGATION_TIME",timeStamp2Date(involvedGoodsInfo.get("INVESTIGATION_TIME")));
            }
            if(involvedGoodsInfo.get("CREATE_TIME")!=null){
                involvedGoodsInfo.put("CREATE_TIME",timeStamp2Date(involvedGoodsInfo.get("CREATE_TIME")));
            }
            if(involvedGoodsInfo.get("UPDATE_TIME")!=null){
                involvedGoodsInfo.put("UPDATE_TIME",timeStamp2Date(involvedGoodsInfo.get("UPDATE_TIME")));
            }
        }
        resultMap.put("involvedGoodsInfo",involvedGoodsInfo);
        List<Map<String, Object>> involvedGoodsEnclosure = secondJdbcTemplate.queryForList("SELECT INVOLVED_GOODS_ENCLOSURE_ID AS FILEID,INVOLVED_GOODS_ENCLOSURE_NAME AS FILENAME FROM INVOLVED_GOODS_ENCLOSURE WHERE INVOLVED_GOODS_INFO_ID=?",involvedGoodsInfoId);
        resultMap.put("involvedGoodsEnclosure",involvedGoodsEnclosure);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectMediaEnvironmentInfo(String mediaEnvironmentInfoId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlMediaEnvironmentInfo = "select t1.*,t2.TYPE_NAME,t2.MEDIA_TYPE from MEDIA_ENVIRONMENT_INFO t1 "
                +" LEFT JOIN MEDIA_TYPE t2 ON t2.TYPE_ID = t1.TYPE_ID WHERE t1.MEDIA_ENVIRONMENT_INFO_ID=? ";
        Map<String, Object> mediaEnvironmentInfo = secondQueryForMap(sqlMediaEnvironmentInfo, mediaEnvironmentInfoId);

        if(mediaEnvironmentInfo!=null){
            if(mediaEnvironmentInfo.get("CREATE_TIME")!=null){
                mediaEnvironmentInfo.put("CREATE_TIME",timeStamp2Date(mediaEnvironmentInfo.get("CREATE_TIME")));
            }
            if(mediaEnvironmentInfo.get("UPDATE_TIME")!=null){
                mediaEnvironmentInfo.put("UPDATE_TIME",timeStamp2Date(mediaEnvironmentInfo.get("UPDATE_TIME")));
            }
        }

        resultMap.put("mediaEnvironmentInfo",mediaEnvironmentInfo);
        List<Map<String, Object>> mediaAtta = secondJdbcTemplate.queryForList("select ATTA_ID AS FILEID,ATTA_NAME AS FILENAME from MEDIA_ATTA WHERE MEDIA_ENVIRONMENT_INFO_ID=?",mediaEnvironmentInfoId);
        resultMap.put("mediaAtta",mediaAtta);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectCaseConclusionInfo(String caseConclusionId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlCaseConclusionInfo = "select * from CASE_CONCLUSION_INFO where CASE_CONCLUSION_ID=?";
        Map<String, Object> caseConclusionInfo = secondQueryForMap(sqlCaseConclusionInfo, caseConclusionId);
        if(caseConclusionInfo!=null){
            if(caseConclusionInfo.get("CREATE_TIME")!=null){
                caseConclusionInfo.put("CREATE_TIME",timeStamp2Date(caseConclusionInfo.get("CREATE_TIME")));
            }
            if(caseConclusionInfo.get("UPDATE_TIME")!=null){
                caseConclusionInfo.put("UPDATE_TIME",timeStamp2Date(caseConclusionInfo.get("UPDATE_TIME")));
            }
            if(caseConclusionInfo.get("CRIME_TIME")!=null){
                caseConclusionInfo.put("CRIME_TIME",timeStamp2Date(caseConclusionInfo.get("CRIME_TIME")));
            }
        }
        resultMap.put("caseConclusionInfo",caseConclusionInfo);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectVisitSituation(String visitId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlVisitSituation = "select t1.*,t2.VISIT_TYPE_NAME from VISIT_SITUATION t1 "
                +" LEFT JOIN VISIT_TYPE t2 ON t2.VISIT_TYPE_ID = t1.VISIT_TYPE_ID WHERE t1.VISIT_ID=? ";
        Map<String, Object> visitSituation = secondQueryForMap(sqlVisitSituation, visitId);
        if(visitSituation!=null){
            if(visitSituation.get("VISIT_TIME")!=null){
                visitSituation.put("VISIT_TIME",timeStamp2Date(visitSituation.get("VISIT_TIME")));
            }
            if(visitSituation.get("CREATE_TIME")!=null){
                visitSituation.put("CREATE_TIME",timeStamp2Date(visitSituation.get("CREATE_TIME")));
            }
            if(visitSituation.get("UPDATE_TIME")!=null){
                visitSituation.put("UPDATE_TIME",timeStamp2Date(visitSituation.get("UPDATE_TIME")));
            }
        }
        resultMap.put("visitSituation",visitSituation);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectEleInfo(String eleInfoId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlEleInfo = "select t1.*,t2.INVESTIGATION_TYPE_NAME from ELE_INFO t1 "
                +" LEFT JOIN INVESTIGATION_TYPE t2 ON t1.INVESTIGATION_TYPE_ID=t2.INVESTIGATION_TYPE_ID where t1.ELE_INFO_ID=?";
        Map<String, Object> eleInfo = secondQueryForMap(sqlEleInfo, eleInfoId);
        if(eleInfo!=null){
            if(eleInfo.get("EXT_TIME")!=null){
                eleInfo.put("EXT_TIME",timeStamp2Date(eleInfo.get("EXT_TIME")));
            }
            if(eleInfo.get("CREATE_TIME")!=null){
                eleInfo.put("CREATE_TIME",timeStamp2Date(eleInfo.get("CREATE_TIME")));
            }
            if(eleInfo.get("UPDATE_TIME")!=null){
                eleInfo.put("UPDATE_TIME",timeStamp2Date(eleInfo.get("UPDATE_TIME")));
            }
        }
        resultMap.put("eleInfo",eleInfo);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectStaOfSuspect(String staId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlStaOfSuspect = "select t1.*,t2.INVESTIGATION_TYPE_NAME from STA_OF_SUSPECT t1 "
                +" LEFT JOIN INVESTIGATION_TYPE t2 ON t1.INVESTIGATION_TYPE_ID=t2.INVESTIGATION_TYPE_ID where t1.STA_ID=?";
        Map<String, Object> staOfSuspect = secondQueryForMap(sqlStaOfSuspect, staId);

        if(staOfSuspect!=null){
            if(staOfSuspect.get("CREATE_TIME")!=null){
                staOfSuspect.put("CREATE_TIME",timeStamp2Date(staOfSuspect.get("CREATE_TIME")));
            }
            if(staOfSuspect.get("UPDATE_TIME")!=null){
                staOfSuspect.put("UPDATE_TIME",timeStamp2Date(staOfSuspect.get("UPDATE_TIME")));
            }
            String involvedPersonInfoId = staOfSuspect.get("INVOLVED_PERSON_INFO_ID")==null?"":staOfSuspect.get("INVOLVED_PERSON_INFO_ID").toString();
            Map<String, Object> involvedPersonInfo =secondQueryForMap("select * from INVOLVED_PERSON_INFO where INVOLVED_PERSON_INFO_ID=?",involvedPersonInfoId);
            if(involvedPersonInfo!=null){
                if(involvedPersonInfo.get("INVESTIGATION_TIME")!=null){
                    involvedPersonInfo.put("INVESTIGATION_TIME",timeStamp2Date(involvedPersonInfo.get("INVESTIGATION_TIME")));
                }
                if(involvedPersonInfo.get("CREATE_TIME")!=null){
                    involvedPersonInfo.put("CREATE_TIME",timeStamp2Date(involvedPersonInfo.get("CREATE_TIME")));
                }
                if(involvedPersonInfo.get("UPDATE_TIME")!=null){
                    involvedPersonInfo.put("UPDATE_TIME",timeStamp2Date(involvedPersonInfo.get("UPDATE_TIME")));
                }
            }
            resultMap.put("involvedPersonInfo",involvedPersonInfo);
        }


        resultMap.put("staOfSuspect",staOfSuspect);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectInvolvedPersonInfo(String involvedPersonInfoId) throws ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlInvolvedPersonInfo = " select * from INVOLVED_PERSON_INFO where INVOLVED_PERSON_INFO_ID=?";
        Map<String, Object> involvedPersonInfo = secondQueryForMap(sqlInvolvedPersonInfo, involvedPersonInfoId);
        if(involvedPersonInfo!=null){
            if(involvedPersonInfo.get("INVESTIGATION_TIME")!=null){
                involvedPersonInfo.put("INVESTIGATION_TIME",timeStamp2Date(involvedPersonInfo.get("INVESTIGATION_TIME")));
            }
            if(involvedPersonInfo.get("CREATE_TIME")!=null){
                involvedPersonInfo.put("CREATE_TIME",timeStamp2Date(involvedPersonInfo.get("CREATE_TIME")));
            }
            if(involvedPersonInfo.get("UPDATE_TIME")!=null){
                involvedPersonInfo.put("UPDATE_TIME",timeStamp2Date(involvedPersonInfo.get("UPDATE_TIME")));
            }
        }
        resultMap.put("involvedPersonInfo",involvedPersonInfo);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectMediumInfo(String mediumInfoId) {
        Map<String,Object> resultMap = new HashMap<>();
        String sqlMediumInfo = " select * from MEDIUM_INFO where MEDIUM_INFO_ID=?";
        Map<String, Object> mediumInfo = secondQueryForMap(sqlMediumInfo, mediumInfoId);
        resultMap.put("mediumInfo",mediumInfo);
        return resultMap;
    }

    @Override
    public void down(String FILEID,String tableName,String tableIdName,String tableFileName,String tableFileContent,HttpServletResponse response) {
        Map<String, Object> involvedGoodsEnclosure = secondQueryForMap("SELECT * FROM "+tableName+" WHERE "+tableIdName+"=?",FILEID);
        if(involvedGoodsEnclosure!=null){
            ServletOutputStream out = null;
            try {
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                String fileName = String.valueOf(involvedGoodsEnclosure.get(tableFileName));
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName,"UTF-8"));
                out = response.getOutputStream();
                out.write((byte[]) involvedGoodsEnclosure.get(tableFileContent));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(out!=null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }




     /*   Map<String, Object> involvedGoodsEnclosure = secondQueryForMap("SELECT * FROM INVOLVED_GOODS_ENCLOSURE WHERE INVOLVED_GOODS_ENCLOSURE_ID=?",FILEID);
        if(involvedGoodsEnclosure!=null){
            ServletOutputStream out = null;
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" +  String.valueOf(involvedGoodsEnclosure.get("INVOLVED_GOODS_ENCLOSURE_NAME")));
            try {
                out = response.getOutputStream();
                out.write((byte[]) involvedGoodsEnclosure.get("INVOLVED_GOODS_ENCLOSURE_CONTENT"));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(out!=null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }*/



    }

    @Override
    @Transactional(readOnly=false, propagation= Propagation.REQUIRES_NEW,rollbackFor = {Exception.class,RuntimeException.class})
    public int update(Map<String, Object> map) {
        Object[] objects = new Object[map.size()-2];
        String sql = "update "+ (map.get("tableName")==null?"":map.get("tableName").toString()) +" set ";
        String tableId = map.get("tableId")==null?"":map.get("tableId").toString();
        int i = 0;
        for (String key : map.keySet()) {
            if(!"tableName".equals(key)&&!"tableId".equals(key)&&!tableId.equals(key)){
                sql = sql +" "+key + "=?,";
                if("UPDATE_TIME".equals(key)){
                    objects[i]=new Date();
                }else {
                    objects[i]=map.get(key);
                }
                i++;
            }
        }
        sql = sql.substring(0,sql.length()-1);
        sql = sql + " where "+map.get("tableId").toString()+"=?";
        objects[map.size()-3]=map.get(tableId);
        int count = secondJdbcTemplate.update(sql,objects);
        return 0;
    }

    @Override @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class,
            RuntimeException.class }) public int insert(Map<String, Object> map) {
        Object[] objects = new Object[map.size()];
        String sql = "insert into ";
        sql += map.get("tableName") == null ? "" : map.get("tableName").toString() + "(";
        String keypart = "";
        String valpart = "";
        for (Map.Entry<String, Object> e : map.entrySet()) {
            if (e.getKey().equals("tableName") || e.getKey().equals("tableId"))
                continue;
            keypart += e.getKey();
            keypart += ",";
            if (e.getValue().getClass().equals(String.class)) {
                valpart += "'";
                valpart += e.getValue().toString();
                valpart += "'";
            }
            valpart += ",";
        }
        keypart = keypart.trim();
        keypart = keypart.substring(0, keypart.length() - 1);
        valpart = valpart.trim();
        valpart = valpart.substring(0, valpart.length() - 1);
        sql += keypart;
        sql += ")";
        sql += " values(";
        sql += valpart;
        sql += ");";
        System.out.print(sql);
        secondJdbcTemplate.execute(sql);
        return 0;
    }


    public String timeStamp2Date(Object o) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=sdf.parse(o.toString());
        return sdf.format(date);
    }


}
