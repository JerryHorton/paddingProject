package com.example.padding.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.padding.common.R;
import com.example.padding.entity.User;
import com.example.padding.service.UserService;
import com.example.padding.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @Date 2023/6/28 14:19
 * @Description 用户控制器
 * @Author Sxy
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 分页查询用户信息
     *
     * @param page     当前页面号
     * @param pageSize 每页数据条数
     * @param name     查询条件(可以为空)
     * @return 分页结果(records中封装查询数据)
     */
    @GetMapping("/list")
    public R<Page<User>> page(int page, int pageSize, String name) {
        log.info("page:{} pageSize:{}", page, pageSize);
        Page<User> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(name), User::getUsername, name);
        queryWrapper.orderByAsc(User::getId);
        userService.page(pageInfo);
        return R.success(pageInfo);
    }

    /**
     * 登录
     *
     * @param user {username : ..., password:...}
     * @return
     */
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody User user) {
        //将页面提交的密码password进行MD5加密处理,与数据库中加密密码比对
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User usr = userService.getOne(queryWrapper);
        //如果没有查询到则返回登录失败
        if (usr == null) {
            return R.error("用户名不存在");
        } else {
            //密码比对，如果不一致则返回登录失败
            if (!password.equals(usr.getPassword())) {
                return R.error("密码错误");
            }
        }
        //登录成功，将员工id存入Session并返回登录结果
        //request.getSession().setAttribute("user", usr.getId());
        String token = JwtUtils.createToken(user.getId(), user.getUsername(), 2 * 60 * 60);
        //sesson中存入token
        //request.getSession().setAttribute("token", token);
        log.info("用户{}登录成功", usr.getUsername());
        log.info("token:{}", token);
        //登录成功返回用户信息，用于用户信息回显
        return R.success(token);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录用户id
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }

    /**
     * 新增用户
     *
     * @param request
     * @param user    {username : ..., password:...}
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody User user) {
        log.info("新增员工: {}", user);
        //设置初始密码：123456，进行MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //保存员工信息到数据库
        userService.save(user);
        return R.success("新增员工成功");
    }
}
