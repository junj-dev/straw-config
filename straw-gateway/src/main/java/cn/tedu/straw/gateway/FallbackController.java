package cn.tedu.straw.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 服务熔断控制器
 * @Author: ChenHaiBao
 * @CreateDate: 2020/4/13$ 15:53$
 * @Version: 1.0
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    @ResponseBody
    public Map<String,Object> fallbackA() {
        Map<String,Object> result=new HashMap<>();
        result.put("code","500");
        result.put("msg","服务不可用，请稍后再试！");
        return result;
    }
}