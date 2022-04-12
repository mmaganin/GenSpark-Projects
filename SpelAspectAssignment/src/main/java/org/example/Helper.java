package org.example;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Aspect
@EnableAspectJAutoProxy
public class Helper {
    private Scanner s;
    @Before("execution(* Student.*(..))")
    public void printBeforeAll(){
        System.out.println("This prints before all method calls in Student Class");
    }

    @AfterReturning(pointcut = "execution(public String getLangLearned())", returning = "Java")
    public void printAfterReturning(){
        System.out.println("This prints after running getLangLearned() if the result is 'Java'");
    }


}
