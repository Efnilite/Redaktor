package com.efnilite.redaktor.command.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String[] aliases() default "";

    String description() default "";

    String permission() default "";

    String permissionMessage() default "";

    String usage() default "";

}
