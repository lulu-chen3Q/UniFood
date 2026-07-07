package com.unifood.service;

import com.unifood.dto.EmployeeDTO;
import com.unifood.dto.EmployeeLoginDTO;
import com.unifood.dto.EmployeePageQueryDTO;
import com.unifood.entity.Employee;
import com.unifood.result.PageResult;

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

    /**
     * 分页查询
     * @param employeePageQueryDTO
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据id查询员工
     *
     * @param id
     * @return
     */
    Employee getById(Long id);
    /**
     * 编辑员工信息
     *
     * @param employeeDTO
     * @return
     */
    void updata(EmployeeDTO employeeDTO);
}
