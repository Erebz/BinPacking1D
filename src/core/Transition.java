package core;

public interface Transition {
    public Transition getInverse();

    @Override
    public boolean equals(Object o);
}
