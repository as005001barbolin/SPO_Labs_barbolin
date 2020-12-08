//package com.company;
class Triangle
{
    //инициализировали стороны треугольника. Т.к. он равнобедренный, то две стороны (A и B) равны
    double sideAB, sideC;
    //задали начальные значения
    public Triangle()
{
    this.sideAB = 5;
    this.sideC = 8;
}
    public Triangle(double sideAB, double sideC)
    {
        this.sideAB = sideAB;
        this.sideC = sideC;
    }
    //задаем и получаем стороны AB
    public void setSideAB(double sideAB)
    {
        this.sideAB = sideAB;
    }
    public double getSideAB()
    {
        return this.sideAB;
    }
    //задаем и получаем стороны C
    public void setSideC(double sideC)
    {
        this.sideC = sideC;
    }
    public double getSideC()
    {
        return this.sideC;
    }
    //нахождение площади и периметра, варианты с заданными сторонами или сразу по треугольнику
    public double perimeter()
    {
        return ((sideAB * 2) + sideC);
    }
    public double perimeter(double sideAB, double sideC)
    {
        return ((sideAB * 2) + sideC);
    }
    //площадь = 1/2 основания * высота. высота = корень из (гипотенуза^2 + катет^2)
    public double square()
    {
        return (sideC * Math.sqrt(Math.pow(sideAB, 2) - Math.pow((sideC / 2), 2)) / 2);
    }
    public double square(double sideAB, double sideC)
    {
        return (sideC * Math.sqrt(Math.pow(sideAB, 2) - Math.pow((sideC / 2), 2)) / 2);
    }
    //проверка существования
    public boolean isCanBe()
    {
        return (sideAB * 2 >= sideC && sideAB + sideC >= sideAB);
    }
    //проверка идентичности
    public boolean isIdentity(Triangle triangle)
    {
        return (sideAB == triangle.sideAB && sideC == triangle.sideC);
    }
    //вывод информации по сторонам треугольника
    public String showParams()
    {
        return ("Sides of triangle are:\nA = "+sideAB+"\nB = "+sideAB+"\nC = "+sideC+"\n");
    }
}
//демонстрация использования класса треугольник
public class task1 {
    public static void main(String[] args) {
        Triangle trial_triangle_1 = new Triangle();
        Triangle trial_triangle_2 = new Triangle(3,4);
        System.out.println(trial_triangle_1.showParams());
        double changeSideC = 9;
        trial_triangle_1.setSideC(changeSideC);
        System.out.println("Let's see the new side in tr1: "+trial_triangle_1.showParams());
        System.out.println("perimeter "+trial_triangle_1.perimeter());
        System.out.println("square "+trial_triangle_2.square());
        System.out.println("Is trial_triangle_1 exist: " + trial_triangle_1.isCanBe());
        System.out.println("Are the triangles equal each other: "+trial_triangle_1.isIdentity(trial_triangle_2));
        System.out.println("Try to use perimeter method by another way: "+ trial_triangle_2.perimeter(4,6));
    }
}
