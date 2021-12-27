package mks;

import java.util.Objects;

public class SingletonClass {
    private static SingletonClass singletonClass;
    int value;

    private SingletonClass(int value) {
        this.value = value;
    }

    public static synchronized SingletonClass newSingletonClass(int value) {
        if (singletonClass == null) {
            singletonClass = new SingletonClass(value);
        }
        return singletonClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingletonClass that = (SingletonClass) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "SingletonClass{" +
                "value=" + value +
                '}';
    }
}
