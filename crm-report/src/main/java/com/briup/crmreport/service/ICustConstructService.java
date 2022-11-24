package com.briup.crmreport.service;

import com.briup.crmreport.entity.BarData;
import com.briup.crmreport.entity.PieData;
import com.briup.crmresource.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author lining
 * @Date 2022/11/23
 */
public interface ICustConstructService {

    BarData collectByRegion();

    List<PieData> collectByLevel();

    BarData collectBySatisfy();
}
