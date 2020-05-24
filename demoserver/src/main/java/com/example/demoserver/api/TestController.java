package com.example.demoserver.api;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.example.demoserver.config.FlowRules;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @SentinelResource(value="HelloWorld",blockHandler = "blockHandlerForGetUser")
    @RequestMapping(value="/test")
    @ResponseBody
    public List<Map<String, Object>> test(){
        //开启流控
        FlowRules.initFlowRules();

        JSONObject json = new JSONObject();
        json.put("content", "This is the test Service.");
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(json.getInnerMap());
        return result;
    }

    public  List<Map<String, Object>> blockHandlerForGetUser(BlockException ex) {
        JSONObject json = new JSONObject();
        json.put("notify", "Blocked.");
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(json.getInnerMap());
        return result;
    }
}
