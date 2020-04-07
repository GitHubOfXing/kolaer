package com.uxin.baselib.layer.event;

import com.uxin.baselib.layer.event.producer.IPublisher;
import com.uxin.baselib.layer.event.producer.PublisherImpOne;
import com.uxin.baselib.layer.event.resumer.ISubcriber;
import com.uxin.baselib.layer.event.resumer.SubcriberImpOne;
import com.uxin.baselib.layer.event.resumer.SubscribePublish;

public class SubPubTest {
    public static void main(String[] args) {
        SubscribePublish<String> subscribePublish = new SubscribePublish<String>("订阅器");
        IPublisher<String> publisher1 = new PublisherImpOne<String>("发布者1");
        ISubcriber<String> subcriber1 = new SubcriberImpOne<String>("订阅者1");
        ISubcriber<String> subcriber2 = new SubcriberImpOne<String>("订阅者2");
        subcriber1.subcribe(subscribePublish);
        subcriber2.subcribe(subscribePublish);
        publisher1.publish(subscribePublish, "welcome", true);
        publisher1.publish(subscribePublish, "to", true);
        publisher1.publish(subscribePublish, "yy", false);
    }
}