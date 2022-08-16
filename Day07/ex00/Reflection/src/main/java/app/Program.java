package app;

import classes.Car;
import classes.User;

import java.lang.reflect.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Object[] list = new Object[]{new Car(), new User()};
        Scanner scanner = new Scanner(System.in);

        Object q = new User();
        System.out.println(q.getClass().getConstructors()[1].getParameters()[0].getName());
        System.out.println("Classes:");
        for (Object o : list) {
            System.out.println("\t -" + o.getClass().getSimpleName());
        }
        System.out.println("----------------------------\nEnter class name:");
        try {
            makeReflection(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void makeReflection(String str) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Class<?> object = Class.forName("classes." + str);
        System.out.println("----------------------------");
        findFieldsAndMethods(object);

        //TODO
    }

    public static void findFieldsAndMethods(Class<?> o) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Field[] fields = o.getDeclaredFields();
        Method[] methods = o.getDeclaredMethods();

        if (fields.length != 0) {
            System.out.println("fields:");
            for (Field f : fields) {
                System.out.println("\t" + f.getType().getSimpleName() + "\t" + f.getName());
            }
        }
        if (methods.length != 0) {
            System.out.println("methods:");
            for (Method m : methods) {
                System.out.println("\t" + m.getReturnType().getSimpleName() + "\t" + m.getName() + "(" + getFields(m.getParameterTypes()) + ")");
            }
        }
        System.out.println("----------------------------\nLet’s create an object.");
        Object object = createObject(o);
        changeObject(object);
        invokeMethod(object);
    }

    public static void invokeMethod(Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        Method m = null;

        System.out.println("---------------------");
        for (Method q : o.getClass().getDeclaredMethods()) {
            System.out.println(" " + q.getName());
        }
        System.out.println("---------------------\nEnter name of the method for call:");
        while (true) {
            try {
               String str = scanner.nextLine();
               for (Method h : o.getClass().getDeclaredMethods())
               {
                   if (h.getName().equals(str))
                   {
                       m = h;
                       break;
                   }
               }
               if (m != null)
                   break;
               throw new NoSuchMethodException();
            } catch (NoSuchMethodException e) {
                System.out.println("No such method! Try again!");
            }
        }
        Object[] objects = new Object[m.getParameters().length];
        int i = 0;

        for (Parameter p : m.getParameters())
        {
            System.out.println("Enter " + p.getType().getSimpleName() + " value");
            objects[i++] = (getType(scanner, p.getType().getSimpleName().toLowerCase()));
        }
        m.invoke(o, objects);
        System.out.println(o);
    }


    public static void changeObject(Object o) throws NoSuchFieldException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        Field field = null;
        System.out.println("---------------------\nEnter name of the field for changing:");
        String str = scanner.nextLine();
        field = o.getClass().getDeclaredField(str);
        System.out.println("Enter " + field.getType().getSimpleName() + " value:");
        field.setAccessible(true);
        field.set(o, getType(scanner, field.getType().getSimpleName().toLowerCase()));
        field.setAccessible(false);
        System.out.println("Object updated: " + o);
    }

    public static Object createObject(Class<?> o) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] c = o.getDeclaredConstructors();
        Parameter[] parameters;
        Scanner scanner = new Scanner(System.in);

        int i = 0, f = 0;
        while (c[i].getParameterCount() == 0)
            i++;
        Object[] objects = new Object[c[i].getParameterCount()];
        for (Parameter p : c[i].getParameters()) {
            System.out.println(p.getName() + "(" + p.getType().getSimpleName() + "):");
            objects[f++] = getType(scanner, p.getType().getSimpleName().toLowerCase());
        }
        Object s = c[i].newInstance(objects);
        System.out.println("Object created: " + s);
        return s;
    }

    private static Object getType(Scanner scan, String type) {
        switch (type) {
            case "string":
                return scan.nextLine();
            case "int":
            case "integer":
                return scan.nextInt();
            case "double":
                return scan.nextDouble();
            case "long":
                return scan.nextLong();
            case "boolean":
            case "bool":
                return scan.nextBoolean();
            case "char":
                return scan.next();
            default:
                throw new RuntimeException("Unrecognized type");
        }
    }

    public static String getFields(Class<?>[] parameterTypes) {
        StringBuilder str = new StringBuilder();

        if (parameterTypes.length == 0)
            return "";
        for (int i = 0; i < parameterTypes.length - 1; i++) {
            str.append(parameterTypes[i].getSimpleName());
            str.append(",");
        }
        str.append(parameterTypes[parameterTypes.length - 1].getSimpleName());
        return str.toString();
    }
}
