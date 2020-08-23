package com.example.visualpage.controller;

import com.example.visualpage.service.ApiWuZhengBService;
import com.example.visualpage.utils.ResultVo;
import com.example.visualpage.utils.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/wuzhengB")
public class ApiWuZhengBController {

    @Autowired
    private ApiWuZhengBService apiWuZhengBService;

    @RequestMapping(value = "/down")
    public void down(String FILEID,String tableName,String tableIdName,String tableFileName,String tableFileContent,HttpServletResponse response){
        apiWuZhengBService.down(FILEID,tableName,tableIdName,tableFileName,tableFileContent,response);
    }

    /**
     * Insert into kinetic_set
     */
    @RequestMapping(value = "/insert") public ResultVo insert(@RequestBody Map<String, Object> map) {
        try {
            return ResultVoUtil.success(apiWuZhengBService.insert(map));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    public ResultVo update(@RequestBody Map<String,Object> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.update(map));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }

    }

    /**
     *查询勘验基础信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/markGoods")
    public ResultVo selectMarkGoods(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectMarkGoods(map.get("markGoodsId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     * 涉案物品
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/involvedGoodsInfo")
    public ResultVo selectInvolvedGoodsInfo(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectInvolvedGoodsInfo(map.get("involvedGoodsInfoId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     * 媒介环境信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/mediaEnvironmentInfo")
    public ResultVo selectMediaEnvironmentInfo(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectMediaEnvironmentInfo(map.get("mediaEnvironmentInfoId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     * 案事件全貌
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/caseConclusionInfo")
    public ResultVo selectCaseConclusionInfo(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectCaseConclusionInfo(map.get("caseConclusionId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     * 走访情况
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/visitSituation")
    public ResultVo selectVisitSituation(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectVisitSituation(map.get("visitId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     * 电子信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/eleInfo")
    public ResultVo selectEleInfo(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectEleInfo(map.get("eleInfoId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }
    /**
     * 嫌疑人供述
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/staOfSuspect")
    public ResultVo selectStaOfSuspect(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectStaOfSuspect(map.get("staId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     *涉案人员
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/involvedPersonInfo")
    public ResultVo selectInvolvedPersonInfo(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectInvolvedPersonInfo(map.get("involvedPersonInfoId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }

    /**
     * 媒介信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/select/mediumInfo")
    public ResultVo selectMediumInfo(@RequestBody Map<String,String> map){
        try {
            return ResultVoUtil.success(apiWuZhengBService.selectMediumInfo(map.get("mediumInfoId")));
        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.serviceErr();
        }
    }


}
