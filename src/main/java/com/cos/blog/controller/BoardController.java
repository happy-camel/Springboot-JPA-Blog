package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

//    컨트롤러에서 세션을 어떻게 찾?
//    @AuthenticationPrincipal PrincipalDetail principal
    @GetMapping({"", "/"})
    public String index(Model model) {
//        WEB-INF/views/index.jsp

//        System.out.println("로그인 사용자 아이디 : " +principal.getUsername());
        //index페이지로 boards값이 들어간다
        model.addAttribute("boards", boardService.글목록());
        return "index";//컨트롤러는 리턴시 viewResolver작동
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
