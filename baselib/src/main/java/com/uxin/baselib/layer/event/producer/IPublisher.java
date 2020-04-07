package com.uxin.baselib.layer.event.producer;

import com.uxin.baselib.layer.event.resumer.SubscribePublish;

public interface IPublisher<M> {
    public void publish(SubscribePublish subscribePublish, M message, boolean isInstantMsg);
}