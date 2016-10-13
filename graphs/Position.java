package graphs;

/**
    Generic position.

    An abstract position that serves the same role as int
    for positions in an array.

    All position-oriented data structures can utilize this
    interface because we make absolutely no assumptions as
    to how multiple positions interrelate.

    Value-oriented data structures should <b>not</b> use
    positions in their interfaces, especially since we support
    a put() operation!

    @param <T> Type of each element in a position.
*/
public interface Position<T> {
    /**
        Read element.

        @return Element at this position.
    */
    T get();
    /**
        Write element.

        @param t Element to store at this position.
    */
    void put(T t);
}
