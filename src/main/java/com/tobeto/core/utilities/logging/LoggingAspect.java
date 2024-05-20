package com.tobeto.core.utilities.logging;

import java.time.LocalDateTime;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.SecurityUtils;
import com.tobeto.entities.abstracts.BaseEntity;
import com.tobeto.entities.concretes.User;

@Service
@Aspect
public class LoggingAspect {

	@Autowired
	private LoggingService loggingService;

	@Before("execution(* com.tobeto.business.concretes.*.create(..))")
	public void logBeforeCreate(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args.length > 0 && args[0] instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) args[0];
			String entityName = entity.getClass().getSimpleName();
			String username = SecurityUtils.getCurrentUsername();
			loggingService.logCreate(entity.getId(), entityName, username, LocalDateTime.now());
		}
	}

	@AfterReturning(pointcut = "execution(* com.tobeto.business.concretes.*.update(..))", returning = "result")
	public void logAfterUpdate(JoinPoint joinPoint, Object result) {
		boolean success = result != null;
		if (result instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) result;
			String entityName = entity.getClass().getSimpleName();
			String username = SecurityUtils.getCurrentUsername();
			LocalDateTime updateDate = LocalDateTime.now();
			Object[] args = joinPoint.getArgs();
			String updateDetails = "Updated with params: " + getSafeArgs(args);
			loggingService.logUpdate(entity.getId(), entityName, username, updateDate, updateDetails, success);
		} else {
			loggingService.logUpdate(null, "Unknown Entity", "Unknown User", LocalDateTime.now(), "Update failed",
					success);
		}
	}

	private String getSafeArgs(Object[] args) {
		StringBuilder safeArgs = new StringBuilder();
		for (Object arg : args) {
			if (arg instanceof User) {
				User user = (User) arg;
				safeArgs.append("User(id=").append(user.getId()).append(", email=").append(user.getEmail()).append(")");
			} else {
				safeArgs.append(arg.toString());
			}
		}
		return safeArgs.toString();
	}

	@Before("execution(* com.tobeto.business.concretes.*.delete(..))")
	public void logBeforeDelete(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Object[] args = joinPoint.getArgs();
		if (args.length > 0 && args[0] instanceof UUID) {
			UUID entityId = (UUID) args[0];
			String entityName = signature.getDeclaringType().getSimpleName().replace("Manager", "");
			String username = SecurityUtils.getCurrentUsername();
			loggingService.logSoftDelete(entityId, entityName, username);
		}
	}
}
