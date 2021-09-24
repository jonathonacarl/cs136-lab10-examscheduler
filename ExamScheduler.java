import structure5.*;
import java.util.Scanner;
import java.util.Iterator;

// Includes an implementation for Extension 1.

/**
* A GraphListUndirected-based implementation solving the final exam scheduling problem
*/
public class ExamScheduler {

    private static final int NUM_COURSES = 4;

  /**
  * Adds edges between all of the classes that a given student takes
  * @param courses, a vector containg the courses from the file
  * @param graph, an undirected graphList housing our exam scheduling system
  * @pre courses is not null, graph is not null
  */
  public static void addEdges(Vector<String> courses, GraphListUndirected<String, Integer> graph) {

    for (int i = 0; i < courses.size() - 1; i++) {
      String course1 = courses.get(i);
      for (int j = i + 1; j < courses.size(); j++) {
        //create references between all possible combinations of courses
        String course2 = courses.get(j);
        graph.addEdge(course1, course2, 1);
        //label edge 1 since a student shares those two courses in common
      }
    }
  }


  /**
  * Builds the graph between a set of given students and their courses
  * @param courses, a vector containg the courses from the file
  * @param graph, an undirected graphList housing our exam scheduling system
  * @param students, a vector of containg Student objects, namely their name and courses
  */
  public static void buildGraph(GraphListUndirected<String, Integer> graph, Vector<Student> students, Vector<String> courses) {

    Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine()) {
      //store the name of a student
      String name = sc.nextLine();
      courses = new Vector<String>(NUM_COURSES);
      for (int i = 0; i < NUM_COURSES; i++) {
        //we assume each student has the same numCourses
        String course = sc.nextLine();
        courses.add(course);
        if (!graph.contains(course)) graph.add(course);
        //ensures we have no repeat vertices
      }
      //add edges between all of the courses of a student
      Vector<String> temp = new Vector<String>(courses);
      addEdges(temp, graph);
      Student s = new Student(name, courses);
      students.add(s);
      //keep track of students and their courses
    }
  }


  /**
  * Uses a graph to determine the exam slots such that no student has two classes in the same slot
  * @param courses, a vector containg the courses from the file
  * @param graph, an undirected graphList housing our exam scheduling system
  * @param students, a vector of containg Student objects, namely their name and courses
  * @pre graph, students, courses are not null
  * @return a vector of courses sorted in increasing order by slot number
  */
  public static Vector<Vector<String>> examSlots(GraphListUndirected<String, Integer> graph, Vector<Student> students, Vector<String> courses) {

    //walk through the graph, arbitrarily picking courses and adding to a slot if they share no edges
    Vector<Vector<String>> slots = new Vector<Vector<String>>();
    for (int i = 0; i < graph.size(); i++) {
      Vector<String> temp = new Vector<String>();
      Iterator<String> iter = graph.iterator();
      while (iter.hasNext()) {
        String course = iter.next();
        //ensures we never add a course to more than one exam slot
        if (!graph.isVisited(course)) {
          //ensures that current course does not overlap with any other courses in slot
          if (!sharedEdges(graph, temp, course)) {
            temp.add(course);
            graph.visit(course);
          }
        }
      }
      //prevent extra iterations
      if (temp.isEmpty()) break;
      //build vector of courses sorted by slot number
      slots.add(temp);
    }
    return slots;
  }


  /**
  * A helper method for examSlots that checks if any two vertices share an edge
  * @param temp, a vector containg the courses from the file
  * @param graph, an undirected graphList housing our exam scheduling system
  * @param course, a string that contians the name of a course
  * @return a bool true iff two vertices share an edge, false otherwise
  */
  public static boolean sharedEdges(GraphListUndirected<String, Integer> graph, Vector<String> temp, String course) {
    for (String c : temp) {
      if (graph.containsEdge(c, course)) return true;
    }
    return false;
  }

  /**
  * A helper method which builds an alphabetic ordering of all given classes and their exam slot
  * @param courses, a vector containg the courses from the file
  * @param graph, an undirected graphList housing our exam scheduling system
  * @param students, a vector of containg Student objects, namely their name and courses
  * @pre graph, students, and courses are non-null
  * @return an OrderedVector of ComparableAssociations containing a string and integer corresponding to a course and it's slot, respectively
  */
  public static OrderedVector<ComparableAssociation<String, Integer>> scheduleBuilder(GraphListUndirected<String, Integer> graph, Vector<Student> students, Vector<String> courses) {
    Vector<Vector<String>> slots = examSlots(graph, students, courses);
    OrderedVector<ComparableAssociation<String, Integer>> sortedCourses = new OrderedVector<ComparableAssociation<String, Integer>>();
    for (int i = 0; i < slots.size(); i++) {
      Vector<String> singleSlot = slots.get(i);
      // courses already sorted by slot
      for (int j = 0; j < singleSlot.size(); j++) {
        //associate a course with its slot
        ComparableAssociation<String, Integer> course = new ComparableAssociation<String, Integer>(singleSlot.get(j), i);
        sortedCourses.add(course);
        //OrderedVector sorts by key (course)
      }
    }
    return sortedCourses;
  }

  /**
  * A string representation of courses alphabetically ordered with their corresponding slot
  * @param schedule, an OrderedVector of ComparableAssociations containing a string and integer corresponding to a course and it's slot, respectively
  */
  public static String finalExamSchedule(OrderedVector<ComparableAssociation<String, Integer>> schedule) {
    String str = "";
    Iterator<ComparableAssociation<String, Integer>> iter = schedule.iterator();
    while (iter.hasNext()) {
      ComparableAssociation<String, Integer> temp = iter.next();
      str += temp.getKey() + " (Slot " + temp.getValue() + ")\n";
    }
    return str;
  }

  /**
  * A string representation of courses in order by exam slots
  * @pre graph, students, and courses are non-null
  */
  public static String toString(GraphListUndirected<String, Integer> graph, Vector<Student> students, Vector<String> courses) {
    Vector<Vector<String>> slots = examSlots(graph, students, courses);
    String str = "";
    for (int i = 0; i < slots.size(); i++) {
      str += "Slot " + (i + 1) + ": ";
      for (int j = 0; j < slots.get(i).size(); j++) {
        str += slots.get(i).get(j) + " ";
      }
      str += "\n";
    }
    return str;
  }

  /**
  * For testing.
  */
  public static void main(String[] args) {
    GraphListUndirected<String, Integer> graph = new GraphListUndirected<String, Integer>();
    Vector<Student> students = new Vector<Student>();
    Vector<String> courses = new Vector<String>(NUM_COURSES);

    buildGraph(graph, students, courses);
    System.out.println(finalExamSchedule(scheduleBuilder(graph, students, courses)));


  }
}
