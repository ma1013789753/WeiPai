package com.jokerdata.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: oldma
 * @Date: 2018/10/17 15:19
 * @describe： app统一返回实体类
 * @version: 1.0
 */

@Data
@ApiModel(description = "返回响应数据")
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回状态码
     */
    @ApiModelProperty(value = "返回状态码")
    private Integer code;


    /**
     * 返回内容
     */
    @ApiModelProperty(value = "返回内容")
    private T datas;



    public static <T> ApiResult result(Integer status, T msg) {
        ApiResult r = new ApiResult();
        r.setCode(status);
        r.setDatas(msg);
        return r;
    }


    //快速返回成功
    public static <T> ApiResult success() {
        return success("1");
    }


    public static <T> ApiResult success(T data) {
        return result(200, data);
    }

    /**
     * @return Result
     * @describe 自定义错误代码
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author oldma
     */
    public static <T> ApiResult error(String msg) {
        Map<String,String> map = new HashMap<>();
        map.put("error",msg);
        return result(400, map);
    }



}
