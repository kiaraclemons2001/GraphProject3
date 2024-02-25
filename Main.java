import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.util.Vector;
import java.lang.Math;
import java.util.Stack;

class Main {
  public static double distance(Vehicle v1, Vehicle v2) {
    double x1 = v1.getX();
    double y1 = v1.getY();

    double x2 = v2.getX();
    double y2 = v2.getY();

    double distanceX = Math.pow((x2 - x1), 2);
    double distanceY = Math.pow((y2 - y1), 2);

    double finalDistance = Math.sqrt(distanceX + distanceY);
    return (Math.round(finalDistance * 10.0) / 10.0);
  }

  public static boolean loopArray(Vector<Edge> T, Edge v) {
    boolean inside;
    for (int i = 0; i < T.size(); i++) { // iterate through the edge array
      System.out.print("Printing: ");
      T.get(i).print();
      if (T.get(i).getDest() == v.getDest()) {
        inside = true; // if there is something in the array, increment
        System.out.println("The index for the vector destination is: " + T.get(i).getDest()
            + " and the edge's destination is: " + v.getDest() + " so the inside is true");
        return inside;
      }

    }
    inside = false;
    System.out.println("Returning " + inside);
    return inside;
  }

  public static void main(String[] args) throws IOException {
    String filename = "data.txt";
    File file = new File(filename);
    Scanner fileInput = null;

    Scanner userInput = new Scanner(System.in);
    fileInput = new Scanner(file);
    int dataNum = fileInput.nextInt();
    System.out.println(dataNum);
    System.out.println(fileInput.nextLine());

    Vector<Vehicle> Vehicles = new Vector<Vehicle>(dataNum);

    Vector<Edge> Graph = new Vector<Edge>(dataNum);

    Vector<AdjacencyList> ALists = new Vector<AdjacencyList>(dataNum);

    double dataRange = fileInput.nextDouble();
    System.out.println(dataRange);
    System.out.println(fileInput.nextLine());
    int count = 0;
    while (fileInput.hasNext()) {
      double x = fileInput.nextDouble();
      double y = fileInput.nextDouble();
      Vehicle node = new Vehicle(count, x, y);
      Vehicles.add(node); // info for coordinates
      node.print();
      Edge joint = new Edge(count); // for the 1st vector; the one that is the "head"
      Graph.add(joint);
      AdjacencyList newL = new AdjacencyList(); // the vector for storing the links
      newL.add(joint);
      ALists.add(newL);
      count++;
    }

    for (int i = 0; i < Vehicles.size(); i++) {
      for (int j = 0; j < Vehicles.size(); j++) {
        if (i == j) {
          // do nothing
          // go back
        } else { // if i is not j, calculate the distance
          double distance = distance(Vehicles.get(i), Vehicles.get(j)); // calculate distance
          if (distance < dataRange) {
            Edge link = new Edge(i, j, distance);
            ALists.get(i).add(link);
            // add to its adjacency list
          }
        } // if i does not j

      } // inside for loop for j
    } // outer for loop for i

    String selectedOption = "";
    while (!selectedOption.equals("8")) {
      System.out.println(
          "Please choose from the following options: \n  1. Display All Edges \n  2. Display All Adjacent Vehicles \n  3. Move a Vehicle \n  4. DFS \n  5. BFS \n  6. MST \n  7. Shortest Path \n  8. Exit ");
      System.out.print("Please enter a number 1-8: ");
      selectedOption = userInput.nextLine();
      switch (selectedOption) {
        case "1":
          for (Edge joint : Graph) {
            joint.print();
          }
          break;
        case "2":
          String selectedOption2 = "";
          boolean check = true;
          System.out.print("Please enter the index of the edge you want the adjacency list of: ");
          selectedOption2 = userInput.nextLine();
          while (check) {
            try {
              ALists.get(Integer.parseInt(selectedOption2)).traverse();
              check = false;
            } catch (Exception e) {
              System.out.print("Please enter the index of the edge you want the adjacency list of: ");
              selectedOption2 = userInput.nextLine();
            }
          }
          break;
        case "3":
          System.out.println("Please enter the ID of vehicle you would like to move: ");
          int moveID = userInput.nextInt();
          System.out.println("Please enter the new x coordinate for the vehicle: ");
          double newX = userInput.nextDouble();
          System.out.println("Please enter the new y coordinate for the vehicle: ");
          double newY = userInput.nextDouble();
          // changing the vehicle information

          // delete edge
          Edge deleteEdge = null;
          int q = 0;
          while (deleteEdge == null && q < dataNum) {
            deleteEdge = ALists.get(q).find(moveID);
            q++;
          }
          System.out.println("Found the delete edge!");
          for (int i = 0; i < dataNum; i++) {
            ALists.get(i).remove(deleteEdge);
            System.out.println("Removing the id for the " + deleteEdge.getDest() + " edge from " + i + " adjacency list");
          }

          // change coordinates
          Vehicles.get(moveID).setX(newX);
          Vehicles.get(moveID).setY(newY);

          // add edge back into the adjacency list
          for (int i = 0; i < dataNum; i++) {
            // calculate the distance
            double distance = distance(Vehicles.get(i), Vehicles.get(moveID)); // calculate distance
            if (distance < dataRange) {
              Edge link = new Edge(i, moveID, distance);
              ALists.get(i).add(link);
              // add to its adjacency list
              System.out.println("Adding the new vehicle to the adjacency list for " + i);
            }
          }

          break;
        case "4":
          System.out.print("Please enter vehicle ID: ");
          int target = userInput.nextInt();

          Stack<Vehicle> tracker = new Stack<Vehicle>();
          boolean[] visited = new boolean[dataNum];
          Vehicle current = new Vehicle();

          tracker.push(Vehicles.elementAt(target));
          while (!tracker.empty()) {
            current = tracker.pop();
            if (!visited[current.getID()]) {
              current.print();
              visited[current.getID()] = true;
              AdjacencyList targetList = ALists.get(current.getID());

              for (int i = 0; i < targetList.size(); i++) {
                if (!visited[targetList.find(i).dest]) {
                  tracker.push(Vehicles.get(targetList.find(i).dest));
                }
              }
            }
          }
          break;
        case "5":
          /*
           * System.out.print("Please enter vehicle ID: ");
           * Vehicle targeted = Vehicles.get(userInput.nextInt());
           * boolean[] touched = new boolean[dataNum];
           * AdjacencyList<Vehicle> hitlist = new AdjacencyList<Vehicle>();
           * 
           * hitlist.add(Vehicles.get(targeted.id));
           * touched[targeted.id] = true;
           * 
           * while (hitlist.size() > 0)
           * {
           * targeted = hitlist.poll();
           * targeted.print();
           * 
           * AdjacencyList associates = ALists.get(targeted.id);
           * associates.traverse();
           * int flag = 0;
           * while(flag < associates.size())
           * {
           * if (!touched[associates.find(flag).src])
           * {
           * touched[associates.find(flag).src] = true;
           * hitlist.add(Vehicles.get(associates.find(flag).src));
           * }
           * flag++;
           * }
           * }
           */
          break;
        case "6":

          Vector<Edge> MST = new Vector<Edge>(); // Minimum spanning tree; "T"
          Vector<Edge> MSTwW = new Vector<Edge>(); // Minimum spanning tree with weights; "TV"

          // make function for v which adds the adjacency list for u
          // loop through adjacency list for u
          // Add each element into the vector v from the adjacency list for u
          // make visited array/arraylist for v which are the edges not in MSTwW yet
          // v would be the least weight so loop through arraylist for v to find the least
          // weight
          Edge u = Graph.get(0); // has to be in TV/MSTwW
          MSTwW.add(u);
          int iteration = 0;
          while (MST.size() < dataNum) { // need other condition to stop loop ?
            Vector<Edge> aL4U = new Vector<Edge>(); // Adjacency list for u
            aL4U = ALists.get(u.getDest()).traverseAL();
            Edge min = aL4U.get(1);
            for (int i = 0; i < aL4U.size(); i++) { // find the least weight
              if (min.getW() > aL4U.get(i).getW() && (loopArray(MSTwW, aL4U.get(i))) && aL4U.get(i).getW() != 0.0) {
                min = aL4U.get(i);
                System.out.print("The new min is: ");
                min.print();
                System.out.print("Because " + min.getW() + " is > " + aL4U.get(i).getW()
                    + "and it is not in the loop array because the function is: " + (!loopArray(MSTwW, aL4U.get(i))));
                // check that v is not already visited
              }
            } // found v, the least weight in adjacency list
            MSTwW.add(min);
            System.out.println("Adding the min to the MSTwW array");
            min.print();
            System.out.println("Adding these two to the array: ");
            MSTwW.get(iteration).print();
            System.out.print(" and ");
            MSTwW.get(iteration + 1).print();

            MST.add(MSTwW.get(iteration));
            MST.add(MSTwW.get(iteration + 1));

            u = MSTwW.get(iteration + 1); // in MSTwW
            System.out.print("The new u is ");
            u.print();
            if (aL4U == null) { // no edges of least weight that havent been visited
              break;
            }
            iteration++;
          }

          // end of while loop, see if there is an MST or not; print or not print
          if (MST.size() < dataNum - 1) {
            System.out.println("There is no spanning tree");
          } else {
            for (int i = 0; i < MST.size(); i++) {
              MST.get(i).print();
            }
          }

          break;
        case "7":
            System.out.println("\t - - - Unfinished code - - -");
          break;
        case "8":
          System.out.print("  ");
          selectedOption = "8";
          System.out.println("  Exited…");
          userInput.close();
          return;
        default:
          if (!selectedOption.equals("8")) {
            System.out.println("  !Please enter a digit 1-6…");
          }
      }
    }
  }
}
