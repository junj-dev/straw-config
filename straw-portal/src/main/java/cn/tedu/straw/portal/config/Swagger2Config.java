package cn.tedu.straw.portal.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean
    public Docket portal_member_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**")).build().groupName("问答系统").pathMapping("/")
                .apiInfo(portal_member_apiInfo())
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        getListResponseMessage())
                .globalResponseMessage(RequestMethod.POST,
                        getListResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE,
                        getListResponseMessage())
                .enableUrlTemplating(true);
    }

    @Autowired
    private TypeResolver typeResolver;

    private ArrayList<ResponseMessage> getListResponseMessage() {
        return newArrayList(new ResponseMessageBuilder()
                        .code(500)
                        .message("系统错误")
                        .responseModel(new ModelRef("系统错误"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(400)
                        .message("请求错误")
                        .responseModel(new ModelRef("请求错误"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("参数不对")
                        .responseModel(new ModelRef("参数不对"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(402)
                        .message("参数值不对")
                        .responseModel(new ModelRef("参数值不对"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("访问的资源不存在")
                        .responseModel(new ModelRef("访问的资源不存"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(408)
                        .message("请求超时")
                        .responseModel(new ModelRef("请求超时"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(501)
                        .message("请登录")
                        .responseModel(new ModelRef("请登录"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(503)
                        .message("服务不可用")
                        .responseModel(new ModelRef("服务不可用"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(504)
                        .message("连接超时")
                        .responseModel(new ModelRef("连接超时"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(505)
                        .message("更新数据失败")
                        .responseModel(new ModelRef("更新数据失败"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(506)
                        .message("无操作权限")
                        .responseModel(new ModelRef("无操作权限"))
                        .build());
    }


    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    private ApiInfo portal_member_apiInfo() {
        return new ApiInfoBuilder()
                .title("稻草问答系统API文档")//标题
                .description("API接口文档")//描述
                .version("1.0.0")//版本号
                .contact(new Contact("陈海宝", "", "chenhb@tedu.cn"))
                .build();
    }
}
