public class Edge {
  int src; //source number, what number it is
  int dest; //destination number, what number it is connected with
  double w; //weight or distance
  public Edge next;

  public Edge(){
    src=0;
    dest=0;
    w=0.0;
    next=null;
    
  }
  
  public Edge(int source, int destination, double weight)
  {
    src = source;
    dest= destination;
    w = weight;
    next=null;
  }

  public Edge(int source)
  {
    src = source;
    dest=0;
    w=0.0;
    next=null;
  }

   public void print(){
     if (next==null){
       System.out.println("The source is: "+src+". The destination is: "+dest+". The weight/distance is: "+w+". ");
     }
     else{
       System.out.println("The source is: "+src+". The destination is: "+dest+". The weight/distance is: "+w+". And the next edge is: "+next.getDest()+". ");
     }
  }

  public int getSrc(){
    return src;
  }

    public int getDest(){
    return dest;
  }

  public double getW(){
    return w;
  }
  
  public void setSrc(int newSrc){
    src=newSrc;
  }

  public void setDest(int newDest){
    dest=newDest;
  }

  public void setW(double newW){
    w=newW;
  }
  
}
