package com.efnilite.connotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command annotation for methods.
 *
 * @author Efnilite
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String[] aliases() default "";

    String description() default "";

    String permission() default "You don't have enough permissions to do that!";

    String permissionMessage() default "";

    String usage() default "";

}
