package com.wdc.service;

import com.wdc.pojo.Emp;
import com.wdc.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end);

    void delete(List<Integer> ids);

    void save(Emp emp);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    public Emp getById(Integer id);

    /**
     * 更新员工
     * @param emp
     */
    public void update(Emp emp);

    Emp login(Emp emp);
}
