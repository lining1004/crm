package com.briup.crmreport.service;

import com.briup.crmreport.entity.BarData;
import com.briup.crmreport.entity.PieData;
import com.briup.crmreport.web.rest.CustomerRestService;
import com.briup.crmreport.web.vm.CustomerVM;
import com.briup.crmresource.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author lining
 * @Date 2022/11/23
 */
@Service
public class CustConstructServiceImpl implements ICustConstructService {
    @Autowired
    private CustomerRestService restService;

    public BarData collectByRegion() {
        //远程调用获取用户信息
        List<CustomerVM> list = restService.findByPage(0, -1).getData();
        //按照区域对集合对象进行分类统计
        Map<String, Long> map = list.stream()
                .collect(Collectors.
                        groupingByConcurrent(CustomerVM::getRegion, Collectors.counting()));
        //创建统计图对象
        BarData barData = BarData.builder()
                            .category(map.keySet())
                            .value(map.values())
                            .build();
        System.out.println(barData);
        return barData;
    }

    public List<PieData> collectByLevel() {
        //远程调用获取用户信息
        List<CustomerVM> list = restService.findByPage(0, -1).getData();
        //按照客户满意度对集合对象进行分类统计
        Map<String, Long> map = list.stream().collect(Collectors
                .groupingByConcurrent(CustomerVM::getLevelLabel, Collectors.counting()));
        //封装成饼状图对象
        List<PieData> pieDatas = map.entrySet().stream().map(entry ->
                        PieData.builder().name(entry.getKey()).value(entry.getValue()).build())
                .collect(Collectors.toList());
        return pieDatas;
    }

    public BarData collectBySatisfy() {
        //远程调用获取用户信息
        List<CustomerVM> list = restService.findByPage(0, -1).getData();
        //按照客户等级对集合对象进行分类统计
        Map<String, Long> map = list.stream().collect(Collectors
                .groupingByConcurrent(CustomerVM::getLevelLabel, Collectors.counting()));
        //创建统计图对象
        BarData barData = BarData.builder()
                .category(map.keySet())
                .value(map.values())
                .build();
        return barData;
    }
}
