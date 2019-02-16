package com.huawei;

import com.huawei.domain.Resource;

public class ProducerAndConsumer1 {
    public static void main(String[] args) {
        Resource resource=new Resource();
        resource.setFlag(true);
        Thread producer = new Thread(() -> {
            int count=0;
            synchronized (resource){
                for (;;){
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(resource.getFlag()){
                        if(count%2==0){
                            resource.setName("zhangsan");
                            resource.setAge(1111);
                        }else {
                            resource.setName("lisi");
                            resource.setAge(222);
                        }
                    }
                   try {
                       count++;
                       resource.setFlag(false);
                       resource.notifyAll();
                       resource.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
        }, "producer");
        Thread consumer = new Thread(() -> {
            synchronized (resource){
                for (;;){
                    if(!resource.getFlag())
                    System.out.println(resource);
                    resource.setFlag(true);
                    resource.notifyAll();
                    try {
                        resource.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, "consumer");

        producer.start();
        consumer.start();

    }
}
