package com.briup.crmreport.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 饼状图每个部分数据,返回前端数据为List<PieData>
 * @Author lining
 * @Date 2022/11/23
 */
@Builder
@Data
public class PieData {
    private String name;    //饼图每个部分的数据类型
    private Number value;   //饼图每个部分的数据量
}
