package com.wdc.service;

import com.wdc.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> list();

    void delete(Integer id);

    void add(Dept dept);
}
