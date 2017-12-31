package student.streams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Main {

    private static Collection<Employee> emps = createEmployees();

    private static Collection<Employee> createEmployees() {
        Collection<Employee> emps = new ArrayList<>();
        emps.add(new Employee("Peter Smith", "London", 25000));
        emps.add(new Employee("Johan Mitra", "Berlin", 21000));
        emps.add(new Employee("Diane Evans", "London", 32000));
        emps.add(new Employee("Meera Jones", "Geneva", 2500000));
        emps.add(new Employee("Gerry Lomax", "London", 7000));
        emps.add(new Employee("Steff Holby", "Berlin", 55000));
        emps.add(new Employee("Franz Elsom", "Berlin", 75000));
        emps.add(new Employee("Simon Peter", "Geneva", 150000));
        return emps;
    }


    public static void main(String... args) {

        displayEmployeeFullDetails();

        displayEmployeeNames();

        displayWageBill();

        displaySortedDistinctOffices();

        displayFilteredEmployees("Berlin Employees", e -> e.getOffice().equals("Berlin"));

        displayFilteredEmployees("High Paid Employees", e -> e.getSalary() >= 55000);

        displayFilteredEmployees("High Paid Employees", e -> e.getSalary() >= 55000 && e.getOffice().equals("Berlin"));

        displaySalaryStats();

        displaySalaryTests("Berlin");

        // TODO: Call other functions here...

    }

    private static void displayEmployeeFullDetails() {
        try (Stream<Employee> stream = emps.stream()) {
            System.out.println("\nFull details of all employees:");
            stream.forEach(Employee::display);
        }
    }

    private static void displayEmployeeNames() {
        try (Stream<Employee> stream = emps.stream()) {
            System.out.println("\nNames of all employees");
            stream.map(employee -> employee.getName()).forEach(System.out::println);
        }
    }

    private static void displayWageBill() {
        try (Stream<Employee> stream = emps.stream()) {
            System.out.println("\nTotall of the wages");
            System.out.println(stream.mapToDouble(Employee::getSalary).sum());
        }
        // TODO
    }

    private static void displaySortedDistinctOffices() {
        try (Stream<Employee> stream = emps.stream()) {
            System.out.println("\nSorted offices");
            stream.map(e -> e.getOffice()).distinct().sorted().forEach(System.out::println);
        }
    }

    private static void displayFilteredEmployees(String description, Predicate<Employee> predicate) {

        try (Stream<Employee> stream = emps.stream()) {
            System.out.println("\n" + description);
            stream.filter(predicate).forEach(System.out::println);
        }


    }

    private static void displaySalaryStats() {
        System.out.println("\nMin Salary of all employees: " + emps.stream().mapToDouble(e -> e.getSalary()).min().getAsDouble());
        System.out.println("Max Salary of all employees: " + emps.stream().mapToDouble(e -> e.getSalary()).max().getAsDouble());
        System.out.println("Average Salary of all employees: " + emps.stream().mapToDouble(e -> e.getSalary()).average().getAsDouble());

        System.out.println("\nTop 3 by wage");
        emps.stream().sorted().limit(3).forEach(System.out::println);

        System.out.println("\nTop 3 by name");
        emps.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .limit(3)
                .forEach(System.out::println);

    }

    private static void displaySalaryTests(String city) {

        Boolean minWage = emps.stream().allMatch(employee -> employee.getSalary() >= 7000);
        Boolean earnTooMuch = emps.stream().allMatch(employee -> employee.getSalary() >= 1000000);

        System.out.println(minWage ? "\nMaking min wage" : "\nNot making min wage");
        System.out.println(earnTooMuch ? "\nMillionares work here" : "\nMillionares not here");

        Optional<Employee> firstInCity = emps.stream().filter(employee -> employee.getOffice().equals("Berlin")).findFirst();
       if(firstInCity.isPresent()){
           System.out.printf("First employee in %s: %s\n", city, firstInCity.get());
       }
       else {
           System.out.printf("No employees in %s\n", city);
       }


    }
}
