package org.kuspakov.springaop.aop;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kuspakov.springaop.entity.Book;
import org.kuspakov.springaop.util.CustomResponse;
import org.kuspakov.springaop.util.CustomStatus;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class MyAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    MethodSignature methodSignature;

    private MyAspect myAspect;

    @BeforeEach
    public void init() throws Throwable {
        MockitoAnnotations.openMocks(this);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Title");
        book.setAuthor("Author");
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(joinPoint.getArgs()).thenReturn(Arrays.array(book));
        when(joinPoint.proceed()).thenReturn(book);
        myAspect = new MyAspect();
    }

    @Test
    void aroundAddingAdvice() throws Throwable {
        when(methodSignature.getMethod()).thenReturn(getMethodAdd());
        assertEquals("Title", ((Book) myAspect.aroundAddingAdvice(joinPoint)).getTitle());
        when(joinPoint.proceed()).thenThrow(NoSuchElementException.class);
        assertEquals(CustomStatus.EXCEPTION.getCode(), ((CustomResponse<?>) myAspect.aroundAddingAdvice(joinPoint)).getCode());
    }

    @Test
    void aroundGetAdvice() throws Throwable {
        when(methodSignature.getMethod()).thenReturn(getMethodAll());
        assertEquals("Title", ((Book) myAspect.aroundGetAdvice(joinPoint)).getTitle());
        when(joinPoint.proceed()).thenThrow(NoSuchElementException.class);
        assertEquals(CustomStatus.NOT_FOUND.getCode(), ((CustomResponse<?>) myAspect.aroundGetAdvice(joinPoint)).getCode());
    }

    @Test
    void logAfterThrow() throws Throwable {
        Logger fooLogger = (Logger) LoggerFactory.getLogger(MyAspect.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        fooLogger.addAppender(listAppender);
        when(methodSignature.getMethod()).thenReturn(getMethodAdd());
        when(joinPoint.proceed()).thenThrow(NoSuchElementException.class);
        myAspect.aroundGetAdvice(joinPoint);
        List<ILoggingEvent> logsList = listAppender.list;
        assertNull(logsList.get(0)
                .getMessage());
        assertEquals(Level.ERROR, logsList.get(0)
                .getLevel());
    }

    public Method getMethodAdd() throws NoSuchMethodException {
        return getClass().getDeclaredMethod("addBook");
    }

    public Method getMethodAll() throws NoSuchMethodException {
        return getClass().getDeclaredMethod("getAll");
    }

    public void addBook() {

    }

    public void getAll() {

    }
}