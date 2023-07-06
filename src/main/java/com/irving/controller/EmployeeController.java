package com.irving.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irving.commonly.R;
import com.irving.utlis.ThreadLocalUtils;
import com.irving.domain.Employee;
import com.irving.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
//employee/login
@RequestMapping("/employee")
@Controller
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @PostMapping("/login")
    @ResponseBody
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        R<Employee> employeeR = employeeService.login(employee);
        if (employeeR.getData() != null) {
            employeeR.getData().setPassword(null);
            request.getSession().setAttribute("employee", employeeR.getData());
            ThreadLocalUtils.SetObject(employeeR.getData());
        }
        return employeeR;
    }

    @PostMapping("/logout")
    @ResponseBody
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @Transactional
    @ResponseBody
    @PostMapping()
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request) {


        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        boolean save = false;
        save = employeeService.save(employee);

        if (save) {
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }


    @ResponseBody
    @GetMapping("/page")
    public R<Page<Employee>> page(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer size, @RequestParam(value = "name", required = false) String name) throws JsonProcessingException {
        if (name == null) {
            Page<Employee> pageOne = employeeService.getPage(page, size);
            System.out.println("哈哈哈哈哈哈" + pageOne.getRecords());
            return R.success(pageOne);
        }
        Page<Employee> page1 = new Page<>();
        page1.setPages(page);
        page1.setSize(size);
        LambdaQueryWrapper<Employee> lq = new LambdaQueryWrapper<>();
        lq.like(Employee::getName, name);
        Page<Employee> page2 = employeeService.page(page1, lq);
        return R.success(page2);
    }

    @ResponseBody
    @PutMapping()
    public R<String> update(@RequestBody Employee employee, HttpServletRequest request) {
        employee.setUpdateUser(((Employee) request.getSession().getAttribute("employee")).getId());
        boolean b = employeeService.updateById(employee);
        if (b) {
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @ResponseBody
    @GetMapping("/{id}")
    public R<Employee> Get(@PathVariable("id") Long id) {
        LambdaQueryWrapper<Employee> lq = new LambdaQueryWrapper();
        lq.eq(Employee::getId, id);
        Employee one = employeeService.getOne(lq);
        log.info(id.toString());
        return R.success(one);
    }

}
