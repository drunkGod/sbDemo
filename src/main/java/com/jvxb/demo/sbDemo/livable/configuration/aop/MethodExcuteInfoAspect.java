package com.jvxb.demo.sbDemo.livable.configuration.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jvxb.demo.sbDemo.livable.utils.DateUtil;

/**
 * 记录ResponseBody接口对应方法执行情况-切面。 包括： 1.用时超过3s的方法，需要分析原因想办法优化
 * 2.执行的方法抛出异常，需要记录到专门的error日志
 * 
 * @author 抓娃小兵
 */
@Component
@Aspect
public class MethodExcuteInfoAspect {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 切点位置：使用 ResponseBody和 RestController的方法
	 */
	@Pointcut("@annotation(org.springframework.web.bind.annotation.ResponseBody) || @annotation(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.web.bind.annotation.RestController)")
	public void methodExecuteAspect() {
	}

	@Around("methodExecuteAspect()")
	public Object timeSpentOfMethod(ProceedingJoinPoint pjp) throws Throwable {

		// 切点方法执行前的时间
		long start = System.currentTimeMillis();
		// 执行方法
		Object retVal = pjp.proceed();
		// 切点方法执行所花的时间
		long duration = System.currentTimeMillis() - start;
		// 记录
		String operateTime = DateUtil.getTime();
		String operateClassName = pjp.getTarget().getClass().getName();
		String operateMethodName = pjp.getSignature().getName();
		String operateArgs = "";
		Object[] argsArr = pjp.getArgs();
		for (Object arg : argsArr) {
			if (arg == null) {
				continue;
			}
			operateArgs += arg.toString() + ",";
		}
		// 警告时间：方法执行超过3000毫秒
		long warningThreshold = 3000;
		if (duration > warningThreshold) {
			// 记录到专门的日志, 以供优化
			logger.warn(String.format("[%s] %s#%s" + "() 方法执行时间为  %d(ms), 超过 %d ms, 需优化。 args is [%s]",
					operateClassName, operateMethodName, operateTime, duration, warningThreshold, operateArgs));
		}
		return retVal;
	}

	/**
	 * 方法执行出现未捕获的异常，记录到error日志。好像会自动记录Servlet.service() for servlet [dispatcherServlet]。额。。
	 */
	@AfterThrowing(throwing = "ex", pointcut = "methodExecuteAspect()")
	public void exceptionOfMethod(Throwable ex) throws Throwable {
		String errTime = DateUtil.getTime();
		StackTraceElement stackTraceElement = ex.getStackTrace()[0];
		String errClassName = stackTraceElement.getClassName();
		String errMethodName = stackTraceElement.getMethodName();
		int errLine = stackTraceElement.getLineNumber();
		String errMsg = ex.getMessage();
		// 记录到专门的error日志, 以供查找原因
		logger.error(String.format("[%s] %s#%s" + "() 方法执行异常。line is [%d], errMsg is [%s]", errTime, errClassName,
				errMethodName, errLine, errMsg));
	}

}
