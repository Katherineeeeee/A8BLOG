package com.lrm.web.admin;

import com.lrm.po.User;
import com.lrm.service.UserService;
import com.lrm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limi on 2017/10/15.
 */
@Controller


@RequestMapping("/admin")
public class ALoginController {


    @Autowired
    private UserService userService;

    private static long id = 1;
    private int type = 1;
    private String avatar = null;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }



    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null && user.getType() == 1) {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        } else if(user != null && user.getType() == 2){
            //attributes.addFlashAttribute("message", "You Are The Administrator");
            //return null;
            user.setPassword(null);
            session.setAttribute("user",user);
            return "blogger/index";
        } else{
            attributes.addFlashAttribute("message", "wrong user name or password");
            return "redirect:/admin";
        }
    }

    @GetMapping("/signup")
    public String signup() {
        return "admin/signuppage";
    }

    @PostMapping("/signuppage")
    public String signuppage(@RequestParam String nickname,
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String email,
                        @RequestParam String cpassword,
                        HttpSession session,
                        RedirectAttributes attributes) {
        if(!password.equals(cpassword)){
            attributes.addFlashAttribute("message", "The two passwords are inconsistent");
            return "redirect:/admin/signup";
        }
        System.out.println(password);
        password = MD5Utils.code(password);
        System.out.println(password);
        id++;
        User user = new User(id,nickname,username,password,email,avatar,type,new Date());
        userService.saveAll(user);
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/begin";
    }
}
