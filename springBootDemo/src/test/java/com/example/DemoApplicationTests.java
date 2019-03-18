package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
@Autowired
	DataSource dataSource;
    @Autowired
	RedisTemplate<Object,Object> redisTemplate;
	@Test
	public void contextLoads() {

	}
public class MyRedisLock implements Lock{
public volatile Thread curThread;
public int state;
	@Override
	public void lock() {
		if(null==curThread){
			String lockKey="kkk";
			String LOCKVALUE="vvv";
			/*该方法会在没有key时，设置key;存在key时返回false；因此可以通过该方法及设置key的有效期，判断是否有其它线程持有锁*/
			Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey,LOCKVALUE);
			if(success != null && success){
				redisTemplate.expire(lockKey,3,TimeUnit.SECONDS);
				curThread=Thread.currentThread();
			}
		}else {
			//有线程占有锁
			if(curThread==Thread.currentThread()){
				state++;
			}
		}



	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {

		return false;
	}


	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {


	}

	@Override
	public Condition newCondition() {
		return null;
	}
}
}
