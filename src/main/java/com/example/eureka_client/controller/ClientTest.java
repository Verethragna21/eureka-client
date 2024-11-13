package com.example.eureka_client.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientTest {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/get-greeting-no-feign")
    public ResponseEntity<?> getGreetings() {

        InstanceInfo service = eurekaClient
                .getApplication("greetings-service")
                .getInstances()
                .get(0);

        String hostName = service.getHostName();
        int port = service.getPort();

        return ResponseEntity.ok("Service is located on: " + hostName + " " + port);
    }

    @GetMapping("/greeting")
    public String greeting() {
        return String.format(
                "Hello from '%s'|", eurekaClient.getApplication(appName).getName()
        );
    }

}
