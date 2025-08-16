package com.vishnu.studentdb;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        System.out.println("=== Student Database Management (Console + MySQL) ===");
        while (true) {
            System.out.println("\nStudents Menu:");
            System.out.println("1) Add Student");
            System.out.println("2) View All Students");
            System.out.println("3) Find Student by ID");
            System.out.println("4) Update Student");
            System.out.println("5) Delete Student");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String ch = in.nextLine().trim();

            try {
                switch (ch) {
                    case "1":
                        addStudent();
                        break;
                    case "2":
                        listStudents();
                        break;
                    case "3":
                        findStudent();
                        break;
                    case "4":
                        updateStudent();
                        break;
                    case "5":
                        deleteStudent();
                        break;
                    case "0":
                        System.out.println("Bye!");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addStudent() throws Exception {
        System.out.print("First name: ");
        String fn = in.nextLine().trim();
        System.out.print("Last name: ");
        String ln = in.nextLine().trim();
        System.out.print("DOB (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(in.nextLine().trim());
        System.out.print("Gender (Male/Female/Other): ");
        String gender = in.nextLine().trim();
        System.out.print("Contact number: ");
        String contact = in.nextLine().trim();
        System.out.print("Email: ");
        String email = in.nextLine().trim();

        Student s = new Student(fn, ln, dob, gender, contact, email);
        int id = studentDAO.add(s);
        System.out.println(id > 0 ? "Inserted student_id = " + id : "Insert failed.");
    }

    private static void listStudents() throws Exception {
        List<Student> list = studentDAO.findAll();
        if (list.isEmpty()) { System.out.println("No students yet."); return; }
        System.out.printf("%-4s %-12s %-12s %-12s %-8s %-15s %-25s%n",
                "ID","FirstName","LastName","DOB","Gender","Contact","Email");
        for (Student s : list) {
            System.out.printf("%-4d %-12s %-12s %-12s %-8s %-15s %-25s%n",
                    s.getStudentId(), s.getFirstName(), s.getLastName(),
                    s.getDob(), s.getGender(), s.getContactNumber(), s.getEmail());
        }
    }

    private static void findStudent() throws Exception {
        System.out.print("student_id: ");
        int id = Integer.parseInt(in.nextLine().trim());
        Student s = studentDAO.findById(id);
        if (s == null) { System.out.println("Not found."); return; }
        System.out.println("ID: " + s.getStudentId());
        System.out.println("Name: " + s.getFirstName() + " " + s.getLastName());
        System.out.println("DOB: " + s.getDob());
        System.out.println("Gender: " + s.getGender());
        System.out.println("Contact: " + s.getContactNumber());
        System.out.println("Email: " + s.getEmail());
    }

    private static void updateStudent() throws Exception {
        System.out.print("student_id to update: ");
        int id = Integer.parseInt(in.nextLine().trim());
        Student existing = studentDAO.findById(id);
        if (existing == null) { System.out.println("Not found."); return; }

        System.out.print("First name (" + existing.getFirstName() + "): ");
        String fn = orDefault(in.nextLine(), existing.getFirstName());
        System.out.print("Last name (" + existing.getLastName() + "): ");
        String ln = orDefault(in.nextLine(), existing.getLastName());
        System.out.print("DOB YYYY-MM-DD (" + existing.getDob() + "): ");
        String dobStr = in.nextLine().trim();
        LocalDate dob = dobStr.isEmpty() ? existing.getDob() : LocalDate.parse(dobStr);
        System.out.print("Gender (" + existing.getGender() + "): ");
        String gender = orDefault(in.nextLine(), existing.getGender());
        System.out.print("Contact (" + existing.getContactNumber() + "): ");
        String contact = orDefault(in.nextLine(), existing.getContactNumber());
        System.out.print("Email (" + existing.getEmail() + "): ");
        String email = orDefault(in.nextLine(), existing.getEmail());

        existing.setFirstName(fn);
        existing.setLastName(ln);
        existing.setDob(dob);
        existing.setGender(gender);
        existing.setContactNumber(contact);
        existing.setEmail(email);

        System.out.println(studentDAO.update(existing) ? "Updated." : "No change.");
    }

    private static void deleteStudent() throws Exception {
        System.out.print("student_id to delete: ");
        int id = Integer.parseInt(in.nextLine().trim());
        System.out.println(studentDAO.delete(id) ? "Deleted." : "Not found.");
    }

    private static String orDefault(String input, String def) {
        String t = input == null ? "" : input.trim();
        return t.isEmpty() ? def : t;
    }
}
