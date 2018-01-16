package com.senpure.base.annotation;

import java.lang.annotation.*;

/**
 * Created by 罗中正 on 2018/1/15 0015.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExtPermission {

    String uri() default "";
    String method() default "";
    /**
     * 可读的名字
     *
     * @return
     */
    String value() default "";
    /**
     * 唯一标识
     *
     * @return
     */
    String name() default "";

}
