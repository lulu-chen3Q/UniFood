package com.unifood.service;

import com.unifood.dto.EmployeeDTO;
import com.unifood.dto.EmployeeLoginDTO;
import com.unifood.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工业务方法
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);
}
