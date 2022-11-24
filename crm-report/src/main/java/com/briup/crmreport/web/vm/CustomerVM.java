package com.briup.crmreport.web.vm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author lining
 * @Date 2022/11/23
 */
@Data
public class CustomerVM {
    private Long id;
    private String name;
    private String region; //区域
    private String levelLabel; //客户等级
    private Integer satisfy; //满意度
}
