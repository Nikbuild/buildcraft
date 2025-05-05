package buildcraft.api.transport.pipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PipeEventHandler {
  PipeEventPriority priority() default PipeEventPriority.NORMAL;
  
  boolean receiveCancelled() default false;
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */