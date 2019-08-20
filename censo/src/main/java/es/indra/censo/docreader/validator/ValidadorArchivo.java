package es.indra.censo.docreader.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValidadorClassArchivo.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidadorArchivo {
	
	String message() default "El contenido del archivo no puede estar vacio";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}
