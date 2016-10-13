package graphs;

/**
    Directed graphs.

    A general interface for directed graphs. If you need an
    undirected graph, simply insert directed edges in both
    directions.

    We assume that vertices and edges each carry one uniform
    type of data. If you need, say, a bipartite graph, you'll
    have to use inheritance to model that.

    Instead of our customary Position<T> interfaces we use
    Vertex<V> and Edge<E> interfaces for positions. We can
    therefore overload method names to keep down interface
    complexity and (more importantly) we get some degree of
    static type safety (clients who confuse vertex and edge
    positions will find out at compile-time).

    Positions can be invalid for several reasons: They could
    be null, they could come from a different data structure,
    or they could come from a different, unrelated graph. We
    don't allow self-loops or duplicate edges.

    We don't throw our own specific exceptions anymore: Just
    like many Java classes, we throw IllegalArgumentException
    with a short string describing the problem instead.

    @param <V> Type of vertex element
    @param <E> Type of edge element
*/
public interface Graph<V, E> {
    /**
        Insert new vertex.
        @param v Element to insert.
        @return Vertex position created to hold element.
    */
    Vertex<V> insert(V v);
    /**
        Insert new edge.
        @param from Vertex position where edge starts.
        @param to Vertex position where edge ends.
        @param e Element to insert.
        @return Edge position created to hold element.
        @throws IllegalArgumentException If vertex positions
            are invalid or if this insertion would create a
            duplicate edge.
    */
    Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
        throws IllegalArgumentException;

    /**
        Remove a vertex.
        @param v Vertex position to remove.
        @return Element from destroyed vertex position.
        @throws IllegalArgumentException If vertex position
            is invalid or if vertex still has incident edges.
    */
    V remove(Vertex<V> v)
        throws IllegalArgumentException;
    /**
        Remove an edge.
        @param e Edge position to remove.
        @return Element from destroyed edge position.
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    E remove(Edge<E> e)
        throws IllegalArgumentException;

    /**
        Vertices of graph.
        @return Iterable that can be used to explore the
          vertices of the graph; the remove() method of
          the iterator should not affect the graph.
    */
    Iterable<Vertex<V>> vertices();
    /**
        Edges of graph.
        @return Iterable that can be used to explore the
          edges of the graph; the remove() method of the
          iterator should not affect the graph.
    */
    Iterable<Edge<E>> edges();

    /**
        Outgoing edges of vertex.
        @param v Vertex position to explore.
        @return Iterable that can be used to explore the
          outgoing edges of the given vertex; the remove()
          method of the iterator should not affect the graph.
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    Iterable<Edge<E>> outgoing(Vertex<V> v)
        throws IllegalArgumentException;
    /**
        Incoming edges of vertex.
        @param v Vertex position to explore.
        @return Iterable that can be used to explore the
          incoming edges of the given vertex; the remove()
          method of the iterator should not affect the graph.
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    Iterable<Edge<E>> incoming(Vertex<V> v)
        throws IllegalArgumentException;

    /**
        Start vertex of edge.
        @param e Edge position to explore.
        @return Vertex position edge starts from.
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    Vertex<V> from(Edge<E> e)
        throws IllegalArgumentException;
    /**
        End vertex of edge.
        @param e Edge position to explore.
        @return Vertex position edge leads to.
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    Vertex<V> to(Edge<E> e)
        throws IllegalArgumentException;

    /**
        Label vertex with object.
        @param v Vertex position to label.
        @param l Label object.
        @throws IllegalArgumentException If vertex position
            is invalid or label is null.
    */
    void label(Vertex<V> v, Object l)
        throws IllegalArgumentException;
    /**
        Label edge with object.
        @param e Edge position to label.
        @param l Label object.
        @throws IllegalArgumentException If edge position
            is invalid or label is null.
    */
    void label(Edge<E> e, Object l)
        throws IllegalArgumentException;
    /**
        Vertex label.
        @param v Vertex position to query.
        @return Label object (or null if none).
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    Object label(Vertex<V> v)
        throws IllegalArgumentException;
    /**
        Edge label.
        @param e Edge position to query.
        @return Label object (or null if none).
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    Object label(Edge<E> e)
        throws IllegalArgumentException;
    /**
        Clear all labels.
    */
    void clearLabels();
}
