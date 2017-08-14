package scala.reflect.internal.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

public class StatisticsHelper extends MutableCallSite {
    protected static final MethodType BOOLEAN_METHOD_TYPE = MethodType.methodType(Boolean.class).unwrap();
    protected static final MethodHandle DEFAULT_METHOD_HANDLE;
    static {
        try {
            DEFAULT_METHOD_HANDLE = MethodHandles.lookup().findVirtual(
                Class.forName("scala.reflect.internal.util.Statistics$"), "defaultValue", BOOLEAN_METHOD_TYPE);
        } catch (NoSuchMethodException|IllegalAccessException|ClassNotFoundException e) {
            throw new AssertionError("Abort: method `defaultValue` was not found in Statistics: " + e.getMessage());
        }
    }

    StatisticsHelper() {
        super(BOOLEAN_METHOD_TYPE);
    }
}