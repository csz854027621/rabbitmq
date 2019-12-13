package com.csz.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

public class Send {

    private final static String QUEUE_NAME="simple_queue_csz";

    private final static String FANOUT_EXCHANGE_NAME="fanout_exchange_csz";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection=RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME,"fanout");
        String msg="fanout信息";
        channel.basicPublish(FANOUT_EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("发送："+msg+"成功!");
        channel.close();
        connection.createChannel();
    }

    public void simpleQueue() throws IOException, TimeoutException {
        Connection connection=RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg="你好啊！！";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());


        channel.close();
        connection.close();
    }
}
