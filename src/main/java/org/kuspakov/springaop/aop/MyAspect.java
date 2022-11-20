package org.kuspakov.springaop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.kuspakov.springaop.entity.Book;
import org.kuspakov.springaop.util.CustomResponse;
import org.kuspakov.springaop.util.CustomStatus;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Aspect
@Slf4j
public class MyAspect {

    @Around("Pointcuts.allAddMethods()")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint) {
        Book book = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if ("addBook".equals(methodSignature.getMethod().getName())) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof Book) {
                    book = (Book) arg;
                    log.info("Try to add new Book with title - {}", book.getTitle());
                }
            }
        }

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            result = new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }
        assert book != null;
        log.info("Book add with title - {}", book.getTitle());
        return result;
    }

    @Around("Pointcuts.allGetMethods()")
    public Object aroundGetAdvice(ProceedingJoinPoint joinPoint) {
        String title;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if ("getAll".equals(methodSignature.getMethod().getName())) {
            log.info("Try to get all Books");
        } else if ("getBookByTitle".equals(methodSignature.getMethod().getName())) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof String) {
                    title = (String) arg;
                    log.info("Try to get Book with title - {}", title);
                }
            }
        }

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage(), e);
            return new CustomResponse<>(null, CustomStatus.NOT_FOUND);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            result = new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }

        if ("getAll".equals(methodSignature.getMethod().getName())) {
            log.info("All books get");
        } else if ("getBookByTitle".equals(methodSignature.getMethod().getName())) {
            log.info("Book get");
        }
        return result;
    }
}
