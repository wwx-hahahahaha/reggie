package com.irving.commonly;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.irving.domain.Employee;
import com.irving.domain.User;
import com.irving.utlis.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        long id = Thread.currentThread().getId();
        log.info(String.valueOf(id));
        Object objects = ThreadLocalUtils.getObjects();
//        Father employee = (Father) metaObject.getOriginalObject();
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        employee.setCreateUser(threadEmployee.getId());
//        employee.setUpdateUser(threadEmployee.getId());
//        try {
//            Class<?> aClass = metaObject.getOriginalObject().getClass();
//            Object o = aClass.newInstance();
//            Method createTime = aClass.getMethod("setCreateTime", LocalDateTime.class);
//            createTime.invoke(o,LocalDateTime.now());
//            Method updateTime = aClass.getMethod("setUpdateTime", LocalDateTime.class);
//            updateTime.invoke(o,LocalDateTime.now());
//
//
//            Method createUser = aClass.getMethod("setCreateUser", Long.class);
//            createUser.invoke(o,threadEmployee.getId());
//            Method updateUser = aClass.getMethod("setUpdateUser", Long.class);
//            updateUser.invoke(o,threadEmployee.getId());
//            BeanUtils.copyProperties(o,);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());


        if (objects instanceof Employee) {
            Employee threadEmployee = (Employee) ThreadLocalUtils.getObjects();
            metaObject.setValue("updateUser", threadEmployee.getId());
            metaObject.setValue("createUser", threadEmployee.getId());
        } else {
            User user = (User) ThreadLocalUtils.getObjects();
            metaObject.setValue("updateUser", user.getId());
            metaObject.setValue("createUser", user.getId());
        }
    }


    @Override
    public void updateFill(MetaObject metaObject) {
//        Employee threadEmployee = (Employee) ThreadLocalUtils.getObjects();
        Object objects = ThreadLocalUtils.getObjects();
        metaObject.setValue("updateTime", LocalDateTime.now());
        if (objects instanceof Employee) {
            Employee threadEmployee = (Employee) ThreadLocalUtils.getObjects();
            metaObject.setValue("updateUser", threadEmployee.getId());
        } else {
            User user = (User) ThreadLocalUtils.getObjects();
            metaObject.setValue("updateUser", user.getId());
        }

    }
}
