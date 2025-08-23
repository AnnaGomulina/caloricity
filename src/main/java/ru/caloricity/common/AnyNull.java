package ru.caloricity.common;

public class AnyNull {
    Object[] objects;

    public AnyNull(Object... objects) {
        this.objects = objects;
    }

    public boolean is() {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }
}
