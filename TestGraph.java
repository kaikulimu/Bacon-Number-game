/**
 * @author: Vincent Yan
 * @email: vyan1@jhu.edu
*/


import static org.junit.Assert.assertEquals;

import java.util.Iterator; 
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.Test;
import org.junit.runner.RunWith;
import graphs.*;
 
@RunWith(Theories.class)
public class TestGraph {
 
    private interface Fixture {
        Graph<String, String> init();
    }
 
    @DataPoint
    public static final Fixture SparseGraph = new Fixture() {
        public SparseGraph<String, String> init() {
            return new SparseGraph<String, String>();
        }
    };

    @Theory
    public void insertVertex(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        a.insert("Vincent");
        a.insert("Tom");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        assertEquals(iter.next().get(), "Peter");
        assertEquals(iter.next().get(), "Andy");
        assertEquals(iter.next().get(), "Vincent");
        assertEquals(iter.next().get(), "Tom");
        assertEquals(iter.hasNext(), false);
    }

    @Theory
    public void insertEdge(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        a.insert("Vincent");
        a.insert("Tom");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        Vertex<String> vincent = iter.next();
        Vertex<String> tom = iter.next();
        a.insert(peter, andy, "1");
        a.insert(vincent, tom, "2");
        a.insert(tom, vincent, "3"); 
        Iterator<Edge<String>> iter2 = a.edges().iterator();
        Edge<String> e1 = iter2.next();
        Edge<String> e2 = iter2.next();
        Edge<String> e3 = iter2.next();
        assertEquals(iter2.hasNext(), false);
        assertEquals(e1.get(), "1");
        assertEquals(a.from(e1), peter);
        assertEquals(a.to(e1), andy);
        assertEquals(e2.get(), "2");
        assertEquals(a.from(e2), vincent);
        assertEquals(a.to(e2), tom);
        assertEquals(e3.get(), "3");
        assertEquals(a.from(e3), tom);
        assertEquals(a.to(e3), vincent);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void validateVertexFailCuzNull(Fixture fix) throws IllegalArgumentException {
        Graph<String, String> a = fix.init();
        a.insert(null, null, "1");
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void validateVertexFailCuzNotInGraph(Fixture fix) throws IllegalArgumentException {
        Graph<String, String> a = fix.init();
        Graph<String, String> b = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        b.insert(peter, andy, "1");
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void insertEdgeFailCuzSelfLoop(Fixture fix) throws IllegalArgumentException {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        a.insert(peter, peter, "1");
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void insertEdgeFailCuzDuplicateEdge(Fixture fix) throws IllegalArgumentException {
        Graph<String, String> a = fix.init();
        Graph<String, String> b = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        a.insert(peter, andy, "1");
        a.insert(peter, andy, "2");
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void validateEdgeFailCuzNull(Fixture fix) throws IllegalArgumentException {
        Graph<String, String> a = fix.init();
        a.from(null);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void validateEdgeFailCuzNotInGraph(Fixture fix) throws IllegalArgumentException {
        Graph<String, String> a = fix.init();
        Graph<String, String> b = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        a.insert(peter, andy, "1");
        Iterator<Edge<String>> iter2 = a.edges().iterator();
        Edge<String> e1 = iter2.next();
        b.from(e1);
    }

    @Theory
    public void removeVertex(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        a.insert("Vincent");
        a.insert("Tom");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        Vertex<String> vincent = iter.next();
        Vertex<String> tom = iter.next();
        a.remove(peter);
        a.remove(tom);
        Iterator<Vertex<String>> iter2 = a.vertices().iterator();
        Vertex<String> v1 = iter2.next();
        Vertex<String> v2 = iter2.next();
        assertEquals(v1.get(), "Andy");
        assertEquals(v2.get(), "Vincent");
        assertEquals(iter2.hasNext(), false);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void removeVertexFailCuzIncidentEdges(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        a.insert(peter, andy, "1");
        a.remove(peter);
    }

    @Theory
    public void removeEdge(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        a.insert("Vincent");
        a.insert("Tom");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        Vertex<String> vincent = iter.next();
        Vertex<String> tom = iter.next();
        a.insert(peter, andy, "1");
        a.insert(vincent, tom, "2");
        Iterator<Edge<String>> iter2 = a.edges().iterator();
        Edge<String> e1 = iter2.next();
        Edge<String> e2 = iter2.next();
        a.remove(e1);
        Iterator<Edge<String>> iter3 = a.edges().iterator();
        Edge<String> edge = iter3.next();
        assertEquals(edge.get(), "2");
        assertEquals(iter3.hasNext(), false);
    }

    @Theory
    public void iteratorVertices(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        a.insert("Vincent");
        a.insert("Tom");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        assertEquals(iter.hasNext(), true);
        assertEquals(iter.next().get(), "Peter");
        assertEquals(iter.hasNext(), true);
        assertEquals(iter.next().get(), "Andy");
        assertEquals(iter.hasNext(), true);
        assertEquals(iter.next().get(), "Vincent");
        assertEquals(iter.hasNext(), true);
        assertEquals(iter.next().get(), "Tom");
        assertEquals(iter.hasNext(), false);
    }

    @Theory
    public void iteratorEdges(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        a.insert("Vincent");
        a.insert("Tom");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        Vertex<String> vincent = iter.next();
        Vertex<String> tom = iter.next();
        a.insert(peter, andy, "1");
        a.insert(vincent, tom, "2");
        Iterator<Edge<String>> iter2 = a.edges().iterator();
        assertEquals(iter2.hasNext(), true);
        assertEquals(iter2.next().get(), "1");
        assertEquals(iter2.hasNext(), true);
        assertEquals(iter2.next().get(), "2");
        assertEquals(iter2.hasNext(), false);
    }

    @Theory
    public void iteratorOutgoingVertices(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        a.insert("Vincent");
        a.insert("Tom");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        Vertex<String> vincent = iter.next();
        Vertex<String> tom = iter.next();
        a.insert(peter, andy, "1");
        a.insert(peter, vincent, "2");
        a.insert(vincent, tom, "3");
        a.insert(peter, tom, "4");
        Iterator<Edge<String>> iter2 = a.outgoing(peter).iterator();
        assertEquals(iter2.hasNext(), true);
        assertEquals(iter2.next().get(), "1");
        assertEquals(iter2.hasNext(), true);
        assertEquals(iter2.next().get(), "2");
        assertEquals(iter2.hasNext(), true);
        assertEquals(iter2.next().get(), "4");
        assertEquals(iter2.hasNext(), false);
    }

    @Theory
    public void iteratorIncomingVertices(Fixture fix) {
        Graph<String, String> a = fix.init();
        a.insert("Peter");
        a.insert("Andy");
        a.insert("Vincent");
        a.insert("Tom");
        Iterator<Vertex<String>> iter = a.vertices().iterator();
        Vertex<String> peter = iter.next();
        Vertex<String> andy = iter.next();
        Vertex<String> vincent = iter.next();
        Vertex<String> tom = iter.next();
        a.insert(andy, peter, "1");
        a.insert(vincent, peter, "2");
        a.insert(vincent, andy, "3");
        a.insert(tom, peter, "4");
        Iterator<Edge<String>> iter2 = a.incoming(peter).iterator();
        assertEquals(iter2.hasNext(), true);
        assertEquals(iter2.next().get(), "1");
        assertEquals(iter2.hasNext(), true);
        assertEquals(iter2.next().get(), "2");
        assertEquals(iter2.hasNext(), true);
        assertEquals(iter2.next().get(), "4");
        assertEquals(iter2.hasNext(), false);
    }

    @Theory
    public void fromVertexOfEdge(Fixture fix) {
        Graph<String, String> a = fix.init();
        Vertex<String> peter = a.insert("Peter");
        Vertex<String> andy = a.insert("Andy");
        Edge<String> e1 = a.insert(peter, andy, "1");
        assertEquals(a.from(e1), peter);
    }

    @Theory
    public void toVertexOfEdge(Fixture fix) {
        Graph<String, String> a = fix.init();
        Vertex<String> peter = a.insert("Peter");
        Vertex<String> andy = a.insert("Andy");
        Edge<String> e1 = a.insert(peter, andy, "1");
        assertEquals(a.to(e1), andy);
    }   

    @Theory
    public void labelingVertex(Fixture fix) {
        Graph<String, String> a = fix.init();
        Vertex<String> peter = a.insert("Peter");
        Vertex<String> andy = a.insert("Andy");
        a.label(peter, "pizza");
        a.label(andy, "apple pie");
        assertEquals(a.label(peter), "pizza");
        assertEquals(a.label(andy), "apple pie");
    }

    @Theory
    public void labelingEdge(Fixture fix) {
        Graph<String, String> a = fix.init();
        Vertex<String> peter = a.insert("Peter");
        Vertex<String> andy = a.insert("Andy");
        Vertex<String> vincent = a.insert("Vincent");
        Vertex<String> tom = a.insert("Tom");
        Edge<String> e1 = a.insert(peter, andy, "1");
        Edge<String> e2 = a.insert(vincent, tom, "2");
        a.label(e1, "one");
        a.label(e2, "two");
        assertEquals(a.label(e1), "one");
        assertEquals(a.label(e2), "two");
    }
    
    @Theory @Test(expected = IllegalArgumentException.class)
    public void labelNullFails(Fixture fix) {
        Graph<String, String> a = fix.init();
        Vertex<String> peter = a.insert("Peter");
        a.label(peter, null);
    }

    @Theory
    public void clearLabels(Fixture fix) {
        Graph<String, String> a = fix.init();
        Vertex<String> peter = a.insert("Peter");
        Vertex<String> andy = a.insert("Andy");
        Vertex<String> vincent = a.insert("Vincent");
        Vertex<String> tom = a.insert("Tom");
        a.label(peter, "pizza");
        a.label(andy, "apple pie");
        a.label(vincent, "vindaloo");
        a.label(tom, "thai ice tea");
        Edge<String> e1 = a.insert(peter, andy, "1");
        Edge<String> e2 = a.insert(vincent, tom, "2");
        a.label(e1, "one");
        a.label(e2, "two");
        a.clearLabels();
        assertEquals(a.label(peter), null);
        assertEquals(a.label(andy), null);
        assertEquals(a.label(vincent), null);
        assertEquals(a.label(tom), null);
        assertEquals(a.label(e1), null);
        assertEquals(a.label(e2), null);
    }

    @Theory
    public void toStringWorks(Fixture fix) {
        Graph<String, String> a = fix.init();
        assertEquals(a.toString(), "digraph {\n}");
        Vertex<String> peter = a.insert("Peter");
        assertEquals(a.toString(), "digraph {\n  \"Peter\";\n}");
        Vertex<String> andy = a.insert("Andy");
        assertEquals(a.toString(), "digraph {\n  \"Peter\";\n  \"Andy\";\n}");
        Vertex<String> vincent = a.insert("Vincent");
        assertEquals(a.toString(), "digraph {\n  \"Peter\";\n  \"Andy\";\n  \"Vincent\";\n}");
        Vertex<String> tom = a.insert("Tom");
        assertEquals(a.toString(), "digraph {\n  \"Peter\";\n  \"Andy\";\n  \"Vincent\";\n  \"Tom\";\n}");
        Edge<String> e1 = a.insert(peter, andy, "1");
        assertEquals(a.toString(), "digraph {\n  \"Peter\";\n  \"Andy\";\n  \"Vincent\";\n  \"Tom\";"
            + "\n  \"Peter\" -> \"Andy\" [label=\"1\"];\n}");
        Edge<String> e2 = a.insert(vincent, tom, "2");
        assertEquals(a.toString(), "digraph {\n  \"Peter\";\n  \"Andy\";\n  \"Vincent\";\n  \"Tom\";"
            + "\n  \"Peter\" -> \"Andy\" [label=\"1\"];" + "\n  \"Vincent\" -> \"Tom\" [label=\"2\"];\n}");
    }
}
