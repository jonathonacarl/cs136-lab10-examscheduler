// Students, fill this in.
import structure5.*;

/**
 * An implentation of a Student which contains a student's name and their courses
 */
public class Student {

  private String name;
  private Vector<String> courses = new Vector<String>(4);

  /**
   * Constructs a student object with a name and vector of their courses
   */
  public Student(String name, Vector<String> courses) {
    this.name = name;
    this.courses = courses;
  }

  /** gets the name of a given student
   * @pre student is not null
   * @return a string
   */
  public String getName() {
    return this.name;
  }
  /** gets the courses of a given student
   * @pre student is not null
   * @return an array of strings
   */
  public Vector<String> getCourses() {
    return this.courses;
  }

  /**
   * A string representation of the Student
   */
  public String toString() {
    return this.name + "\n" + courses.get(0) + "\n" + courses.get(1) + "\n" + courses.get(2)
    + "\n" + courses.get(3) + "\n";
  }

  /**
   * For testing
   */
  public static void main(String[] args) {
    Vector<String> courses = new Vector<String>();
    courses.add("CSCI 136");
    courses.add("MATH 251");
    courses.add("ENGL 201");
    courses.add("PHIL 101");
    Student test = new Student("Bill", courses);
    System.out.println(test);
  }
}
