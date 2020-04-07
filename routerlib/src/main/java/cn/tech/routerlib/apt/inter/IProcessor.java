package cn.tech.routerlib.apt.inter;


import javax.annotation.processing.RoundEnvironment;

import cn.tech.routerlib.apt.AnnotationProcessor;


/**
 * Created by baixiaokang on 16/10/8.
 * 注解处理器接口
 */

public interface IProcessor {
    void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor);
}