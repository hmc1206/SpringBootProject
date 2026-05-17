package com.example.Test.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //@RestController를 이용해서 별도의 화면 없이 데이터를 전송하고자 한다.
public class SampleController {
    @GetMapping("/hello")  //localhost:8081/hello
    public String[] hello(){ //hello()는 @GetMapping을 이용해서 브라우저의 주소창에서 호출이 가능하도록 설정
        return new String[]{"hello", "world"};
    }
}
