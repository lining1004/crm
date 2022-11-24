package com.briup.crmreport.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * 柱状图数据
 * @Author lining
 * @Date 2022/11/23
 */
@Builder
@Data
public class BarData {
    private Collection<String> category;//横坐标数据
    private Collection<? extends Number> value;//纵坐标数据
}
