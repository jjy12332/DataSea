package com.app.aop;


import com.app.Utils.TokenUtil;
import com.app.bean.DoResult;
import com.app.bean.UserToken;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 切面
 * 用于拦截jwt，进行校验，与用户验证
 */

//设置当前类为切面类类
@Aspect
@Configuration
public class Interceptor {


//    @Autowired
//    Jedis jedis;// = new Jedis("127.0.0.1", 6379);

    @Autowired
    JedisPool jedisPool;


    /**
     * //Pointcut表示式  @Before("brokerAspect()") 即可
     * 这种使用方式等同于以下方式，直接定义execution表达式使用 @Before("execution(* com.savage.aop.MessageSender.*(..))")
     */
    /**
     * @Pointcut 注解指定一个切面，定义需要拦截的东西，这里介绍两个常用的表达式：一个是使用 execution()，另一个是使用 annotation()。
     * 以 execution(* com.bowen.controller..*.*(..)) 表达式为例，语法如下：
     *
     * execution() 为表达式主体
     * 第一个 * 号的位置：表示返回值类型， * 表示所有类型
     * 包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包， com.bowen.controller 包、子包下所有类的方法
     * 第二个 * 号的位置：表示类名， * 表示所有类 *(..) ：这个星号表示方法名， * 表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数
     * annotation() 方式是针对某个注解来定义切面，比如我们对具有@GetMapping注解的方法做切面，可以如下定义切面：
     *
     */
    @Pointcut(value="execution(public * com.app.controller..*.*(..)) && !execution(* com.app.controller..*.AddUser(..))")
    public void brokerAspect(){//Point签名

    }


    /**
     *     //ProceedingJoinPoint pjp
     *     //JoinPoint joinPoint
     * 要知道为什么之前，首先要知道JoinPoint 和ProceedingJoinPoint 是什么。
     *
     * JoinPoint
     * AOP中的织入点（切入点），可以通过这个拿到目标方法的参数、返回值、签名等
     * ProceedingJoinPoint
     * ProceedingJoinPoint是继承JoinPoint的，他主要是做环绕通知的。例如前置、执行、后置通知
     * 那为什么不能用@AfterReturning中使用ProceedingJoinPoint呢？
     *
     * 其实很简单，就是因为JoinPoint接口实现类的引用无法转为ProceedingJoinPoint。
     */
//    @Before("brokerAspect()")
//    public void before(JoinPoint joinPoint) throws Throwable {
//        System.out.println("这是前置通知");
//        System.out.println("正在执行的目标类是: " + joinPoint.getTarget());
//        System.out.println("正在执行的目标方法是: " + joinPoint.getSignature().getName());
//    }

//    @After("brokerAspect()")
//    public void after(JoinPoint joinPoint){
//        System.out.println("这是后置通知");
//        System.out.println("正在执行的目标类是: " + joinPoint.getTarget());
//        System.out.println("正在执行的目标方法是: " + joinPoint.getSignature().getName());
//    }


    @Around("brokerAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {


        System.out.println("这是环绕通知");
        System.out.println("正在执行的目标类是: " + joinPoint.getTarget());
        System.out.println("正在执行的目标方法是: " + joinPoint.getSignature().getName());

        //获取请求的请求头，其包含token
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String jwt = request.getHeader("jwt");

        //如果token为空，则返回错误信息，不允许进入控制层
        if(StringUtils.isBlank(jwt)){
            return DoResult.error(999,"token有问题，请获取token","");
        }

        //解析token
        UserToken infoFromToken = TokenUtil.getInfoFromToken(jwt);

        //检查随机token在redis中是否存在，

        Jedis jedis = jedisPool.getResource();
        if (!jedis.exists(infoFromToken.getUuidToken())) {
            //不存在让用户重新登录
            return DoResult.success(500, "该用户以超时，请重新登录", "");
        }

        //将解析出来的数据放入请求头中，为后续方法提供服务
        RequestContextHolder.getRequestAttributes().setAttribute("userId", infoFromToken, 0);

        //执行方法
        Object proceed = joinPoint.proceed();
        return proceed;
    }



}
