package scala.reflect.internal.util;

import scala.reflect.internal.util.AlmostFinalValue;
import java.lang.invoke.MethodHandle;

/**
 * Represents a container with a boolean value that tells the compiler whether
 * an option is enabled or not. This class is used for configuration purposes
 * (see scala.reflect.internal.util.Statistics).
 * 
 * Its implementation delegates to {@link scala.reflect.internal.util.AlmostFinalValue},
 * which helps performance (see docs to find out why).
 */
public class StaticStatistics {
  private final boolean value;

  public StaticStatistics(boolean value) {
    this.value = value;
  }
  
  public boolean isEnabledNow() {
    return value;
  }

  protected final static class EnabledStatistics extends StaticStatistics {
    EnabledStatistics() {
        super(true);
    }
  }

  protected final static class DisabledStatistics extends StaticStatistics {
    DisabledStatistics() {
        super(false);
    }
  }
  
  private static final AlmostFinalValue<StaticStatistics> DEFAULT_VALUE = new AlmostFinalValue<StaticStatistics>() {
    @Override
    protected StaticStatistics initialValue() {
        return new DisabledStatistics();
    }
  };

  private static final MethodHandle DEFAULT_VALUE_GETTER = DEFAULT_VALUE.createGetter();
  
  public static boolean isEnabled() {
    try {
      return ((StaticStatistics)(Object)DEFAULT_VALUE_GETTER.invokeExact()).isEnabledNow();
    } catch (Throwable e) {
      throw new AssertionError(e.getMessage(), e);
    }
  }
  
  public static void enable() {
    DEFAULT_VALUE.setValue(new EnabledStatistics());
  }

  public static void disable() {
    DEFAULT_VALUE.setValue(new DisabledStatistics());
  }
}