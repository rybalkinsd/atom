package ru.atom.boot.hw;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("hello")
public class HelloController {

    /**
     * curl test
     * <p>
     * curl -i localhost:8080/hello/world
     </p>*/
    @RequestMapping("world")
    @ResponseBody
    public String hi() {
        return "Hello, Spring-boot World!";
    }
}
