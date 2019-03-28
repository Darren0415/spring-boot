package com.darren.center.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.darren.center.springboot.common.ResponseHelper;
import com.darren.center.springboot.common.WebStatusEnum;
import com.darren.center.springboot.entity.User;
import com.darren.center.springboot.service.UserService;
import com.darren.center.springboot.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test(){
        return "user/test";
    }

    @RequestMapping("/list")
    public String list(){
        return "user/list";
    }

    /**
     * 返回json数据
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @ResponseBody
    public ResponseHelper listPage(@RequestParam Map<String, Object> params){
        List<User> users = userService.selectUserList(params);
        Integer pageCount = userService.selectUserListPageCount(params);
        return ResponseUtils.response(WebStatusEnum.SUCCESS.getCode(), WebStatusEnum.SUCCESS.getMsg(), users, pageCount);
    }

    /**
     * 跳转到编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        User user = userService.selectByPrimaryKey(Long.valueOf(id));
        model.addAttribute("user", user);
        return "user/edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResponseHelper save(User user){
        int flag;
        if (null == user.getId()){
            flag = userService.addUser(user);
        }else {
            flag = userService.editUserById(user);
        }
        ResponseHelper responseHelper = ResponseUtils.responseWrap(flag);
        System.out.println(JSONObject.toJSONString(responseHelper));
        return responseHelper;
    }
}