import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tester.*;


public class Graph {
  List<Vertex> allVertices;
  
  Graph(List<Vertex> vertices) {
    this.allVertices = vertices;
  }
  
  List<Vertex> inEdges(Vertex vertex) {
    List<Vertex> acc = new ArrayList<Vertex>();
    
    // For all vertices...
    for (Vertex v: this.allVertices) {
      // That don't equal this vertex...
      if(!v.equals(vertex)) {
        // Check all edges to see if...
        for (Edge e: v.outEdges) {
          // ... the edge goes to this vertex.
          // If it does,
          if (e.to.equals(vertex)) {
            // Add that vertex to the accumulator.
            acc.add(e.from);
          }
        }
      }
    }
    
    return acc;
  }
  
//  boolean hasPathBetween(Vertex from, Vertex to) {
//    Deque<Vertex> alreadySeen = new Deque<Vertex>();
//    Deque<Vertex> worklist = new Deque<Vertex>();
//    
//    worklist.addAtHead(from);
//    
//    while(!worklist.isEmpty()) {
//      // The next vertex to process -- popped off the worklist
//      Vertex next = worklist.removeFromTail();
//      if (next == to) {
//        return true;
//      }
//      else if (alreadySeen.contains(next)) {
//        // Do nothing - we've already seen it, no need to process
//      } else {
//        // We havent seen the next, so we should add all vertices its connected to
//        // to the worklist and at it to the alreadySeen list
//        for (Edge e: next.outEdges) {
//          worklist.addAtHead(e.to);
//        }
//        alreadySeen.addAtHead(next);
//      }
//    }
//    
//    return false;
//  }

  boolean dfs(Vertex from, Vertex to) {
    return hasPathBetween(from, to, new Stack());
  }
  
  boolean bfs(Vertex from, Vertex to) {
    return hasPathBetween(from, to, new Queue());
  }
  
  boolean hasPathBetween(Vertex from, Vertex to, ICollection<Vertex> worklist) {
    Deque<Vertex> alreadySeen = new Deque<Vertex>();
    
    worklist.add(from);
    
    while(!worklist.isEmpty()) {
      Vertex next = worklist.remove();
      
      if (next == to) {
        return true;
      } else if (alreadySeen.contains(next)) {
        // Nothing
      } else {
        for (Edge e: next.outEdges) {
          worklist.add(e.to);
        }
        alreadySeen.addAtHead(next);
      }
    }
    
    return false;
  }
  
  ArrayList<Vertex> pathBetween(Vertex from, Vertex to, ICollection<Vertex> Q) {
    
    ArrayList<Vertex> S = new ArrayList<Vertex>();
    
    S.add(from);
    Q.add(from);
    Vertex current = from;
    
    while (!Q.isEmpty()) {
      current = Q.remove();
      if (current == to) {
        break;
      }
      for (Edge e: current.outEdges) {
        Vertex n = e.to;
        if (!S.contains(n)) {
          S.add(n);
          n.parent = current;
          Q.add(n);
        }
      }
    }
    
    ArrayList<Vertex> path = new ArrayList<Vertex>();
    while (true) {
      path.add(current);
      if (current.parent == null) {
        break;
      } else {
        current = current.parent;
      }
    }
    return path;
  }
}

//public class Graph {
//
//  List<List<Integer>> matrix;
//  
//  // Connecting i TO j
//  Integer getWeightConnecting(int i, int j) {
//    return matrix.get(i).get(j);
//  }
//}

class ExamplesGraph {
  
  Graph graph1;
  Vertex vertex1;
  Vertex vertex2;
  Vertex vertex3;
  
  public void init() {
    Edge edge1 = new Edge(null, null);
    ArrayList<Edge> edgeList1 = new ArrayList<Edge>();
    edgeList1.add(edge1);
    vertex1 = new Vertex(edgeList1);
    vertex2 = new Vertex(new ArrayList<Edge>());
    vertex3 = new Vertex(new ArrayList<Edge>());
    edge1.from = vertex1;
    edge1.to = vertex2;
    
    ArrayList<Vertex> vertices1 = new ArrayList<Vertex>();
    vertices1.add(vertex1);
    vertices1.add(vertex2);
    vertices1.add(vertex3);
    this.graph1 = new Graph(vertices1);
  }
  
  
  boolean testGraph(Tester t) {
    init();
    return true;
  }
  
  boolean testInEdges(Tester t) {
    init();
    ArrayList<Vertex> tempList = new ArrayList<Vertex>();
    tempList.add(vertex1);
    
    return t.checkExpect(this.graph1.inEdges(this.vertex2), tempList);
  }
  
  boolean testVertexHasPathTo(Tester t) {
    init();
    boolean beforeAdding = t.checkExpect(vertex2.hasPathTo(vertex1), false);
    
    // This causes a stack overflow exception w/o a blacklist!
    Edge tempEdge1 = new Edge(vertex2, vertex3);
    Edge tempEdge2 = new Edge(vertex3, vertex2);
    Edge tempEdge3 = new Edge(vertex3, vertex1);
    vertex2.outEdges.add(tempEdge1);
    vertex3.outEdges.add(tempEdge2);
    vertex3.outEdges.add(tempEdge3);
    
    return t.checkExpect(vertex1.hasPathTo(vertex2), true)
        && t.checkExpect(vertex2.hasPathTo(vertex1), true)
        && beforeAdding;
  }
  
  boolean testHasPathBetween(Tester t) {
    init();
    
    Edge tempEdge1 = new Edge(vertex2, vertex3);
    Edge tempEdge2 = new Edge(vertex3, vertex2);
    Edge tempEdge3 = new Edge(vertex3, vertex1);
    vertex2.outEdges.add(tempEdge1);
    vertex3.outEdges.add(tempEdge2);
    vertex3.outEdges.add(tempEdge3);
    
    return t.checkExpect(this.graph1.bfs(vertex1, vertex2), true)
        && t.checkExpect(this.graph1.dfs(vertex1, vertex3), true);
  }
  
  boolean testPathBetween(Tester t) {
    for (Vertex v: this.graph1.pathBetween(vertex1, vertex2, new Queue<Vertex>())) {
      System.out.println(v);
    }
    return t.checkExpect(this.graph1.pathBetween(vertex1, vertex2, new Queue<Vertex>()), null);
  }
  
  
}