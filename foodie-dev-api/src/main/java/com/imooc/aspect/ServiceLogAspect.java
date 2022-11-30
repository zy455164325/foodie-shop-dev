package com.imooc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName ServiceLogAspect
 * @Description service类方法执行时间日志监控
 * @Author braveZeng
 * @Date 10:31 2022/11/30
 * Version 1.0
 **/
@Aspect
@Component
public class ServiceLogAspect {
    final static Logger logger= LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * AOP通知：
     * 1. 前置通知：在方法调用前执行
     * 2. 后置通知：在方法正常调用之后执行(若遇异常则不会执行)
     * 3. 环绕通知： 在方法调用前后，都分别可以执行的通知
     * 4. 异常通知： 如果方法调用过程中发生异常，则通知
     * 5. 最终通知： 在方法调用之后通知（类似于try中的finally）
     *
     * 切面表达式：
     * execution  代表所要执行的表达式主体
     * 第一处 * 代表方法返回类型 * 代表所有类型
     * 第二处 包名代表aop监控的类所在的包
     * 第三处 .. 代表该包以及其子包下所有类的方法
     * 第四处 * 代表类名，* 代表所有类
     * 第五处 *(..) *代表类中的方法名 （..）表示方法中的任何参数
     */
    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("==========开始执行{}.{} ================",
                                joinPoint.getTarget().getClass(),
                                joinPoint.getSignature().getName());
        // 记录开始时间
        long begin= System.currentTimeMillis();
        // 执行目标 service
        Object result=joinPoint.proceed();
        // 记录结束时间
        long end= System.currentTimeMillis();
        long takeTime=end - begin;
        if(takeTime>3000){
            logger.error("========执行结束，耗时：{} 毫秒 =======",takeTime);
        }else if(takeTime>2000){
            logger.warn("========执行结束，耗时：{} 毫秒 =======",takeTime);
        }else{
            logger.info("========执行结束，耗时：{} 毫秒 =======",takeTime);
        }
        return result;

    }
}
