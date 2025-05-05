package buildcraft.transport.pipes.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PipeEventPriority {
  int priority() default 0;
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\events\PipeEventPriority.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */