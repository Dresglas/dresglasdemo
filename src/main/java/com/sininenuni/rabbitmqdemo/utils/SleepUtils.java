package com.sininenuni.rabbitmqdemo.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.DeliverCallback;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class SleepUtils {
    public static void sleep(int second) {
        try {
            Thread.sleep( 1000 * second );
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}