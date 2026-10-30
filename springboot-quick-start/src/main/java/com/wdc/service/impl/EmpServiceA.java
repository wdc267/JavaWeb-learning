package com.wdc.service.impl;

import com.wdc.dao.EmpDao;
import com.wdc.dao.impl.EmpDaoA;
import com.wdc.pojo.Emp;
import com.wdc.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//业务逻辑实现类（按照业务标准实现）
@Service
public class EmpServiceA implements EmpService {
        //dao层对象
//        private EmpDao empDao = new EmpDaoA();
        @Autowired
        private EmpDao empDao;
        @Override
        public List<Emp> listEmp() {
            //1. 调用dao, 获取数据
            List<Emp> empList = empDao.listEmp();
            //2. 对数据进行转换处理 - gender, job
            empList.stream().forEach(emp -> {
                        //处理 gender 1: 男, 2: 女
                        String gender = emp.getGender();
                        if("1".equals(gender)){
                                    emp.setGender("男");
                    }else if("2".equals(gender)){
                                    emp.setGender("女");
                    }
                        //处理job - 1: 讲师, 2: 班主任 , 3: 就业指导
                        String job = emp.getJob();
                    if("1".equals(job)){
                                    emp.setJob("讲师");
                    }else if("2".equals(job)){
                                    emp.setJob("班主任");
                    }else if("3".equals(job)){
                                    emp.setJob("就业指导");
                    }
            });
            return empList;
    }
}
