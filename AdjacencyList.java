import java.util.Vector;

public class AdjacencyList {
  public Edge root;
  public Edge head;
  int size;

  public AdjacencyList() {
    head = null;
    root = null;
  }

  public void add(Edge input) {
    if (root == null) {
      root = input;
      head = root;
    } else if (root.next == null){
      head.next = input;
      head = input;
    } else {
      Edge walker;
      for (walker = root; walker.next != null; walker = walker.next){
        if (input.getW() < walker.next.getW()){
          input.next = walker.next;
          walker.next = input;
          return;
        }
      }
      head.next = input;
      head = input;
    }
  }

  public void traverse() {
    Edge walker;
    for (walker = root; walker != null; walker = walker.next) {
      walker.print();
    }
  }

  public int size()
  {
      traverse();
      return size;
  }

  public Vector traverseAL() {
    Edge walker;
    Vector <Edge> v = new Vector <Edge>();
    for (walker = root; walker != null; walker = walker.next) {
      v.add(walker);
      System.out.print("Adding to the vector: ");
      walker.print();
    }
    return v;
  }

    public Edge find (int it) {
    Edge walker;
    for (walker = root; walker != null; walker = walker.next) {
      if (it==walker.getDest()){
        return walker;
      } 
    }
      System.out.println("Could not find the id for "+it+". ");
      return null;
  }

  public void remove (Edge delete){
    Edge walker;
    Edge PrevEdge = null;
    for (walker=root; walker != null; walker=walker.next){
      if (walker.getDest()==delete.getDest()){
        if(walker.equals(root)){ //removing at the root (beginning)
          root=root.next;
        }
        else if (walker.equals(head)){//removing at the end 
          PrevEdge.next=null;
          head=PrevEdge;
        }
        else{ //removing in the middle
          PrevEdge.next=walker.next;
        }
        
      }
      PrevEdge=walker;
    }
  }

  /*public Vector addVector(int num4Vector){
    
    
  }*/
  }

