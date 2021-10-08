# Lab: Exam Scheduling

We will write a program (that could be used) to schedule final exams for the Registrar so that no student has two exams at the same time.

You will use a greedy algorithm to determine an assignment of classes to exam slots such that:
  * No student is enrolled in two courses assigned to the same exam slot.
  * Any attempt to combine two exam slots into one slot would violate rule 1.
The second requirement ensures that we do not gratuitously waste exam slots (students would like to start their breaks as soon as possible, after all).

The key to doing this assignment is to build a graph as we read in the file of students and their schedules, where:

* Each node of the graph is a course taken by at least one student in the college.
* An edge is drawn between two nodes if there is at least one student taking both courses.
* The label of an edge could be the number of students with both classes (although we don't really need the weights for this program).
