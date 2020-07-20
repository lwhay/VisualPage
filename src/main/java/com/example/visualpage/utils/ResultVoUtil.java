package com.example.visualpage.utils;


public class ResultVoUtil {

    public static <T> ResultVo success(T t) {
        ResultVo result = new ResultVo();
        result.setCode(200);
        result.setStatus("success");
        result.setMessage("请求成功");
        result.setData(t);
        return result;
    }

    public static ResultVo success(String message) {
        ResultVo result = new ResultVo();
        result.setCode(200);
        result.setStatus("success");
        result.setMessage(message);
        return result;
    }

    /**
     * 参数错误
     *
     * @return
     */
    public static ResultVo paramErr(String msg) {
        ResultVo result = new ResultVo();
        result.setCode(0);
        result.setStatus("params_error");
        result.setMessage(msg);
        return result;
    }

    /**
     * 服务器错误
     *
     * @return
     */
    public static ResultVo serviceErr() {
        ResultVo result = new ResultVo();
        result.setCode(500);
        result.setStatus("error");
        result.setMessage("系统内部错误");
        return result;
    }

    public static <T> ResultVo zidingyi(Integer code,String status,String message,T t) {
        ResultVo result = new ResultVo();
        result.setCode(code);
        result.setStatus(status);
        result.setMessage(message);
        result.setData(t);
        return result;
    }





}
