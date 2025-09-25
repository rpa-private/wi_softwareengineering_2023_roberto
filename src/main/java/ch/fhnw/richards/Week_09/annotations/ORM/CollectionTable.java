package ch.fhnw.richards.Week_09.annotations.ORM;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // Applies to attributes
@Retention(RetentionPolicy.RUNTIME) // Annotation will be processed at runtime
public @interface CollectionTable {
    String name() default "";
    String joinColumn() default"";
}
