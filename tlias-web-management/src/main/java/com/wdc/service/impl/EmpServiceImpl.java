package com.wdc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdc.anno.Log;
import com.wdc.mapper.EmpMapper;
import com.wdc.pojo.Emp;
import com.wdc.pojo.PageBean;
import com.wdc.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end){
//        Long count = empMapper.count();
//        Integer start = (page - 1) * pageSize;
//        List<Emp> empList = empMapper.list(start, pageSize);
//        PageBean pageBean = new PageBean(count, empList);
        PageHelper.startPage(page, pageSize);
        List<Emp> empList = empMapper.list(name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) empList;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    @Log
    public void delete(List<Integer> ids){
        empMapper.deleteByIds(ids);
    }

    @Override
    @Log
    public void save(Emp emp){
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.findById(id);
    }

    @Override
    @Log
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now()); //更新修改时间为当前时间
        empMapper.update(emp);
    }

    @Override
    public Emp login(Emp emp){
        Emp loginEmp = empMapper.getByUsernameAndPassword(emp);
        return loginEmp;
    }

}
