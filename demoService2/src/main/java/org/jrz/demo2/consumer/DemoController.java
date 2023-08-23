package org.jrz.demo2.consumer;

//import org.jrz.demo.service.UserService;
import org.jrz.rpc.annotation.JrpcRemote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {


/*    @JrpcRemote
    private UserService userService;*/


    @RequestMapping("/hello")
    public String hello() {

        return "hello";

    }
}
