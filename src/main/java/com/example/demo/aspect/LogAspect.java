package com.example.demo.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Date;



@Aspect
@Component
public class LogAspect {
    //@Before("execution(* com.example.demo.controller.*.*(..))")

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.example.demo.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        for(Object arg : joinPoint.getArgs()){
            sb.append("arg:" +arg.toString() + "|");
        }
        logger.info("before method:"+ new Date() + sb.toString());

    }
    @After("execution(* com.example.demo.controller.IndexController.*(..))")
    public void afterMethod(){
        logger.info("after method" + new Date());

    }
}
