package microservices.hr.infrastructure.util;

final public class Safe {
    public static <T> T safe(SafeClosure<T> closure) {
        try {
            return closure.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void safeVoid(SafeVoidClosure closure) {
        try {
            closure.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface SafeVoidClosure {
        void call() throws Exception;
    }

    public interface SafeClosure<T> {
        T call() throws Exception;
    }
}
