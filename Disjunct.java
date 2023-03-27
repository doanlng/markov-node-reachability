
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Repeatable;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Disjunct{
    public String fact() default "" ;
    public String[] args() default {};
}
