/*
package com.briup.crmuitl.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class MyBatisPlusGenerator {
    public static void main(String[] args) {
        //1.连接信息；
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/crm?serverTimezone=UTC", "root", "root")
                .globalConfig(builder -> {
                    builder.author("briup") // 设置作者  代码注释
                            .outputDir("D:\\crm\\crm-marketing\\src\\main\\java"); // 指定java代码输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.briup") // 设置父包名
                            .moduleName("crmmarketing") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\crm\\crm-marketing\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(*/
/*"sal_chance", "sal_plan",*//*
"sal_contrat") // 设置需要生成的表名
                            .addFieldPrefix("cust_", "sal_","cont_") //设置过滤的列前缀
                            .addTablePrefix("cst_", "sal_", "sys_"); // 设置过滤表前缀

                })
                .execute();
    }
}
*/
