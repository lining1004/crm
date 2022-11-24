package com.briup.crmreport.web.controller;

import com.briup.crmreport.entity.BarData;
import com.briup.crmreport.entity.PieData;
import com.briup.crmreport.service.ICustConstructService;
import com.briup.crmresource.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客户构成分析
 * @Author lining
 * @Date 2022/11/23
 */
@Api(tags = "客户构成分析")
@RestController
@RequestMapping("/report/construct")
public class CustConstructController {
    @Autowired
    private ICustConstructService service;
    @ApiOperation("按客户地区分布展示（柱状图）")
    @GetMapping("/region")
    public Result<BarData> collectByRegion(){
        BarData barData = service.collectByRegion();
        return Result.success(barData);
    }
    @ApiOperation("按客户等级分布展示（饼状图）")
    @GetMapping("/level")
    public Result collectByLevel(){
        List<PieData> pieDatas = service.collectByLevel();
        return Result.success(pieDatas);
    }
    @ApiOperation("按客户满意度分布展示（柱状图）")
    @GetMapping("/satisfy")
    public Result collectBySatisfy(){
        BarData barData = service.collectBySatisfy();
        return Result.success(barData);
    }
}
