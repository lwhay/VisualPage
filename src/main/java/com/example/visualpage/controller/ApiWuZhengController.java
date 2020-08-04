package com.example.visualpage.controller;

import com.example.visualpage.service.ApiWuZhengService;
import com.example.visualpage.utils.ResultVo;
import com.example.visualpage.utils.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/wuzheng")
public class ApiWuZhengController {

    @Autowired
    private ApiWuZhengService apiWuZhengService;

    /**
     *查询勘验基础信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/configScene")
    public ResultVo selectConfigScene(@RequestBody Map<String, String> map) {
        try {
            Map<String, Object> resultMap = apiWuZhengService.selectConfigScene(map.get("sceneId"));
            return ResultVoUtil.success(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     *查询勘验基础信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/inquestBaseInfo")
    public ResultVo selectInquestBaseInfo(@RequestBody Map<String,String> map){
        try {
            Map<String, Object> resultMap = apiWuZhengService.selectInquestBaseInfo(map.get("baseInfoId"));
            return ResultVoUtil.success(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    @RequestMapping(value = "/select/bodyBasic")
    public ResultVo selectBodyBasic(@RequestBody Map<String,String> map){
        try {
            Map<String, Object> resultMap = apiWuZhengService.selectBodyBasic(map.get("bodyId"));
            return ResultVoUtil.success(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    @RequestMapping(value = "/select/bodyBasicDetial")
    public ResultVo selectBodyBasicDetail(String id){
        try {
            Map<String, Object> resultMap = apiWuZhengService.selectBodyBasicDetail(id);
            return ResultVoUtil.success(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }


    @RequestMapping(value = "/update")
    public ResultVo update(@RequestBody Map<String,Object> map){
        try {
            return ResultVoUtil.success(apiWuZhengService.update(map));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }



    @RequestMapping(value = "/select/scoutDetial")
    public ResultVo selectscoutAttaDetail(@RequestBody Map<String,String> map){
        try {
            Map<String, Object> resultMap = apiWuZhengService.selectScoutRoomDetail(map.get("scoutId"));
            return ResultVoUtil.success(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    @RequestMapping(value = "/select/MediumDetial")
    public ResultVo selectMediumDetail(@RequestBody Map<String,String> map){
        try {
            Map<String, Object> resultMap = apiWuZhengService.selectMediumDetail(map.get("mediumId"));
            return ResultVoUtil.success(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

}
