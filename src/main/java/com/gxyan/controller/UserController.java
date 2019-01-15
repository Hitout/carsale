package com.gxyan.controller;

import com.gxyan.common.Const;
import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Employee;
import com.gxyan.service.IEmployeeService;
import com.gxyan.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gxyan
 * @date 2018/12/26 20:39
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServerResponse login( String employeeId, String password, HttpSession session) {
        ServerResponse response = userService.login(Integer.valueOf(employeeId), password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());

            Map<String, String> map = new HashMap <>(1);
            map.put("token", session.getId());
            response = ServerResponse.createBySuccess(map);
        }
        log.info("userId:{}, password:{}, data:{}", employeeId, password, response.getData());
        return response;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "info", method = RequestMethod.POST)
    public ServerResponse<Employee> info(HttpSession session) {
        Employee employee = (Employee) session.getAttribute(Const.CURRENT_USER);
        if (employee == null) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        return ServerResponse.createBySuccess(employee);
    }

    @RequestMapping(value = "updateMessage", method = RequestMethod.POST)
    public ServerResponse updateMessage(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @RequestMapping(value = "validPassword", method = RequestMethod.POST)
    public ServerResponse validPassword(HttpSession session, String validPass) {
        Employee employee = (Employee) session.getAttribute(Const.CURRENT_USER);
        return employeeService.validPassword(employee.getId(), validPass);
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public ServerResponse updatePassword(HttpSession session, String oldPass, String newPass) {
        Employee employee = (Employee) session.getAttribute(Const.CURRENT_USER);
        return employeeService.updatePassword(employee.getId(), oldPass, newPass);
    }
}
