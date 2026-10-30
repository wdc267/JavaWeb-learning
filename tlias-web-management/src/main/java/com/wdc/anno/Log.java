package com.wdc.anno;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
}
