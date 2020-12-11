package com.lrm.web.begin;

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
 * Created by ronanjou on 2020/12/09.
 */
@Controller
@RequestMapping("/begin")
public class BeginController {


    @Autowired
    private UserService userService;

    private static long id = 1;
    private int type = 1;
    private String avatar = null;

    @GetMapping
    public String BeginPage() {
        return "begin";
    }



}
