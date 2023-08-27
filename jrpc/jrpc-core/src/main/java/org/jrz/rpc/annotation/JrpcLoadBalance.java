package org.jrz.rpc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 负载均衡注解
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JrpcLoadBalance {

    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * lb策略
     * @return
     */
    String strategy() default "random";
}
