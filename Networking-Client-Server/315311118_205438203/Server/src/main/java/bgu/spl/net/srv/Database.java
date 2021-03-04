package bgu.spl.net.srv;

import bgu.spl.net.impl.newsfeed.PublishNewsCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.Stream;
import java.util.concurrent.*;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

    private ConcurrentHashMap<String, User> users;
    private ConcurrentHashMap<Integer, Course> courses;
    private Object lock = new Object();

    private static class SingletonHolder {

        private static Database singleton = new Database();

        }


    //to prevent user from creating new Database
    private Database()  {
        courses = new ConcurrentHashMap<Integer, Course>();
        users = new ConcurrentHashMap<String, User>();
        initialize("./Courses.txt");
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return SingletonHolder.singleton;
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath)  {
        String[] course;
            File file_path = new File(coursesFilePath);

        Scanner read_file = null;
        try {
            read_file = new Scanner(file_path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        while (read_file.hasNextLine()) {
                String line = read_file.nextLine();
                course = line.split("\\|");
                int[] kdam = new int[0];
                if (!course[2].equals("[]")) {
                    String kdamcourses = course[2].replaceAll("\\[", "").replaceAll("\\]", "");
                    kdam = Stream.of(kdamcourses.split("\\,")).mapToInt(Integer::parseInt).toArray();
                }
                // put the courses in the HashMap
                courses.put(Integer.parseInt(course[0]), new Course(Integer.parseInt(course[0]), course[1], Arrays.stream(kdam).boxed().toArray(Integer[]::new), Integer.parseInt(course[3])));

            }
       return true;

        }



    // OP code 1
    public boolean admin_reg(String username, String password) {

        // error if registered already
        // send back ack if success
        synchronized (lock) {
            if (users.containsKey(username))
                return false;
            users.put(username, new User(username, password, true));
            return true;
        }
    }

    // OP code 2
    public boolean student_reg(String username, String password) {
        // error if registered already
        // send back ack if success
        synchronized (lock) {
            if (users.containsKey(username)) {
                return false;
            }
            users.put(username, new User(username, password, false));
            return true;
        }

    }

    // OP code 3
    public User user_login(String username, String password) {
        // need to send error message back to the client if username doesnt exist\password is incorrect\already logged in

        if (users.containsKey(username) == false) {
            System.out.println("username doesn't exist");
            return null;
        }
        User loginUser = users.get(username);

        if (!loginUser.getPassword().equals(password)) {
            System.out.println("wrong password");
            return null;
        }

        if (loginUser.isLogin() == true) {
            System.out.println("already logged in");
            return null;
        }
        loginUser.setLogin(true);
        return loginUser;
    }

    // OP code 4
    public boolean user_logout() {
        // client send a logout message
        // need to send back ack to client
        // if the user is not even logged in send error
        return true;
    }

    // OP code 6
    public Vector<Integer> get_kdam_courses_of_course(Integer course_index) {
        if (courses.containsKey(course_index))
            return courses.get(course_index).getKdam_courses();
        return null;
    }

    // OP code 5
    public boolean course_reg(Integer course_index, String username) {

        Course x = courses.get(course_index);
        if (!x.isRegistered(username)) {
            x.register(username);
            users.get(username).add_courses(course_index);
            return true;
        } else
            return false;

    }

    // OP code 7
    public String get_course_stat(Integer course_index) {
        if (!courses.containsKey(course_index)) {
            return null;
        }

        Course current = courses.get(course_index);
        String ans = "Course: (" + course_index.toString() + ") " + current.getCousre_name() + "\n";
        ans += "Seats Available:" + current.getNumOfAvailableSeats() + "/" + current.getMax_student() + "\n";
        ans += "Students Registered " + current.RegisteredStudentsString();
        return ans;


    }

    // OP code 8
    public Vector<Integer> get_student_stat(String username) {
        // need to check that the user that send this command is admin.

        return users.get(username).getRegisterd_courses();
    }

    // OP code 9
    public String is_registered(Integer course_index, String username) {

        if (courses.get(course_index).isRegistered(username))
            return "REGISTERED";
        else
            return "NOT REGISTERED";
    }

    // OP code 10
    public boolean unregister(Integer course_index, String username) {
        synchronized (lock) {

            if (courses.get(course_index).isRegistered(username)) {
                users.get(username).UnregisterToCourse(course_index);
                courses.get(course_index).unregister(username);

                return true;
            }

            return false;
        }
    }


    public String GetStudentStat(String Username) {
        if (!users.containsKey(Username)) {
            return null;
        }

        User user = users.get(Username);

        String stat = "Student:"+Username+"\n";
        stat += "Courses:" + user.getCoursesString().replaceAll(" ", "");
        return stat;

    }

    public boolean isExist(Integer CourseNum)
    {
        return courses.containsKey(CourseNum);
    }


}
