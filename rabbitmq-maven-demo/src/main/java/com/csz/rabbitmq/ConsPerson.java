package com.csz.rabbitmq;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class ConsPerson {

    private final static String QUEUE_NAME="simple_queue_csz";
    private final static String FANOUT_QUEUE_NAME="fanout_queue_csz";
    private final static String FANOUT_EXCHANGE_NAME="fanout_exchange_csz";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection=RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(FANOUT_QUEUE_NAME,false,false,false,null);
        channel.queueBind(FANOUT_QUEUE_NAME,FANOUT_EXCHANGE_NAME,"");


        DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body);
                System.out.println(msg);

            }
        };
        channel.basicConsume(FANOUT_QUEUE_NAME,true,defaultConsumer);


    }

    public void simple() throws IOException, TimeoutException {
        Connection connection=RabbitMQUtils.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,
                false,false,null);
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String msg=new String(body);
                System.out.println(msg);
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);

    }
}
