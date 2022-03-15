package org.instancio.testsupport.templates;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for tests using the {@link CreationTestTemplate}.
 * <p>
 * Specifies the number of times to execute a given test.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberOfExecutions {

    int DEFAULT_VALUE = 10;

    /**
     * Number of times a test will be executed.
     * This is how many times an object will be created.
     */
    int value() default DEFAULT_VALUE;
}