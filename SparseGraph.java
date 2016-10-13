/**
 * @author: Vincent Yan
 * @email: vyan1@jhu.edu
*/


import java.util.ArrayList;
import graphs.*;

/**
    A sparse graph implementation of the Graph<V, E> interface.

    @param <V> Type of vertex element
    @param <E> Type of edge element
*/
public class SparseGraph<V, E> implements Graph<V, E> {
    private ArrayList<Vertex<V>> allVertices = new ArrayList<Vertex<V>>();
    private ArrayList<Edge<E>> allEdges = new ArrayList<Edge<E>>();

    private class VertexNode<V> implements Vertex<V> {
        public ArrayList<Edge<E>> incomingEdges = new ArrayList<Edge<E>>();
        public ArrayList<Edge<E>> outgoingEdges = new ArrayList<Edge<E>>();
        public V value;
        public Graph<V, E> manufacturer;
        public Object labelV;

        public V get() {
            return this.value;
        }

        public void put(V v) {
            this.value = v;
        }
    }

    private class EdgeNode<E> implements Edge<E> {
        public VertexNode<V> fromV;
        public VertexNode<V> toV;
        public E value;
        public Graph<V, E> manufacturer;
        public Object labelE;

        public E get() {
            return this.value;
        }

        public void put(E e) {
            this.value = e;
        }
    }

    private VertexNode<V> validate(Vertex<V> v) {
        if (v == null || !(v instanceof SparseGraph.VertexNode)) {
            throw new IllegalArgumentException("Invalid vertex position");
        }
        VertexNode<V> n = (VertexNode) v;
        if (n.manufacturer != this) {
            throw new IllegalArgumentException("Invalid vertex position");
        }
        return n;
    }

    private EdgeNode<E> validate(Edge<E> e) {
        if (e == null || !(e instanceof SparseGraph.EdgeNode)) {
            throw new IllegalArgumentException("Invalid edge position");
        }
        EdgeNode<E> n = (EdgeNode<E>) e;
        if (n.manufacturer != this) {
            throw new IllegalArgumentException("Invalid edge position");
        }
        return n;
    }

    /**
        Insert new vertex.
        @param v Element to insert.
        @return Vertex position created to hold element.
    */
    public Vertex<V> insert(V v) {
        VertexNode<V> newV = new VertexNode<V>();
        newV.value = v;
        newV.manufacturer = this;
        this.allVertices.add(newV);
        return newV;
    }

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

    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
        throws IllegalArgumentException {
        VertexNode<V> fromNode = this.validate(from);
        VertexNode<V> toNode = this.validate(to);
        if (fromNode.equals(toNode)) {
            //can't have self loop
            throw new IllegalArgumentException("Invalid vertex position");
        }
        for (int i = 0; i < fromNode.outgoingEdges.size(); i++) {
            //check if duplicate edge
            if (((EdgeNode) fromNode.outgoingEdges.get(i)).toV.equals(toNode)) {
                throw new IllegalArgumentException("This insertion"
                    + "would create a duplicate edge.");
            }
        }
        EdgeNode<E> newE = new EdgeNode<E>();
        newE.value = e;
        newE.manufacturer = this;
        newE.fromV =  fromNode;
        newE.toV = toNode;
        newE.toV.incomingEdges.add(newE);
        newE.fromV.outgoingEdges.add(newE);
        this.allEdges.add(newE);
        return newE;
    }

    /**
        Remove a vertex.
        @param v Vertex position to remove.
        @return Element from destroyed vertex position.
        @throws IllegalArgumentException If vertex position
            is invalid or if vertex still has incident edges.
    */
    public V remove(Vertex<V> v)
        throws IllegalArgumentException {
        VertexNode<V> n = this.validate(v);
        if (!n.incomingEdges.isEmpty() || !n.outgoingEdges.isEmpty()) {
            throw new IllegalArgumentException("This vertex"
                + "still has incident edges.");
        }
        V val = n.value;
        n.value = null;
        n.manufacturer = null;
        this.allVertices.remove(n);
        return val;
    }

    /**
        Remove an edge.
        @param e Edge position to remove.
        @return Element from destroyed edge position.
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    public E remove(Edge<E> e)
        throws IllegalArgumentException {
        EdgeNode<E> n = this.validate(e);
        E val = n.value;
        n.value = null;
        n.manufacturer = null;
        n.fromV = null;
        n.toV = null;
        this.allEdges.remove(n);
        return val;
    }

    /**
        Vertices of graph.
        @return Iterable that can be used to explore the
          vertices of the graph; the remove() method of
          the iterator should not affect the graph.
    */
    public Iterable<Vertex<V>> vertices() {
        return this.allVertices;
    }

    /**
        Edges of graph.
        @return Iterable that can be used to explore the
          edges of the graph; the remove() method of the
          iterator should not affect the graph.
    */
    public Iterable<Edge<E>> edges() {
        return this.allEdges;
    }

    /**
        Outgoing edges of vertex.
        @param v Vertex position to explore.
        @return Iterable that can be used to explore the
          outgoing edges of the given vertex; the remove()
          method of the iterator should not affect the graph.
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    public Iterable<Edge<E>> outgoing(Vertex<V> v)
        throws IllegalArgumentException {
        VertexNode<V> n = this.validate(v);
        return n.outgoingEdges;
    }

    /**
        Incoming edges of vertex.
        @param v Vertex position to explore.
        @return Iterable that can be used to explore the
          incoming edges of the given vertex; the remove()
          method of the iterator should not affect the graph.
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    public Iterable<Edge<E>> incoming(Vertex<V> v)
        throws IllegalArgumentException {
        VertexNode<V> n = this.validate(v);
        return n.incomingEdges;
    }

    /**
        Start vertex of edge.
        @param e Edge position to explore.
        @return Vertex position edge starts from.
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    public Vertex<V> from(Edge<E> e)
        throws IllegalArgumentException {
        EdgeNode<E> n = this.validate(e);
        return n.fromV;
    }

    /**
        End vertex of edge.
        @param e Edge position to explore.
        @return Vertex position edge leads to.
        @throws IllegalArgumentException If edge position
            is invalid.
    */

    public Vertex<V> to(Edge<E> e)
        throws IllegalArgumentException {
        EdgeNode<E> n = this.validate(e);
        return n.toV;
    }

    /**
        Label vertex with object.
        @param v Vertex position to label.
        @param l Label object.
        @throws IllegalArgumentException If vertex position
            is invalid or label is null.
    */
    public void label(Vertex<V> v, Object l)
        throws IllegalArgumentException {
        if (l == null) {
            throw new IllegalArgumentException("Null label not allowed.");
        }
        VertexNode<V> n = this.validate(v);
        n.labelV = l;
    }

    /**
        Label edge with object.
        @param e Edge position to label.
        @param l Label object.
        @throws IllegalArgumentException If edge position
            is invalid or label is null.
    */
    public void label(Edge<E> e, Object l)
        throws IllegalArgumentException {
        if (l == null) {
            throw new IllegalArgumentException("Null label not allowed.");
        }
        EdgeNode<E> n = this.validate(e);
        n.labelE = l;
    }

    /**
        Vertex label.
        @param v Vertex position to query.
        @return Label object (or null if none).
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    public Object label(Vertex<V> v)
        throws IllegalArgumentException {
        VertexNode<V> n = this.validate(v);
        return n.labelV;
    }

    /**
        Edge label.
        @param e Edge position to query.
        @return Label object (or null if none).
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    public Object label(Edge<E> e)
        throws IllegalArgumentException {
        EdgeNode<E> n = this.validate(e);
        return n.labelE;
    }

    /**
        Clear all labels.
    */
    public void clearLabels() {
        for (int i = 0; i < this.allVertices.size(); i++) {
            ((VertexNode) this.allVertices.get(i)).labelV = null;
        }
        for (int i = 0; i < this.allEdges.size(); i++) {
            ((EdgeNode) this.allEdges.get(i)).labelE = null;
        }
    }

    /**
        Provides a string representation for the sparse graph.
        @return The string representation.
    */
    public String toString() {
        String s =  "digraph {\n";
        for (int i = 0; i < this.allVertices.size(); i++) {
            s += "  \"" + ((VertexNode) this.allVertices.get(i)).value
                + "\";\n";
        }
        EdgeNode<E> eTemp;
        for (int i = 0; i < this.allEdges.size(); i++) {
            s += "  \"" + ((EdgeNode) this.allEdges.get(i)).fromV.get()
                + "\" -> \""
                + ((EdgeNode) this.allEdges.get(i)).toV.get() + "\" [label=\""
                + ((EdgeNode) this.allEdges.get(i)).value + "\"];\n";
        }
        s += "}";
        return s;
    }
}
