import java.util.ArrayList;
import java.util.List;

public class Vertex {
  List<Edge> outEdges; // edges from this node
  int id;
  Vertex parent;
  
  Vertex(List<Edge> outEdges) {
    this.outEdges = outEdges;
  }
  
  Vertex(List<Edge> outEdges, int id) {
    this(outEdges);
    this.id = id;
  }
  
  ArrayList<Vertex> parentPath() {
    ArrayList<Vertex> temp = new ArrayList<Vertex>();
    if (this.parent == null) {
      return temp;
    } else {
      temp.addAll(this.parentPath());
      return temp;
    }
  }
  
//  boolean hasPathTo(Vertex dest) {
//    for (Edge e: this.outEdges) {
//      if (e.to == dest || e.to.hasPathTo(dest)) 
//        return true;
//    }
//    return false;
//  }
  
  boolean hasPathTo(Vertex dest) {
    return this.hasPathTo(dest, new ArrayList<Vertex>());
  }
  
  boolean hasPathTo(Vertex dest, List<Vertex> blacklist) {
    // (And note that you've checked this edge) 
    blacklist.add(this);
    
    // Check every edge
    for (Edge e: this.outEdges) {
      // This has a path if the edge is going to your destination, or 
      // if the to isnt in the blacklist and has a path to the destination
      if (e.to == dest || (!blacklist.contains(e.to) && e.to.hasPathTo(dest, blacklist)))
        return true;
    }
    return false;
  }
}
