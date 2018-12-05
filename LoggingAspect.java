package br.com.lrm.logdemo.config;

import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


public class LoggingAspect {

    //setup logger

    private Logger myLogger=Logger.getLogger(getClass().getName());

    // setup pointcut declaration
    @Pointcut("execution(* br.com.lrm.logdemo.controller.*.*(..))")
    private void forControllerPackage() {

    }
    // do the same for service  and dao 	
    @Pointcut("execution(* br.com.lrm.logdemo.model.*.*(..))")
    private void forModelPackage() {

    }  
  
    @Pointcut("forControllerPackage() || forModelPackage()")
    private void forAppFlow() {}

    //add @Before advice  
    @Before("forAppFlow() && args(..,request)")
    public void before(JoinPoint theJointPoint)
    {
        //diaplay method we are calling
        String theMethod = theJointPoint.getSignature().toShortString();
        myLogger.info("====> in @Before: calling method " + theMethod);

        //display the arguments to the method


        //GET THE ARGUMENTS
        Object[] args=theJointPoint.getArgs();

        //loop through and display args
        for(Object tempArg : args)
        {
            myLogger.info("===> arguments "+ tempArg.toString());
        }
    }

    // add @AfterReturing Advice

    @AfterReturning(pointcut="forAppFlow()",  
            returning="theResult")
    public void afterReturing(JoinPoint theJointPoint, Object theResult) {

        //display method we RE RETURING FROM
        String theMethod = theJointPoint.getSignature().toShortString();
        myLogger.info("====> in @AfterReturing: from return method " + theMethod);
        
        //DISPLAY DATA RECIEVED

        myLogger.info("===> result : "+theResult);
    }   

}