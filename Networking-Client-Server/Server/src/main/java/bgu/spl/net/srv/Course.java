package bgu.spl.net.srv;

import java.util.Collections;
import java.util.Vector;

public class Course {


    private Integer course_index;
    private String cousre_name;
    private Vector<Integer> kdam_courses;
    private Vector<String> students_registered;
    private int max_student;

    public Course(Integer course_index, String course_name, Integer[] kdam_courses, int max_student) {
        this.course_index = course_index;
        this.cousre_name = course_name;
        this.kdam_courses = new Vector<Integer>();
        Collections.addAll(this.kdam_courses, kdam_courses);
        this.students_registered = new Vector<String>();
        this.max_student = max_student;

    }

    public boolean IsFull() {
        if (max_student == students_registered.size() ) {
            return true;
        } else {
            return false;
        }
    }

    public Vector<Integer> getKdam_courses() {
        return kdam_courses;
    }

    public String getCousre_name() {
        return cousre_name;
    }
    public int getNumOfAvailableSeats()
    {
        return max_student-students_registered.size();
    }

    public int getMax_student() {
        return max_student;
    }

    public boolean isRegistered(String username) {
        if (students_registered.contains(username)) {
            return true;
        } else
            return false;

    }
    public String RegisteredStudentsString()
    {
            Collections.sort(students_registered);
          return  students_registered.toString();

    }

    public void unregister(String username)
    {
        students_registered.remove(username);
    }


    public void register(String username) {
        students_registered.add(username);
    }


}
