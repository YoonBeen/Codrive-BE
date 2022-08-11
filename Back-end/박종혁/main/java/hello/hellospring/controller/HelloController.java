package hello.hellospring.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")//http 주소 창에서 /hello 라는 창을 들어오게 될때 나타내는 페이지를 설명한다. (정적컨텐츠)
    public String Hello(@NotNull Model model){
    model.addAttribute("data","Hello!!");
    return "hello";
    }
    @GetMapping("hello-mvc") //mvc엔진
    public String helloMvc(@RequestParam(value = "name") String name ,Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }
    @GetMapping("hello-string") //API예시
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
    return "hello"+ name;
    }
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static  class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
