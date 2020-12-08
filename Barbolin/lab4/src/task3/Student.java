package com.company;
import java.util.ArrayList;
import java.util.List;

public class Student extends Human {

    public Student(String name, Integer age, String group) {
        super(name, age);
        this.group = group;
    }

    private String group;
    private final List<Course> courses = new ArrayList<>();

    public void signForCourse(String courseName) {
        if (courseName.isBlank())
            throw new RuntimeException("You must declare course name");
        Course.courses.forEach(course -> {
            if (course.getCourseName().equals(courseName)) {
                this.courses.add(course);
                course.getStudents().add(this);
            }
        });
    }


    public List<Course> getCourses() {
        return courses;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", group=" + group +
                '}';
    }
}
