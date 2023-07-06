package com.irving.exception;

import com.irving.commonly.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class AddException {
    @ResponseBody
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public R<String> addException(SQLIntegrityConstraintViolationException e){
        String message = e.getMessage();
        log.info(message);

        if (message.contains("Duplicate entry")){
            String[] strings = message.split(" ");
            return R.error("用户名"+strings[2]+"不能重复");
        }
        return R.error("其它异常");
    }
}
