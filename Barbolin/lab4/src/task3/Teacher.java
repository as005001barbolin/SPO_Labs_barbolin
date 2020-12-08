package com.company;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends Human {
    protected Teacher(String name, Integer age, String degree) {
        super(name, age);
        this.degree = degree;
    }

    private String degree;
    private final List<Course> courses = new ArrayList<>();

    public Course createCourse(String courseName) {
        if (courseName.isBlank())
            throw new RuntimeException("You must declare course name");
        else {
            Course course = new Course(courseName, this);
            courses.add(course);
            return course;
        }
    }

    public void addTeacher(Teacher teacher, Course course) {
        if(course.getTeachers().contains(this))
        course.getTeachers().add(teacher);
    }

    public void finishCourseAndGiveMarks(Course course) {
        course.getStudents().forEach(student -> {
            course.getArchive().put(student, 7);  // 7 - it can be substituted for anything
        });
    }

    public List<Course> getCourses() {
        return courses;
    }


    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", degree='" + degree + '\'' +
                ", age=" + age +
                '}';
    }
}
