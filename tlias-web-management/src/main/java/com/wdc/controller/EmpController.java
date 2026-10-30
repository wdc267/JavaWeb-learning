package com.wdc.controller;

import com.wdc.pojo.Emp;
import com.wdc.pojo.PageBean;
import com.wdc.pojo.Result;
import com.wdc.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,
                       Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        PageBean pageBean = empService.page(page, pageSize, name, gender, begin, end);
        return Result.success(pageBean);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        empService.delete(ids);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Emp emp){
        //记录日志
        log.info("新增员工, emp:{}",emp);
        //调用业务层新增功能
        empService.save(emp);
        //响应
        return Result.success();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    //修改员工
    @PutMapping
    public Result update(@RequestBody Emp emp){
        empService.update(emp);
        return Result.success();
    }
}
