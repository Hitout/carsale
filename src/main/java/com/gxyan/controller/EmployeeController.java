package com.gxyan.controller;

import com.gxyan.common.Const;
import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Employee;
import com.gxyan.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author gxyan
 * @date 2018/12/26 20:39
 */
@Slf4j
@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServerResponse<Employee> login(String userId, String password, HttpSession session) {
        ServerResponse<Employee> response = userService.login(Integer.valueOf(userId), password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        log.info("userId:{}, password:{}, data:{}", userId, password, response.getData());
        return response;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ServerResponse<Employee> info(HttpSession session) {
        Employee employee = (Employee) session.getAttribute(Const.CURRENT_USER);
        if (employee == null) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        log.info("get info:{}",employee.toString());
        return ServerResponse.createBySuccess();
    }
}
