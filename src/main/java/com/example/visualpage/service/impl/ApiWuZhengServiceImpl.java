package com.example.visualpage.service.impl;

import com.example.visualpage.service.ApiWuZhengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ApiWuZhengServiceImpl implements ApiWuZhengService {

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondJdbcTemplate;

    public Map<String, Object> secondQueryForMap(String sql, Object... params) {
        try {
            return secondJdbcTemplate.queryForMap(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> secondQueryForList(String sql, Object... params) {
        try {
            return secondJdbcTemplate.queryForList(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> selectConfigScene(String sceneId) throws ParseException {
        String sqlScene = "select * from scene_t where SCENE_ID=?";
        Map<String, Object> mapscene = secondQueryForMap(sqlScene, sceneId);
        return mapscene;
    }

    @Override public Map<String, Object> selectKineticModel(Integer KSETID) throws ParseException {
        String sqlKinetSet = "select * from kinetic_set where KSETID=?";
        Map<String, Object> mapkineticset = secondQueryForMap(sqlKinetSet, KSETID);
        return mapkineticset;
    }

    @Override
    public Map<String, Object> selectInquestBaseInfo(String baseInfoId) throws ParseException {
        String sqlInquestBaseInfo = "select * from inquest_base_info where BASE_INFO_ID=?";
        String sqlCaseType = "select * from CASE_TYPE where CASE_TYPE_ID=?";
        String sqlTaskSource = "select * from TASK_SOURCE where TASK_SOURCE_ID =?";
        String sqlFieldCommander = "select * from FIELD_COMMANDER where FIELD_COMMANDER_ID =?";
        String sqlSiteChanges = "select * from  SITE_CHANGES where SITE_CHANGES_ID =?";

        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> mapInquestBaseInfo = secondQueryForMap(sqlInquestBaseInfo, baseInfoId);
        if (mapInquestBaseInfo != null) {
            if (mapInquestBaseInfo.get("REPORT_DATE") != null) {
                mapInquestBaseInfo.put("REPORT_DATE", timeStamp2Date(mapInquestBaseInfo.get("REPORT_DATE")));
            }
            if (mapInquestBaseInfo.get("INQUEST_START_TIME") != null) {
                mapInquestBaseInfo.put("INQUEST_START_TIME", timeStamp2Date(mapInquestBaseInfo.get("INQUEST_START_TIME")));
            }
            if (mapInquestBaseInfo.get("INQUEST_END_TIME") != null) {
                mapInquestBaseInfo.put("INQUEST_END_TIME", timeStamp2Date(mapInquestBaseInfo.get("INQUEST_END_TIME")));
            }
            if (mapInquestBaseInfo.get("CREATE_TIME") != null) {
                mapInquestBaseInfo.put("CREATE_TIME", timeStamp2Date(mapInquestBaseInfo.get("CREATE_TIME")));
            }
            if (mapInquestBaseInfo.get("UPDATETIME") != null) {
                mapInquestBaseInfo.put("UPDATETIME", timeStamp2Date(mapInquestBaseInfo.get("UPDATETIME")));
            }
        }
        Map<String, Object> mapCaseType = secondQueryForMap(sqlCaseType, mapInquestBaseInfo.get("CASE_TYPE_ID"));
        Map<String, Object> mapTaskSource = secondQueryForMap(sqlTaskSource, mapInquestBaseInfo.get("TASK_SOURCE_ID"));
        Map<String, Object> mapFieldCommander = secondQueryForMap(sqlFieldCommander, mapInquestBaseInfo.get("FIELD_COMMANDER_ID"));
        Map<String, Object> mapSiteChanges = secondQueryForMap(sqlSiteChanges, mapInquestBaseInfo.get("SITE_CHANGES_ID"));
        resultMap.put("inquestBaseInfo", mapInquestBaseInfo);
        resultMap.put("caseType", mapCaseType);
        resultMap.put("taskSource", mapTaskSource);
        resultMap.put("fieldCommander", mapFieldCommander);
        resultMap.put("siteChanges", mapSiteChanges);
        List<Map<String, Object>> inquestEnclosure = secondJdbcTemplate.queryForList("SELECT INQUEST_ENCLOSURE_ID AS FILEID,INQUEST_ENCLOSURE_NAME AS FILENAME FROM INQUEST_ENCLOSURE WHERE BASE_INFO_ID=?", baseInfoId);
        resultMap.put("inquestEnclosure", inquestEnclosure);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectBodyBasic(String bodyId) throws ParseException {
        Map<String, Object> resultMap = new HashMap<>();
        String sqlCorpseInfo = "select * from CORPSE_INFO where CORPSE_INFO_ID=?";
        Map<String, Object> mapCorpseInfo = secondQueryForMap(sqlCorpseInfo, bodyId);

        String sqlCorpseTestInfo = "select a.*,b.CORRUPT_DEGREE_NAME,c.CORPSE_COLOR_NAME,d.CORPSE_STIFF_DEGREE_NAME,e.CORPSE_INSECT_DEGREE_NAME from  CORPSE_TEST_INFO a ,CORRUPT_DEGREE b,CORPSE_COLOR c,CORPSE_STIFF_DEGREE d,CORPSE_INSECT_DEGREE e where  a.CORRUPT_DEGREE_ID = b.CORRUPT_DEGREE_ID and a.CORPSE_COLOR_ID= c.CORPSE_COLOR_ID and a.CORPSE_STIFF_DEGREE_ID =d.CORPSE_STIFF_DEGREE_ID and a.CORPSE_INSECT_DEGREE_ID = e.CORPSE_INSECT_DEGREE_ID  and  CORPSE_INFO_ID=?";
        List<Map<String, Object>> mapCorpseTestInfo = secondQueryForList(sqlCorpseTestInfo, bodyId);

       /*  String sqlFj = "SELECT * FROM CORPSE_TEST_ENCLOSURE WHERE CORPSE_TEST_INFO_ID =?";
        List<List<Map<String, Object>>> listFj = new ArrayList<>();

       if(mapCorpseTestInfo !=null) {
            for (int j = 0; j < mapCorpseTestInfo.size(); j++) {
                List<Map<String, Object>> mapFj = secondQueryForList(sqlFj, mapCorpseTestInfo.get(j).get("CORPSE_TEST_INFO_ID"));
                listFj.add(mapFj);
            }

        }*/
        if (mapCorpseTestInfo != null) {
            for (int i = 0; i < mapCorpseTestInfo.size(); i++) {

                if (mapCorpseTestInfo.get(i).get("IS_CORPSE_SOURCE") != null) {
                    if ("0".equals(mapCorpseTestInfo.get(i).get("IS_CORPSE_SOURCE"))) {
                        mapCorpseTestInfo.get(i).put("IS_CORPSE_SOURCE", "是");
                    } else {
                        mapCorpseTestInfo.get(i).put("IS_CORPSE_SOURCE", "否");
                    }
                }
                if (mapCorpseTestInfo.get(i).get("DEATH_TIME") != null) {
                    mapCorpseTestInfo.get(i).put("DEATH_TIME", timeStamp2Date(mapCorpseTestInfo.get(i).get("DEATH_TIME")));
                }
                if (mapCorpseTestInfo.get(i).get("CORPSE_TEMPERATURE_TIME") != null) {
                    mapCorpseTestInfo.get(i).put("CORPSE_TEMPERATURE_TIME", timeStamp2Date(mapCorpseTestInfo.get(i).get("CORPSE_TEMPERATURE_TIME")));
                }
                if (mapCorpseTestInfo.get(i).get("CREATE_TIME") != null) {
                    mapCorpseTestInfo.get(i).put("CREATE_TIME", timeStamp2Date(mapCorpseTestInfo.get(i).get("CREATE_TIME")));
                }
                if (mapCorpseTestInfo.get(i).get("IDEN_TIME") != null) {
                    mapCorpseTestInfo.get(i).put("IDEN_TIME", timeStamp2Date(mapCorpseTestInfo.get(i).get("IDEN_TIME")));
                }
            }
        }


        String sqlCorpsePhoto = "select * from CORPSE_PHOTO where CORPSE_INFO_ID=?";
        List<Map<String, Object>> mapCorpsePhoto = secondQueryForList(sqlCorpsePhoto, bodyId);

        resultMap.put("bodyBasic", mapCorpseInfo);
        resultMap.put("corpseTestInfo", mapCorpseTestInfo);
        resultMap.put("corpseCorpsePhoto", mapCorpsePhoto);
//      resultMap.put("listFj",listFj);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectBodyBasicDetail(String id) throws ParseException {
        //String sqlCorpseTestInfo = "select * from CORPSE_TEST_INFO where CORPSE_TEST_INFO_ID=?";
        String sqlCorpseTestInfo = "select a.*,b.CORRUPT_DEGREE_NAME,c.CORPSE_COLOR_NAME,d.CORPSE_STIFF_DEGREE_NAME,e.CORPSE_INSECT_DEGREE_NAME from  CORPSE_TEST_INFO a ,CORRUPT_DEGREE b,CORPSE_COLOR c,CORPSE_STIFF_DEGREE d,CORPSE_INSECT_DEGREE e where  a.CORRUPT_DEGREE_ID = b.CORRUPT_DEGREE_ID and a.CORPSE_COLOR_ID= c.CORPSE_COLOR_ID and a.CORPSE_STIFF_DEGREE_ID =d.CORPSE_STIFF_DEGREE_ID and a.CORPSE_INSECT_DEGREE_ID = e.CORPSE_INSECT_DEGREE_ID  and  CORPSE_TEST_INFO_ID=?";

        Map<String, Object> mapCorpseTestInfo = secondQueryForMap(sqlCorpseTestInfo, id);
        String sqlFj = "SELECT CORPSE_TEST_ENCLOSURE_ID,CORPSE_TEST_ENCLOSURE_NAME FROM CORPSE_TEST_ENCLOSURE WHERE CORPSE_TEST_INFO_ID =?";
        List<Map<String, Object>> mapFj = new ArrayList<>();
        if (mapCorpseTestInfo != null) {
            mapFj = secondQueryForList(sqlFj, mapCorpseTestInfo.get("CORPSE_TEST_INFO_ID"));

            if (mapCorpseTestInfo.get("DEATH_TIME") != null) {
                mapCorpseTestInfo.put("DEATH_TIME", timeStamp2Date(mapCorpseTestInfo.get("DEATH_TIME")));
            }
            if (mapCorpseTestInfo.get("CORPSE_TEMPERATURE_TIME") != null) {
                mapCorpseTestInfo.put("CORPSE_TEMPERATURE_TIME", timeStamp2Date(mapCorpseTestInfo.get("CORPSE_TEMPERATURE_TIME")));
            }
            if (mapCorpseTestInfo.get("CREATE_TIME") != null) {
                mapCorpseTestInfo.put("CREATE_TIME", timeStamp2Date(mapCorpseTestInfo.get("CREATE_TIME")));
            }
            if (mapCorpseTestInfo.get("IDEN_TIME") != null) {
                mapCorpseTestInfo.put("IDEN_TIME", timeStamp2Date(mapCorpseTestInfo.get("IDEN_TIME")));
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("corpseTestInfo", mapCorpseTestInfo);
        resultMap.put("listFj", mapFj);
        return resultMap;
    }

    @Override
    public int update(Map<String, Object> map) {
        String id = map.get("CORPSE_INFO_ID").toString();

        String sql = "UPDATE CORPSE_INFO set CORPSE_INFO_NAME=?,CORPSE_FIND_PLACE =?,CORPSE_INFORMATION =?,CORPSE_ATTITUDE =?,SCENE_BLOODSTAIN_SITUATION=?,SCENE_ES_SURVEY =?,CARRY_ON_GOODS =?,CLOTHES_SITUATION = ?,CORPSE_FEATURES = ?,CORPSE_COSTUMES = ?,CORPSE_INCLUSIONS =?,CORPSE_ANATOMY_INFORMATION =? where CORPSE_INFO_ID =? ";
        int count = secondJdbcTemplate.update(sql, map.get("CORPSE_INFO_NAME"), map.get("CORPSE_FIND_PLACE"), map.get("CORPSE_INFORMATION"), map.get("CORPSE_ATTITUDE"), map.get("SCENE_BLOODSTAIN_SITUATION"), map.get("SCENE_ES_SURVEY"), map.get("CARRY_ON_GOODS"), map.get("CLOTHES_SITUATION"), map.get("CORPSE_FEATURES"), map.get("CORPSE_COSTUMES"), map.get("CORPSE_INCLUSIONS"), map.get("CORPSE_ANATOMY_INFORMATION"), id);
        return count;
    }

    @Override
    public Map<String, Object> selectScoutRoomDetail(String scoutId) throws ParseException {
        String sql = "select * from SCOUT_ROOM where SCOUT_ID =?";
        Map<String, Object> mapScoutRoom = secondQueryForMap(sql, scoutId);
        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> mapSoutAtta = new HashMap<>();
        if (mapScoutRoom != null) {
            String sqlatta = "select ATTA_ID,ATTA_NAME from SCOUT_ATTA  where SCOUT_ID = ?";

            mapSoutAtta = secondQueryForMap(sqlatta, mapScoutRoom.get("SCOUT_ID"));

            if (mapScoutRoom.get("CREATE_TIME") != null) {
                mapScoutRoom.put("CREATE_TIME", timeStamp2Date(mapScoutRoom.get("CREATE_TIME")));
            }
            if (mapScoutRoom.get("UPDATE_TIME") != null) {
                mapScoutRoom.put("UPDATE_TIME", timeStamp2Date(mapScoutRoom.get("UPDATE_TIME")));
            }
        }


        resultMap.put("scoutRoom", mapScoutRoom);
        resultMap.put("scoutAtta", mapSoutAtta);
        return resultMap;

    }

    @Override
    public Map<String, Object> selectMediumDetail(String mediumId) throws ParseException {
        String sql = "select * from MEDIUM_INFO where MEDIUM_INFO_ID =?";
        Map<String, Object> mapMediumInfo = secondQueryForMap(sql, mediumId);
        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> mapMediumFj = new HashMap<>();
        if (mapMediumInfo != null) {
            String sqlfj = "select  MEDIUM_INFO_ENCLOSURE_ID,MEDIUM_INFO_ENCLOSURE_NAME  from MEDIUM_INFO_ENCLOSURE  where MEDIUM_INFO_ID = ?";

            mapMediumFj = secondQueryForMap(sqlfj, mapMediumInfo.get("MEDIUM_INFO_ID"));

            if (mapMediumInfo.get("CREATE_TIME") != null) {
                mapMediumInfo.put("CREATE_TIME", timeStamp2Date(mapMediumInfo.get("CREATE_TIME")));
            }
            if (mapMediumInfo.get("UPDATE_TIME") != null) {
                mapMediumInfo.put("UPDATE_TIME", timeStamp2Date(mapMediumInfo.get("UPDATE_TIME")));
            }
            if (mapMediumInfo.get("EXTRACT_TIME") != null) {
                mapMediumInfo.put("EXTRACT_TIME", timeStamp2Date(mapMediumInfo.get("EXTRACT_TIME")));
            }

        }


        resultMap.put("mediumInfo", mapMediumInfo);
        resultMap.put("mediumFj", mapMediumFj);
        return resultMap;

    }


    public String timeStamp2Date(Object o) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(o.toString());
        return sdf.format(date);
    }


}
