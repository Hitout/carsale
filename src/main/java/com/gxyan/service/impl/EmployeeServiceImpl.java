package com.gxyan.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxyan.common.Const;
import com.gxyan.common.ServerResponse;
import com.gxyan.dao.EmployeeMapper;
import com.gxyan.pojo.Employee;
import com.gxyan.service.IEmployeeService;
import com.gxyan.vo.EmployeeQuery;
import com.gxyan.vo.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/6 16:55
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ServerResponse addEmployee(Employee employee) {
        employee.setId(createEmployeeId());
        employee.setRole(Const.Number.ONE);
        log.info(employee.toString());
        int resultCount = employeeMapper.insert(employee);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse updateEmployee(Employee employee) {
        int resultCount = employeeMapper.updateByPrimaryKeySelective(employee);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse getList(EmployeeQuery employeeQuery) {
        List<Employee> list = PageHelper.startPage(employeeQuery.getPage(), employeeQuery.getLimit()).doSelectPage(()-> employeeMapper.selectSelective(employeeQuery));
        if (list != null) {
            ListVo listVo = new ListVo();
            listVo.setItems(list);
            listVo.setTotal(PageHelper.count(()->employeeMapper.selectSelective(employeeQuery)));
            return ServerResponse.createBySuccess(listVo);
        }
        return ServerResponse.createByErrorMessage("获取客户列表失败");
    }

    @Override
    public ServerResponse validPassword(Integer id, String validPass) {
        String password = employeeMapper.selectPasswordByPrimaryKey(id);
        if (password.equals(validPass)) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse updatePassword(Integer id, String oldPass, String newPass) {
        int resultCount = employeeMapper.updatePasswordByPrimaryKeyAndOldPass(id, oldPass, newPass);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    /**
     * 客户编号
     * 格式为：yyMM 加 三位递增的数字，数字每月重置为1
     * @return
     */
    private Integer createEmployeeId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String format = dateFormat.format(new Date()) + "000";
        return Integer.valueOf(format) + (num++);
    }

    private int num = 1;

    @Scheduled(cron="0 0 0 0 * ?")
    private void clearNum() {
        num = 1;
    }
}
