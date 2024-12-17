package com.pghub.user.utility;

import com.pghub.user.exception.PgHubException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


import lombok.extern.log4j.Log4j2;

@Component
@Aspect
@Log4j2
public class LoggingAspect {
	@AfterThrowing(pointcut="execution(* com.pghub.user.services.*Impl.*(..))", throwing="exception")
	public void logServiceExceptions(PgHubException exception) {
		log.error(exception.getMessage(), exception);
	}
}
