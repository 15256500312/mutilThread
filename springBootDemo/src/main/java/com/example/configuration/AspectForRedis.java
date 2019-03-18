package com.example.configuration;

import com.example.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Aspect
@Component
public class AspectForRedis {
    @Resource(name = "redisTemplate")
    public RedisTemplate<Object,Object> redisTemplate;
    @Pointcut("execution(public * com.example.Service.UserService.queryUsersByPage(..))")
    public void aspectForRedis() {
    }
    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("aspectForRedis()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        String key=pjp.getClass().getName()+pjp.getSignature();
        Object[] args = pjp.getArgs();
        for (Object o:args) {
            key+=o.toString();
        }
        int page=(int)pjp.getArgs()[0];
        int pageSize=(int)pjp.getArgs()[1];
        List<Object> o = (List<Object>)redisTemplate.opsForList().range(key, (pageSize-1)*page, pageSize*page);
        if(o.size()==0){
            List<User> proceed = (List<User>)pjp.proceed();
            for (User user:proceed) {
                redisTemplate.opsForList().rightPush(key, user);
            }
            return proceed;
        }else {
           return o;
        }

    }

}
