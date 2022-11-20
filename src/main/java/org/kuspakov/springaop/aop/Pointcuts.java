package org.kuspakov.springaop.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* org.kuspakov.springaop.service.BookService.get*(..))")
    public void allGetMethods(){

    }

    @Pointcut("execution(* org.kuspakov.springaop.service.BookService.add*(..))")
    public void allAddMethods(){

    }
}
