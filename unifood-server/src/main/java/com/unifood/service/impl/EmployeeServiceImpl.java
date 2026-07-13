package com.unifood.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.unifood.constant.MessageConstant;
import com.unifood.constant.PasswordConstant;
import com.unifood.constant.StatusConstant;
import com.unifood.context.BaseContext;
import com.unifood.dto.EmployeeDTO;
import com.unifood.dto.EmployeeLoginDTO;
import com.unifood.dto.EmployeePageQueryDTO;
import com.unifood.entity.Employee;
import com.unifood.exception.AccountLockedException;
import com.unifood.exception.AccountNotFoundException;
import com.unifood.exception.PasswordErrorException;
import com.unifood.mapper.EmployeeMapper;
import com.unifood.result.PageResult;
import com.unifood.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    /**
     * 新增员工业务方法
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
    Employee employee =new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        //状态
        employee.setStatus(StatusConstant.ENABLE);
        //设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置当前创建 更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //当前登录哦用户id
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }
    /**
     * 分页查询
     * @param employeePageQueryDTO
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
    //selec page from empoyee where 。。。limit
        //开始分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long totals = page.getTotal();
        List<Employee> records = page.getResult();
        return new PageResult(totals,records);
    }
    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        //根据员工的id 设置他的启用禁用员工账号
        //update employee set status = ？ id =？
        //动态sql 封装到实体类
        Employee employee = Employee.builder()
                        .status(status)
                                .id(id)
                                        .build();
        employeeMapper.update(employee);
    }
    /**
     * 根据id查询员工
     *
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        //select * from employee where id = #{id}
        Employee employee = employeeMapper.getById(id);
        //虽然密码加密过了 但还是不想给前端看
        employee.setPassword("****");
        return employee;
    }
    /**
     * 编辑员工信息
     *
     * @param employeeDTO
     * @return
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        //update在mapper的update方法可以复用 但是mapper那里要的是employee对象
        //所以这里做一个数据转换 创建一个空employee 然后对象属性拷贝
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        //dto里并没有创建时间 修改时间 需要单独设置
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }
}
