package com.company;
//изображение результатов
public class Main {

    public static void main(String[] args) {
        Teacher teacher = new Teacher("Adamov I.V", 56, "Bakalavr");
        Teacher teacher1 = new Teacher("Maxov A.D", 28, "Doctor");
        Course course = teacher.createCourse("course3");
        Course course1 = teacher1.createCourse("course4");
        Student student = new Student("Barbolin M.O.", 20, "AS50");
        Student student1 = new Student("Klinistkaya R.P", 20, "AS50");
        student.signForCourse("course3");
        System.out.println(Course.getCourses());
        System.out.println(student);
        System.out.println(teacher.getCourses());
        System.out.println(teacher);
        student1.signForCourse("course4");
        System.out.println(course.getStudents());
        student1.signForCourse("course3");
        System.out.println(student1.getCourses());
        teacher.addTeacher(teacher1, course);
        System.out.println(course.getTeachers());
        teacher.finishCourseAndGiveMarks(course);
        System.out.println(course.getArchive());

    }
}
