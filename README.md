## 客户关系管理系统（CRM）

### 项目介绍

  **CRM**(Customer Relationship Management)项目主要针对企业的具体业务需求，开发出的一套管理企业中客户关系的管理系统，该项目主要包括**系统模块**、**客户管理模块**、**营销管理模块**、**统计报表模块**、**服务管理模块**等，通过各模块提供的功能对企业中服务的创建、分配、执行、处理、归档等完成自动化的管理，提高客户满意度，进而提升企业的核心竞争力。

### 项目预览

演示地址：```http://127.0.0.1:8083/swagger-ui/index.html```

账号：briup 密码：briup

### 技术选型

1. 前后端分离+微服务架构

2. springboot+eureka+gateway+OpenFegin+springCloud config+mybatisPlus+mysql+redis

### 项目架构



### 模块介绍

|      模块名称      |     模块     |
| :----------------: | :----------: |
| crm-eureka-server  |   注册中心   |
| crm-config-server  |   配置中心   |
| crm-gateway-server |   网关路由   |
|    crm-resource    | 通用配置模块 |
|      crm-util      | 通用工具模块 |
|    crm-customer    |   客户管理   |
|     crm-system     |   系统管理   |
|    crm-service     |   服务管理   |
|     crm-report     |   报表管理   |

### 项目搭建

#### crm（父项目）

1. 创建crm项目

   

2. 修改pom.xml

   

3. 添加依赖信息

   

#### crm-eureka-server（注册中心）

1. 创建crm-eureka-server子项目，实现注册中心功能

   ![image-20221115224642219](image/image-20221115224642219.png)

2. 添加依赖信息：**eureka-server**

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   </dependency>
   ```

3. 添加配置信息

   ```yaml
   server:
     port: @eureka_server_port@
   spring:
     application:
       name: eureka-server
   eureka:
     client:
       register-with-eureka: false
       fetch-registry: false
       service-url:
         defaultZone: http://@eureka_server_host@:@eureka_server_port@/eureka/
     server:
       enable-self-preservation: false
       eviction-interval-timer-in-ms: 60000
   ```

4. 添加注解：**@EnableEurekaServer**

   ```java
   @EnableEurekaServer
   @SpringBootApplication
   public class CrmEurekaServerApplication {
       public static void main(String[] args) {
           SpringApplication.run(CrmEurekaServerApplication.class, args);
       }
   }
   ```
   
   > 我们这里配置的是单机模式的eureka-server进行演示,大家可以根据之前的课件内容，配置高可用的eureka-server集群。

#### crm-config（配置中心）

1. 在git仓库中创建**crm**目录，并创建**crm-customer**项目读取的配置文件**customer-dev.yml**

   ![image-20221115100543156](image/image-20221115100543156.png)

   **customer-dev.yml**：

   ```yaml
   server:
     port: 9001
   spring:
     application:
       name: crm-customer
     datasource:
       url: jdbc:mysql://127.0.0.1:3306/crm?serverTimezone=UTC&useunicode=true&characterEncoding=utf8
       driver-class-name: com.mysql.cj.jdbc.Driver
       username: root
       password: root
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8081/eureka/
     instance:
       prefer-ip-address: true
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
   ```

   > 其他微服务项目的配置信息与crm-customer配置相同

   ![image-20221115224442771](image/image-20221115224442771.png)

2. 创建crm-config-server子项目

   ![image-20221115225513609](image/image-20221115225513609.png)
   
3. 添加依赖：**config-server、eureka-client**

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-config-server</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   ```

4. 添加配置信息

   ```yaml
   server:
     port: 9005
   spring:
     application:
       name: config-server
     cloud:
       config:
         server:
           git:
             uri: https://gitee.com/briup-lining/briup-config.git
             search-paths: crm
   eureka:
     instance:
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
       prefer-ip-address: true
     client:
       service-url:
         defaultZone: http://localhost:8081/eureka/
   ```

5. 使用注解：**@EnableDiscoveryClient、@EnableConfigServer**

   ```java
   @EnableDiscoveryClient
   @EnableConfigServer
   @SpringBootApplication
   public class CrmConfigApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(CrmConfigApplication.class, args);
       }
   }
   ```

#### crm-gateway（网关）

1. 创建crm-gateway子项目

   ![image-20221115225940162](image/image-20221115225940162.png)

2. 添加依赖：**gateway、eureka-client**

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-gateway</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   ```

3. 添加注解：**@EnableDiscoveryClient**

   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class CrmGatewayApplication {
       public static void main(String[] args) {
           SpringApplication.run(CrmGatewayApplication.class, args);
       }
   }
   ```

4. 添加配置信息

   > 路由规则中添加crm-customer、crm-marketing、crm-report、crm-service、crm-system5个项目

   ```yaml
   server:
     port: 8888
   spring:
     application:
       name: crm-gateway
     cloud:
       gateway:
         routes:
           - id: crm-customer         #规则名称
             uri: lb://crm-customer   #目标服务地址
             predicates:   #路由条件
               - Path=/api/customer/**
             filters:  #过滤器，去掉前2部分前缀后再转发，/api/test就被去掉了
               - StripPrefix=2
           - id: crm-marketing
             uri: lb://crm-marketing
             predicates:
               - Path=/api/marketing/**
             filters: 
               - StripPrefix=2
           - id: crm-report
             uri: lb://crm-report
             predicates:
               - Path=/api/report/**
             filters: 
               - StripPrefix=2
           - id: crm-service
             uri: lb://crm-service
             predicates:
               - Path=/api/service/**
             filters: 
               - StripPrefix=2
           - id: crm-system
             uri: lb://crm-system
             predicates:
               - Path=/api/system/**
             filters: 
               - StripPrefix=2
   eureka:
     instance:
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
       prefer-ip-address: true
     client:
       service-url:
         defaultZone: http://localhost:8081/eureka/
   ```

#### crm-util (工具类模块)

创建crm-common项目，公共模块主要负责管理项目的通用的依赖信息和工具类

1. 创建crm-common项目

   ![image-20221115230725819](image/image-20221115230725819.png)

2. 添加依赖信息

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   <dependency>
       <groupId>com.auth0</groupId>
       <artifactId>java-jwt</artifactId>
       <version>3.11.0</version>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-config-client</artifactId>
   </dependency>
   <dependency>
       <groupId>com.baomidou</groupId>
       <artifactId>mybatis-plus-boot-starter</artifactId>
       <version>3.4.2</version>
   </dependency>
   <dependency>
       <groupId>com.baomidou</groupId>
       <artifactId>mybatis-plus-generator</artifactId>
       <version>3.5.3</version>
   </dependency>
   <dependency>
       <groupId>org.apache.velocity</groupId>
       <artifactId>velocity</artifactId>
       <version>1.7</version>
   </dependency>
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
   </dependency>
   <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger2</artifactId>
       <version>2.9.2</version>
   </dependency>
   ```

3. 添加常用工具类

   > 后面根据项目实际功能，动态添加

#### crm-resource（通用配置模块）

1. 创建crm-resource项目

   

2. 添加项目依赖信息

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-config-client</artifactId>
   </dependency>
   <dependency>
       <groupId>com.baomidou</groupId>
       <artifactId>mybatis-plus-boot-starter</artifactId>
   </dependency>
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
   </dependency>
   <dependency>
       <groupId>com.spring4all</groupId>
       <artifactId>swagger-spring-boot-starter</artifactId>
   </dependency>
   ```

3. 添加通用配置类

   

#### crm-customer（客户管理服务）

1. 创建crm-customer项目

   ![image-20221115231738336](image/image-20221115231738336.png)
   
2. 添加依赖信息**crm-common**

   ```xml
   <dependency>
       <groupId>com.briup</groupId>
       <artifactId>crm-common</artifactId>
   </dependency>
   ```

3. 在resource目录下创建bootstrap.yml文件，并配置config-server信息

   ```yaml
   spring:
     cloud:
       config:
         discovery:
           enabled: true
           service-id: config-server
         name: crm
         profile: customer
         label: master
   eureka:
     instance:
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
       prefer-ip-address: true
     client:
       service-url:
         defaultZone: http://localhost:8081/eureka/
   ```

4. 添加注解：**@EnableDiscoveryClient**

   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class CrmCustomerApplication {
       public static void main(String[] args) {
           SpringApplication.run(CrmCustomerApplication.class, args);
       }
   }
   ```

#### crm-martketing（营销管理服务）

1. 创建crm-marketing项目

   ![image-20221115231706131](image/image-20221115231706131.png)
   
2. 添加依赖信息**crm-common**

   ```xml
   <dependency>
       <groupId>com.briup</groupId>
       <artifactId>crm-common</artifactId>
   </dependency>
   ```

3. 在resource目录下创建bootstrap.yml文件，并配置config-server信息

   ```yaml
   spring:
     cloud:
       config:
         discovery:
           enabled: true
           service-id: config-server
         name: crm
         profile: marketing
         label: master
   eureka:
     instance:
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
       prefer-ip-address: true
     client:
       service-url:
         defaultZone: http://localhost:8081/eureka/
   ```

4. 添加注解：**@EnableDiscoveryClient**

   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class CrmCustomerApplication {
       public static void main(String[] args) {
           SpringApplication.run(CrmCustomerApplication.class, args);
       }
   }
   ```

#### crm-report （统计管理服务）

1. 创建crm-report项目

   ![image-20221115231642174](image/image-20221115231642174.png)
   
2. 添加依赖信息**crm-common**

   ```xml
   <dependency>
       <groupId>com.briup</groupId>
       <artifactId>crm-common</artifactId>
   </dependency>
   ```

3. 在resource目录下创建bootstrap.yml文件，并配置config-server信息

   ```yaml
   spring:
     cloud:
       config:
         discovery:
           enabled: true
           service-id: config-server
         name: crm
         profile: report
         label: master
   eureka:
     instance:
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
       prefer-ip-address: true
     client:
       service-url:
         defaultZone: http://localhost:8081/eureka/
   ```

4. 添加注解：**@EnableDiscoveryClient**

   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class CrmCustomerApplication {
       public static void main(String[] args) {
           SpringApplication.run(CrmCustomerApplication.class, args);
       }
   }
   ```

#### crm-service（服务管理服务）

1. 创建crm-service项目

   ![image-20221115231611279](image/image-20221115231611279.png)
   
2. 添加依赖信息**crm-common**

   ```xml
   <dependency>
       <groupId>com.briup</groupId>
       <artifactId>crm-common</artifactId>
   </dependency>
   ```

3. 在resource目录下创建bootstrap.yml文件，并配置config-server信息

   ```yaml
   spring:
     cloud:
       config:
         discovery:
           enabled: true
           service-id: config-server
         name: crm
         profile: service
         label: master
   eureka:
     instance:
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
       prefer-ip-address: true
     client:
       service-url:
         defaultZone: http://localhost:8081/eureka/
   ```

4. 添加注解：**@EnableDiscoveryClient**

   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class CrmCustomerApplication {
       public static void main(String[] args) {
           SpringApplication.run(CrmCustomerApplication.class, args);
       }
   }
   ```

#### crm-system（系统管理服务）

1. 创建crm-system项目

   ![image-20221115231541503](image/image-20221115231541503.png)
   
2. 添加依赖信息**crm-common**

   ```xml
   <dependency>
       <groupId>com.briup</groupId>
       <artifactId>crm-common</artifactId>
   </dependency>
   ```

3. 在resource目录下创建bootstrap.yml文件，并配置config-server信息

   ```yaml
   spring:
     cloud:
       config:
         discovery:
           enabled: true
           service-id: config-server
         name: crm
         profile: system
         label: master
   eureka:
     instance:
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
       prefer-ip-address: true
     client:
       service-url:
         defaultZone: http://localhost:8081/eureka/
   ```

4. 添加注解：**@EnableDiscoveryClient**

   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class CrmCustomerApplication {
       public static void main(String[] args) {
           SpringApplication.run(CrmCustomerApplication.class, args);
       }
   }
   ```

### 项目实现

#### Mybatis-Plus

在crm-customer子项目中，实现**分页查询客户信息**功能

1. 执行mybatis-plus提供的代码生成器生成代码。

   ```java
   package com.briup.crmcustomer;
   
   import com.baomidou.mybatisplus.generator.FastAutoGenerator;
   import com.baomidou.mybatisplus.generator.config.OutputFile;
   
   import java.util.Collections;
   
   public class Generator {
       public static void main(String[] args) {
           FastAutoGenerator.create("jdbc:mysql://localhost:3306/crm?serverTimezone=UTC", "root", "root")
                   .globalConfig(builder -> {
                       builder.author("briup")
                               .outputDir("D:\\crm\\crm-customer\\src\\main\\java");
                   })
                   .packageConfig(builder -> {
                       builder.parent("com.briup")
                               .moduleName("crmcustomer")
                               .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\crm\\crm-customer\\src\\main\\resources\\mapper"));
                   })
                   .strategyConfig(builder -> {
                       builder.addInclude("cst_customer")
                               .addFieldPrefix("cust_")
                               .addTablePrefix("cst_","sal","sys");
   
                   })
                   .execute();
       }
   }
   ```

3. 编写```MybatisPlusConfig```配置类

   ```java
   package com.briup.crmcustomer.config;
   
   import com.baomidou.mybatisplus.annotation.DbType;
   import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
   import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
   import org.mybatis.spring.annotation.MapperScan;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   @MapperScan(basePackages = "com.briup.crmcustomer.mapper")
   @Configuration
   public class MybatisPlusConfig {
       @Bean
       public MybatisPlusInterceptor interceptor(){
           MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
           interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
           return interceptor;
       }
   }
   
   ```
   
4. 编写```Swagger2Config```配置类

   ```java
   package com.briup.crmcustomer.config;
   
   import java.util.ArrayList;
   import java.util.Collections;
   import java.util.List;
   
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   import com.google.common.base.Predicate;
   import com.google.common.base.Predicates;
   
   import springfox.documentation.builders.ApiInfoBuilder;
   import springfox.documentation.builders.PathSelectors;
   import springfox.documentation.builders.RequestHandlerSelectors;
   import springfox.documentation.service.ApiInfo;
   import springfox.documentation.service.ApiKey;
   import springfox.documentation.service.AuthorizationScope;
   import springfox.documentation.service.SecurityReference;
   import springfox.documentation.spi.DocumentationType;
   import springfox.documentation.spi.service.contexts.SecurityContext;
   import springfox.documentation.spring.web.plugins.Docket;
   import springfox.documentation.swagger2.annotations.EnableSwagger2;
   
   @EnableSwagger2
   @Configuration
   public class Swagger2Config {
   
   	/**
   	 * 需要配置扫描controller的包路径
   	 */
   	@Bean
   	public Docket createRestApi() {
   		return new Docket(DocumentationType.SWAGGER_2)
   				.apiInfo(apiInfo())
   				.select()
   				.apis(RequestHandlerSelectors.basePackage("com.briup.crmcustomer.controller"))
   				.paths(PathSelectors.any())
   				.build()
   				.securitySchemes(security())
   				.securityContexts(securityContexts());
   
   	}
   
   	private ApiInfo apiInfo() {
   		return new ApiInfoBuilder()
   				.title("用户管理服务")
   				.description("欢迎访问briup官网，http://www.briup.com")
   				.termsOfServiceUrl("http://www.briup.com")
   				.version("1.0")
   				.build();
   	}
   	/**
   	 * 设置认证中显示的显示的基本信息
   	 */
   	private List<ApiKey> security() {
   		return Collections.singletonList(
   				new ApiKey("Authorization", "token", "header")
   		);
   	}
   
   	/**
   	 * 设置认证规则
   	 */
   	private List<SecurityContext> securityContexts() {
   
   		List<String> antPaths = new ArrayList<String>();
   		antPaths.add("/auth/**");
   
   		return Collections.singletonList(
   				SecurityContext.builder()
   						.securityReferences(defaultAuth())
   						.forPaths(antPathsCondition(antPaths))
   						.build()
   		);
   	}
   
   	/**
   	 * 返回认证路径的条件
   	 */
   	private Predicate<String> antPathsCondition(List<String> antPaths){
   
   		List<Predicate<String>> list = new ArrayList<>();
   
   		antPaths.forEach(path->list.add(PathSelectors.ant(path)));
   
   		return Predicates.or(list);
   
   	}
   
   	/**
   	 * 设置认证的范围，以及认证的字段名称
   	 */
   	private List<SecurityReference> defaultAuth() {
   		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
   		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
   		authorizationScopes[0] = authorizationScope;
   		return Collections.singletonList(
   				new SecurityReference("Authorization", authorizationScopes));
   	}
   }
   ```

5. 编写web层代码

   ```java
   @Api(tags = "客户管理")
   @RestController
   @RequestMapping("/auth/customer")
   public class CustomerController {
       @Autowired
       private ICustomerService service;
   
       @ApiOperation("分页查询客户信息")
       @GetMapping("/page")
       public Result findByPage(Integer pageNum,Integer pageSize){
           Page<Customer> page = service.page(new Page<>(pageNum, pageSize));
           return Result.success(page.getRecords());
       }
   }
   ```


#### Swagger

使用gateway网关实现了统一了各个服务的入口。gateway路由的swagger界面，可以路由到的所有工程的swagger聚合在一起。

1. 在crm-gateway项目的pom.xml文件中添加依赖信息：**swagger**、**gateway**、**eureka-client**

   ```xml
   <dependency>
       <groupId>com.spring4all</groupId>
       <artifactId>swagger-spring-boot-starter</artifactId>
       <version>2.0.1.RELEASE</version>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-gateway</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   ```

2. 添加配置信息

   ```yaml
   swagger:
     global-operation-parameters:
   ```
   
3. 添加gateway配置类```SwaggerProvider```

   > Swagger暂不支持webflux项目，所以Gateway里不能配置SwaggerConfig

   ```java
   package com.briup.crmgateway.config;
   
   import org.springframework.cloud.gateway.config.GatewayProperties;
   import org.springframework.cloud.gateway.route.RouteLocator;
   import org.springframework.cloud.gateway.support.NameUtils;
   import org.springframework.context.annotation.Primary;
   import org.springframework.stereotype.Component;
   import springfox.documentation.swagger.web.SwaggerResource;
   import springfox.documentation.swagger.web.SwaggerResourcesProvider;
   
   import java.util.ArrayList;
   import java.util.List;
   
   @Primary
   @Component
   public class SwaggerProvider implements SwaggerResourcesProvider {
       private static final String API_URI = "/v2/api-docs";
       private RouteLocator routeLocator;
       private GatewayProperties gatewayProperties;
   
       public SwaggerProvider(RouteLocator routeLocator,GatewayProperties gatewayProperties){
           this.routeLocator = routeLocator;
           this.gatewayProperties = gatewayProperties;
       }
       @Override
       public List<SwaggerResource> get() {
           List<String> routes = new ArrayList<>();
           routeLocator.getRoutes().subscribe(route->routes.add(route.getId()));
           List<SwaggerResource> list = new ArrayList<>();
           gatewayProperties.getRoutes().stream()
                   .filter(routeDefinition->routes.contains(routeDefinition.getId()))
                   .forEach(routeDefinition ->
                           routeDefinition.getPredicates().stream()
                                   .filter(predicateDefinition ->
                                           "Path".equalsIgnoreCase(predicateDefinition.getName()))
                                   .forEach(predicateDefinition -> {
                                       System.out.println(predicateDefinition);
                                       list.add(swaggerResource(routeDefinition.getId(),
                                               predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX+"0")
                                                       .replace("/**",API_URI)));
                                   })
                   );
           return list;
       }
   
   
       private SwaggerResource swaggerResource(String name,String location) {
           SwaggerResource swaggerResource = new SwaggerResource();
           swaggerResource.setName(name);
           swaggerResource.setLocation(location);
           swaggerResource.setSwaggerVersion("2.0");
           return swaggerResource;
       }
   }
   ```

4. 依次启动4个项目进行测试：

   - crm-eureka-server
   
   - crm-config-server

   - crm-customer
   
   - crm-system
   
   - crm-gateway
   
   访问网关swagger地址进行测试：
   
   ```http
   http://localhost:8888/swagger-ui/index.html
   ```
   
   ![image-20221114230307663](image/image-20221114230307663.png)

#### CORS

在application.yml文件中添加跨域配置

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        add-to-simple-url-handler-mapping: true   #允许options请求通过
        cors-configurations:
          '[/**]':
            allowedOrigins: "*"
            allowedMethods:
              - GET
              - POST
              - DELETE
              - PUT
            allowedHeaders: "*"
```

#### Auth

通过使用gatewa实现网关统一鉴权。使用redis缓存用户token信息和对应的用户信息，可以实现自动续时操作。

#### OpenFeign

> 统计管理服务使用OpenFeign实现远程调用客户管理服务中接口。

实现**统计管理**服务中**客户构成分析**模块的功能。