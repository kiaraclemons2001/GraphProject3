public class Vehicle{
  int id; 
  double x;
  double y;

  public Vehicle(){
    id=0;
    x=0.0;
    y=0.0;
  }

  public Vehicle(int id, double coordinate1, double coordinate2){
    this.id=id;
    x=coordinate1;
    y=coordinate2;
  }

  public void print(){
    System.out.println("The id is: "+id+". The x coordinate is: "+x+". The y coordinate is: "+y+". ");
  }

  public int getID(){
    return id;
  }

    public double getX(){
    return x;
  }

  public double getY(){
    return y;
  }

  public void setID(int newID){
    id=newID;
  }

  public void setX(double newX){
    x=newX;
  }

  public void setY(double newY){
    y=newY;
  }
  
}
