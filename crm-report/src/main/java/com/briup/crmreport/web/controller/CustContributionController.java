package com.briup.crmreport.web.controller;

import com.briup.crmresource.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户贡献分析
 * @Author lining
 * @Date 2022/11/23
 */
//@Api(tags = "客户贡献分析")
//@RestController
//@RequestMapping("/report/contribution")
public class CustContributionController {

    @ApiOperation("按客户地区分布展示（柱状图）")
    @GetMapping("/region")
    public Result collectByRegion(){
        return null;
    }
    @ApiOperation("按客户等级分布展示（饼状图）")
    @GetMapping("/level")
    public Result collectByLevel(){
        return null;
    }
    @ApiOperation("按客户满意度分布展示（柱状图）")
    @GetMapping("/satisfy")
    public Result collectBySatisfy(){
        return null;
    }
}
