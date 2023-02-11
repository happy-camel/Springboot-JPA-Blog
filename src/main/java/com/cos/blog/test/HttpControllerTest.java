package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//사용자 요청 -> 응답(HTML파일)
//@Controller

//사용자 요청 -> 응답(data)
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpVontrollerTest : ";

    //localhost:8000/blog/http/lombok
    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
        System.out.println(TAG+"getter : "+m.getUsername());
        m.setUsername("cos");
        System.out.println(TAG+"getter : "+m.getUsername());
        return "lombok test 완료";
    }

    //인터넷 브라우저에서 요청은 get밖에 못한다. 왜? 바디를 못보내니까
    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청:" + m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
    }
    @PostMapping("http/post")//text/plain, application/json
    public String postTest(@RequestBody String text) {
        return "post 요청"+ text;
    }
    @PutMapping("http/put")
    public String putTest() {
        return "put 요청";
    }
    @DeleteMapping("http/delete")
    public String deleteTest() {
        return "delete 요청";
    }

}
