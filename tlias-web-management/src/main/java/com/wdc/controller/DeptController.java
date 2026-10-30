package com.wdc.controller;

import com.wdc.pojo.Dept;
import com.wdc.pojo.Result;
import com.wdc.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result list(){
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        deptService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门：{}",dept);
        deptService.add(dept);
        return Result.success();
    }
}
