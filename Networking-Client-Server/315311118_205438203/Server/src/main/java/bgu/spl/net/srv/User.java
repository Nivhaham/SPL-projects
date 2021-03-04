package bgu.spl.net.srv;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {
    private String username;
    private String password;
    private Vector<Integer> registerd_courses;
    private AtomicBoolean admin= new AtomicBoolean();
    private AtomicBoolean login=new AtomicBoolean();



    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        registerd_courses = new Vector<>();
        this.admin.set(admin);
        this.login.set(false);
    }

    public String getUsername() {
        return username;
    }

    /*public void setUsername(String username) {
        this.username = username;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Vector<Integer> getRegisterd_courses() {
        return registerd_courses;
    }

    public String getCoursesString() {
        return registerd_courses.toString().replaceAll(" ", "");
    }

    public void setRegisterd_courses(Vector<Integer> registerd_courses) {
        this.registerd_courses = registerd_courses;
    }

    public void add_courses(int course_index) {
        if (!registerd_courses.contains(course_index))
            registerd_courses.add(course_index);
    }

    public boolean gotKdams(Vector<Integer> kdams) {
        if(kdams==null) {
            return true;
        }
        for (Integer x : kdams) {
            if (!registerd_courses.contains(x)) {
                return false;
            }
        }
        return true;

    }
    public void UnregisterToCourse(int course_index){
        if(registerd_courses.contains(course_index))
            registerd_courses.remove((Integer) course_index);
     //   registerd_courses.remove(course_index);
    }

    public boolean isAdmin() {
        return admin.get();
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }

    public boolean isLogin() {
        return this.login.get();
    }

    public void setLogin(boolean login) {
        this.login.set(login);;
    }
}
