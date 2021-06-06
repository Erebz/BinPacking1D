package core;

public interface Transition {
    public abstract Transition getInverse();

    @Override
    public abstract boolean equals(Object o);
}
