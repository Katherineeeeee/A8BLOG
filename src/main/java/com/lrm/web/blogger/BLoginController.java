package com.lrm.web.blogger;

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
@RequestMapping("/blogger")
public class BLoginController {


    @Autowired
    private UserService userService;

    private static long id = 1;
    private int type = 2;
    private String avatar = "https://w.wallhaven.cc/full/ey/wallhaven-eyl1xr.jpg";//默认头像

    @GetMapping
    public String loginPage() {
        return "blogger/login";
    }
    /*@PostMapping
    public String loginPage() {
        return "blogger/login";
    }*/



    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        System.out.println(username+" "+password);
        if (user != null && user.getType() == 1) {

//            user.setPassword(null);
//            session.setAttribute("user",user);
            return null;
        } else if(user != null && user.getType() == 2){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "blogger/index";
        } else{
            attributes.addFlashAttribute("message", "wrong user name or password");
            return "redirect:/blogger";
        }
    }

    @GetMapping("/signup")
    public String signup() {
        return "blogger/signuppage";
    }

    @PostMapping("/signuppage")
    public String signuppage(@RequestParam String nickname,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam String cpassword,
                             @RequestParam String avatar,
                             HttpSession session,
                             RedirectAttributes attributes) {
        if(!password.equals(cpassword)){
            attributes.addFlashAttribute("message", "The two passwords are inconsistent");
            return "redirect:/blogger/signup";
        }
        System.out.println(password);
        password = MD5Utils.code(password);
        System.out.println(password);
        id++;
        User user = new User(id,nickname,username,password,email,avatar,type,new Date());
        try {
            userService.saveAll(user);
        }catch(Exception e){
            attributes.addFlashAttribute("message", "The avatar is too long!");
            return "redirect:/blogger/signup";
        }
        attributes.addFlashAttribute("message", "Sign up successfully!");
        return "redirect:/blogger";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/begin";
    }
}
