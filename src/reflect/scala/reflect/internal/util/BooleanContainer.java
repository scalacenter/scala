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
public class BooleanContainer {
  private final boolean value;

  public BooleanContainer(boolean value) {
    this.value = value;
  }
  
  public boolean isEnabled() {
    return value;
  }

  protected final static class TrueBooleanContainer extends BooleanContainer {
    TrueBooleanContainer() {
        super(true);
    }
  }

  protected final static class FalseBooleanContainer extends BooleanContainer {
    FalseBooleanContainer() {
        super(false);
    }
  }
  
  private static final AlmostFinalValue<BooleanContainer> DEFAULT_VALUE = new AlmostFinalValue<BooleanContainer>() {
    @Override
    protected BooleanContainer initialValue() {
        return new FalseBooleanContainer();
    }
  };

  private static final MethodHandle DEFAULT_VALUE_GETTER = DEFAULT_VALUE.createGetter();
  
  public static BooleanContainer getDefault() {
    try {
      return (BooleanContainer)(Object)DEFAULT_VALUE_GETTER.invokeExact();
    } catch (Throwable e) {
      throw new AssertionError(e.getMessage(), e);
    }
  }
  
  public static void enable() {
    DEFAULT_VALUE.setValue(new TrueBooleanContainer());
  }

  public static void disable() {
    DEFAULT_VALUE.setValue(new FalseBooleanContainer());
  }
}