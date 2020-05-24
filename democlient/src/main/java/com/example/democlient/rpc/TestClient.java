package com.example.democlient.rpc;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class TestClient {

    private final LoadBalancerClient loadBalancerClient;

    public TestClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    @RequestMapping("/test")
    public List<Map<String, Object>> test(){
        ServiceInstance si = this.loadBalancerClient.choose("eureka-application-service");
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(si.getHost()).append(":").append(si.getPort()).append("/test");
        System.out.println("service url : "+sb.toString());

        // SpringMVC RestTemplate，用于快速发起REST请求的模板对象。
        /*
         * RestTemplate是SpringMVC提供的一个用于发起REST请求的模板对象。
         * 基于HTTP协议发起请求的。
         * 发起请求的方式是exchange。需要的参数是： URL, 请求方式， 请求头， 响应类型，【URL rest参数】。
         */
        RestTemplate rt = new RestTemplate();

        /*
         * 创建一个响应类型模板。
         * 就是REST请求的响应体中的数据类型。
         * ParameterizedTypeReference - 代表REST请求的响应体中的数据类型。
         */
        ParameterizedTypeReference<List<Map<String, Object>>> type =
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                };

        /*
         * ResponseEntity:封装了返回值信息，相当于是HTTP Response中的响应体。
         * 发起REST请求。
         */
        ResponseEntity<List<Map<String, Object>>> response =
                rt.exchange(sb.toString(), HttpMethod.GET, null, type);
        /*
         * ResponseEntity.getBody() - 就是获取响应体中的java对象或返回数据结果。
         */
        List<Map<String, Object>> result = response.getBody();

        return result;
    }
}
