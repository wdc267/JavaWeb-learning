package com.wdc.mapper;

import com.wdc.pojo.Emp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
//    @Select("select  count(*) from emp")
//    public Long count();
//
//    @Select("select * from emp limit #{start}, #{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize);

    //根据ID查询员工信息
    @Select("select id, username, password, name, gender, image,job, entrydate, dept_id, create_time, update_time "
            +"from emp where id = #{id}")
    public Emp findById(Integer id);

    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    void deleteByIds(List<Integer> ids);

    @Insert("insert into emp (username, name, gender, image, job,entrydate, dept_id, create_time, update_time) " +
            "values (#{username}, #{name}, #{gender}, #{image}, #{job}, #{entrydate}, #{deptId}, #{createTime}, #{updateTime});")
    void insert(Emp emp);

    //修改员工信息
    public void update(Emp emp);

    @Select("select id, username, password, name, gender, image,job, entrydate, dept_id, create_time, update_time from emp " +
            "where username=#{username} and password =#{password}")
    Emp getByUsernameAndPassword(Emp emp);
}
