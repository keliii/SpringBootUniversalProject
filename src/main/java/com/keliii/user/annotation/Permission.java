package com.keliii.user.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by keliii on 2017/6/21.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    String url() default "";

    boolean isPublic() default false;

}
