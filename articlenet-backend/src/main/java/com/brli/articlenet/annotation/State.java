package com.brli.articlenet.annotation;

import com.brli.articlenet.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



// customised validation for the state of article: published or draft
@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {StateValidation.class})
public @interface State {
    // message when validation fails
    String message() default "state parameter should either be published or draft";

    // grouping
    Class<?>[] groups() default { };

    // get other info from annotation
    Class<? extends Payload>[] payload() default { };
}
