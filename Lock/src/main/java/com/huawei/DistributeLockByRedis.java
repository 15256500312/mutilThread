package com.huawei;

import java.awt.*;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

//redis实现分布式锁
public class DistributeLockByRedis {
    public static void main(String[] args ){
    for(int i=0;i<3;i++){
        retry:
        for (int j=0;j<5;j++){
                if(j==3)break retry;
            System.out.println(j);
        }
    }
    }
}
