package com.irving.utlis;

import com.irving.domain.Employee;
import org.springframework.stereotype.Component;

@Component
public class  ThreadLocalUtils {
    private static ThreadLocal threadLocal=new ThreadLocal();


    public static void SetObject(Object o){
        threadLocal.set(o);
    }

    public static Object getObjects(){
        return threadLocal.get();
    }
}
