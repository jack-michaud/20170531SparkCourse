
public class Edge {
  Vertex from;
  Vertex to;
  int weight;
  
  Edge(Vertex from, Vertex to, int weight) {
    this.from   = from;
    this.to     = to;
    this.weight = weight;
  }
  
  Edge(Vertex from, Vertex to) {
    this(from, to, 1);
  }
}
