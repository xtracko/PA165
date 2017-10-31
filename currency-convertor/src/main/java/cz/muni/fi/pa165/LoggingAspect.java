package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(public * *(..))")
    public Object after(ProceedingJoinPoint joinPoint) throws Throwable {
        long beg = System.nanoTime();
        Object result = joinPoint.proceed();
        long end = System.nanoTime();

        System.out.println("Method " + joinPoint.getSignature().getName() + " took " + (end - beg) + " ns");

        return result;
    }

}
