package com.company;
import java.io.*;
import  java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Stack;

//создадим несколько исключений
class StackIsEmptyException extends Exception{
}
//если запрещенные операции с нулем
class DivisionByNullException extends ArithmeticException{
}
//попытка ввода несуществующей операции
class InvalidOperationException extends Exception{
}

//создадим непосредственно класс калькулятора
class Calculator //extends CalcStack
{
    CalcStack myStack = new CalcStack();
    Double x1 = 0.0, x2 = 0.0, res = 0.0;
    //метод берет 2 верхних числа из стека, складывает их и возвращает число в стек
    public void plus() {
        x1 = myStack.getNumbFromStack();
        x2 = myStack.getNumbFromStack();
        res = x1 + x2;
        myStack.addNumbToStack(res);
    }
    //минус
    public void minus()
    {
        x1 = myStack.getNumbFromStack();
        x2 = myStack.getNumbFromStack();
        res = x1 - x2;
        myStack.addNumbToStack(res);
    }

    //умножение
    public void multiply()
    {
        x1 = myStack.getNumbFromStack();
        x2 = myStack.getNumbFromStack();
        res = x1 * x2;
        myStack.addNumbToStack(res);
    }
    //деление
    public void division()
    {
        x1 = myStack.getNumbFromStack();
        x2 = myStack.getNumbFromStack();
        res = x2 / x1;
        myStack.addNumbToStack(res);
    }
    //корень
    public void sqrt()
    {
        x1 = myStack.getNumbFromStack();
        res = Math.sqrt(x1);
        myStack.addNumbToStack(res);
    }
    //конец класса
}
//определим отдельно наследуемый класс, содержащий методы относящиеся только с стеку
class CalcStack{
    public Stack<Double> calcStack = new Stack<Double>();
    //методы:
    //положить число в стек
    public void addNumbToStack(double number){
        calcStack.push(number);
    }
    //достать число из стека (удаляет при доставании)
    public Double getNumbFromStack(){
        return calcStack.pop();
    }
    //просмотреть верхнее число, не удаляя его из стека
    public Double LookTopNumb(){
        return calcStack.peek();
    }

    //конец класса
}

//демонстрация работы программы - калькулятор
public class Main {

    public static void main(String[] args) throws Exception, ArithmeticException{
        Scanner scr = new Scanner(System.in);
        System.out.println("Вы собираетесь работать с калькулятором через файл или вручную?" +
                "\n1-Через файл" +
                "\n2-Вручную");
        int fileOrHandle = scr.nextInt();
        BufferedReader reader = null;
        FileReader fr = null;
        if(fileOrHandle == 1) {
            System.out.println("Вы выбрали работу через файл. Пожалуйста, введите имя файла:");
            String path = scr.next();
            try{
                fr = new FileReader(path);
            }
            catch (FileNotFoundException e){
                System.out.println("Ошибка доступа к файлу");
            }
            if(fr != null) {
                reader = new BufferedReader(fr);
            }
            else System.out.println("Path is null");
        }
        Calculator myCalc = new Calculator();
        boolean isRun = true;
        String variable = "";
            while (isRun || (reader.readLine()!=null)) {
                //менюинг
                if(fileOrHandle != 1) {
                    System.out.println("\nВыберите пункт меню:\n" +
                            "1 - Добавить число в стек\n" +
                            "2 - Удалить число из стека\n" +
                            "3 - Показать верхнее число в стеке\n" +
                            "4 - Выполнить арифметическую операцию\n" +
                            "5 - Выход из программы\n" +
                            "Введите пункт меню:\n");
                }
                if (fileOrHandle == 1) {
                    variable = reader.readLine();
                } else {
                    variable = scr.next();
                }
                switch (variable) {
                    //добавление числа в стек для дальнейших вычислений
                    case "1": {
                        Double number;
                        if(fileOrHandle == 1){
                            number = Double.parseDouble(reader.readLine());
                        }
                        else {
                            System.out.println("Введите число: (типа 4,3 или 21)");
                            number = scr.nextDouble();
                        }
                        myCalc.myStack.addNumbToStack(number);
                        break;
                    }
                    case "2": {
                        try{
                            double del = myCalc.myStack.getNumbFromStack();
                        }
                        catch (Exception e){
                            System.out.println("Ошибка пустой стек");
                        }
                        break;
                    }
                    case "3": {
                        try{
                            System.out.println("Top number is:" + myCalc.myStack.LookTopNumb());
                        }
                        catch (Exception e){
                            System.out.println("Ошибка пустой стек");
                        }
                        break;
                    }
                    case "4": {
                        String change;
                        if(fileOrHandle == 1){
                            change = reader.readLine();
                        }
                        else{
                            System.out.println("Введите желаемую операцию: " +
                                    "\n+, -, *, /, sqrt");
                            change = scr.next();
                        }
                        switch (change) {
                            case "+": {
                                try {
                                    myCalc.plus();
                                }
                                catch (Exception e){
                                    System.out.println("Ошибка: недостаточно операндов в стеке");
                                }
                                break;
                            }
                            case "-": {
                                try{
                                myCalc.minus();
                                }
                                catch (Exception e){
                                    System.out.println("Ошибка: недостаточно операндов в стеке");
                                }
                                break;
                            }
                            case "*": {
                                try{
                                myCalc.multiply();
                                }
                                catch (Exception e){
                                    System.out.println("Ошибка: недостаточно операндов в стеке");
                                }
                                break;
                            }
                            case "/": {
                                try{
                                    myCalc.division();
                                    }
                                    catch (Exception e){
                                        System.out.println("Ошибка: недостаточно операндов в стеке");
                                    }
                                    break;
                            }
                            case "sqrt": {
                                try{
                                myCalc.sqrt();
                                }
                                catch (Exception e){
                                    System.out.println("Ошибка: недостаточно операндов в стеке");
                                }
                                break;
                            }
                            default: {
                                System.out.println("Incorrect operation");
                                break;
                            }
                        }
                        break;
                    }
                    case "5": {
                        isRun = false;
                        break;
                    }
                    default: {
                        System.out.println("Incorrect operation");
                        break;
                    }
                    //конец оператора switch
                }
                //конец цикла while
            }
    //конец метода мейн
    }

    //конец класса
}
