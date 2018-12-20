package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class LogAdvice {
	@Before("execution(* org.zerock.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("=============================================!");
	}
	
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String,"
			+ "String)) && args(str1,str2)")
	public void logBeforeWithP(String str1 ,String str2) {
		log.info("str1 : "+str1);
		log.info("str2 : "+str2);
		
	}
	@AfterThrowing(pointcut = "execution(* org.zerock.service."
			+ "SampleService*.*(..))",throwing="e")
	public void logException(Exception e) {
		log.info("예외 발생.....!!!!!");
		log.info("예외 내용 : "+e);
	}
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		
		log.info("Target(타겟) : "+pjp.getTarget());
		log.info("Param(파라미터) : "+Arrays.toString(pjp.getArgs()));
		
		//invoke method
		Object result = null;
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("TIME :"+(end-start));
		
		return result;
	}
}
