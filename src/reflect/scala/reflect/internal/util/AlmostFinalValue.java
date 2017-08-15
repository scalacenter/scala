package scala.reflect.internal.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.SwitchPoint;

public class AlmostFinalValue<V> {
  private final AlmostFinalCallSite<V> callsite =
      new AlmostFinalCallSite<>(this);
  
  protected V initialValue() {
    return null;
  }
  
  public MethodHandle createGetter() {
    return callsite.dynamicInvoker();
  }
  
  public void setValue(V value) {
    callsite.setValue(value);
  }
  
  private static class AlmostFinalCallSite<V> extends MutableCallSite {
    private Object value;
    private SwitchPoint switchPoint;
    private final AlmostFinalValue<V> volatileFinalValue;
    private final MethodHandle fallback;
    private final Object lock;
    
    private static final Object NONE = new Object();
    private static final MethodHandle FALLBACK;
    static {
      try {
        FALLBACK = MethodHandles.lookup().findVirtual(AlmostFinalCallSite.class, "fallback",
            MethodType.methodType(Object.class));
      } catch (NoSuchMethodException|IllegalAccessException e) {
        throw new AssertionError(e.getMessage(), e);
      }
    }
    
    AlmostFinalCallSite(AlmostFinalValue<V> volatileFinalValue) {
      super(MethodType.methodType(Object.class));
      Object lock = new Object();
      MethodHandle fallback = FALLBACK.bindTo(this);
      synchronized(lock) {
        value = NONE;
        switchPoint = new SwitchPoint();
        setTarget(fallback);
      }
      this.volatileFinalValue = volatileFinalValue;
      this.lock = lock;
      this.fallback = fallback;
    }

    Object fallback() {
      synchronized(lock) {
        Object value = this.value;
        if (value == NONE) {
          value = volatileFinalValue.initialValue();
        }
        MethodHandle target = switchPoint.guardWithTest(MethodHandles.constant(Object.class, value), fallback);
        setTarget(target);
        return value;
      }
    }
    
    void setValue(V value) {
      synchronized(lock) {
        SwitchPoint switchPoint = this.switchPoint;
        this.value = value;
        this.switchPoint = new SwitchPoint();
        SwitchPoint.invalidateAll(new SwitchPoint[] {switchPoint});
      }
    }
  }
}