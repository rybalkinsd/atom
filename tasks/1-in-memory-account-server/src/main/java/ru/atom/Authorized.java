package ru.atom;

import javax.ws.rs.NameBinding;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ilysk on 27.03.17.
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Authorized {
}