package com.company;
public abstract class Human {
    protected String name;
    protected Integer age;
    //класс, для расширения студентом и преподавателем. содержит ФИО и возраст
    protected Human(String name, Integer age)
    {
        if (name.isBlank()|| age < 0)
            throw new RuntimeException("You must declare both name and age");
        this.name = name;
        this.age = age;
    }
}
