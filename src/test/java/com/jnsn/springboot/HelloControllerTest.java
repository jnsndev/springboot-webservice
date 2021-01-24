package com.jnsn.springboot;

import com.jnsn.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// RunWith: 스프링부트 테스트와 JUnit 사이에 연결자 역할 (SpringRunner라는 스프링 실행자를 사용)
// WebMvcTest: web에 집중할 수 있는 스프링 테스트 어노테이션
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    // Autowired: 스프링이 관리하는 빈을 주입 받는다.
    // MockMvc: 웹 API를 테스트할 때 사용, 스프링 MVC 테스트의 시작점, HTTP GET, POST 등에 대한 API 테스트
    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello!";

        // MockMvc를 통해 /hello 주소로 HTTP GET 요청
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)    // param: API 테스트 시, 사용될 요청 파라미터 설정 (String만 허용)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    // jsonPath: JSON 응답값을 필드별로 검증, $를 기준으로 필드명 명시
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}
