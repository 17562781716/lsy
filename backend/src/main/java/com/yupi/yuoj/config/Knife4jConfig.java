package com.yupi.yuoj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Knife4jConfig {

    @Bean
    public Docket allApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("All APIs")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.yuoj.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("User Management")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.yuoj.controller"))
                .paths(PathSelectors.ant("/user/**"))
                .build();
    }

    @Bean
    public Docket themeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Theme Management")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.yuoj.controller"))
                .paths(PathSelectors.regex("/theme/.*"))
                .build();
    }

    @Bean
    public Docket userThemeWeekApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("User Theme Week")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.yuoj.controller"))
                .paths(PathSelectors.regex("/user/theme/week.*"))
                .build();
    }

    @Bean
    public Docket weekTaskApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Task Checkin")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.yuoj.controller"))
                .paths(PathSelectors.regex("/week/.*|/checkin/.*"))
                .build();
    }

    @Bean
    public Docket postApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Content Management")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.yuoj.controller"))
                .paths(PathSelectors.regex("/post.*"))
                .build();
    }

    @Bean
    public Docket userDataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("User Data")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.yuoj.controller"))
                .paths(PathSelectors.regex("/user/favorite.*|/user/browse.*|/user/message.*"))
                .build();
    }

    @Bean
    public Docket fileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("File Management")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yupi.yuoj.controller"))
                .paths(PathSelectors.regex("/file/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ThemeWeek主题周学习平台")
                .description("## ThemeWeek主题周学习平台 API接口文档\n\n**接口调用地址**: http://localhost:8121/api\n\n**功能模块**:\n\n### 👤 用户管理\n- 用户注册、登录、退出\n- 用户信息查询、更新\n- 用户列表管理（管理员）\n\n### 📁 主题管理\n- 主题分类：学习、运动、美育等12个分类\n- 主题模板：阅读周、跑步周等30+主题模板\n\n### 📅 用户主题周\n- 创建个人主题周计划\n- 管理已创建的主题周\n\n### ✅ 任务打卡\n- 周任务管理\n- 每日打卡记录\n- 打卡统计\n\n### 📝 内容管理\n- 帖子发布、编辑、删除\n- 帖子收藏\n- 帖子点赞\n\n### 💾 用户数据\n- 用户收藏管理\n- 浏览历史记录\n- 消息通知\n\n### 📤 文件管理\n- 文件上传")
                .contact(new Contact("ThemeWeek团队", "", ""))
                .version("1.0.0")
                .build();
    }
}
