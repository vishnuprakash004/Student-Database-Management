package com.vishnu.studentdb;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public int add(Student s) throws SQLException {
        String sql = "INSERT INTO students (first_name, last_name, dob, gender, contact_number, email)"+"VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setDate(3, Date.valueOf(s.getDob()));
            ps.setString(4, s.getGender());
            ps.setString(5, s.getContactNumber());
            ps.setString(6, s.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<Student> findAll() throws SQLException {
        String sql = "SELECT student_id, first_name, last_name, dob, gender, contact_number, email FROM students ORDER BY student_id";
        List<Student> list = new ArrayList<>();
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT student_id, first_name, last_name, dob, gender, contact_number, email FROM students WHERE student_id = ?";
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public boolean update(Student s) throws SQLException {
        String sql = "UPDATE students SET first_name=?, last_name=?, dob=?, gender=?, contact_number=?, email=?"+
            "WHERE student_id=?";
            
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setDate(3, Date.valueOf(s.getDob()));
            ps.setString(4, s.getGender());
            ps.setString(5, s.getContactNumber());
            ps.setString(6, s.getEmail());
            ps.setInt(7, s.getStudentId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection c = Database.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Student map(ResultSet rs) throws SQLException {
        int id = rs.getInt("student_id");
        String fn = rs.getString("first_name");
        String ln = rs.getString("last_name");
        LocalDate dob = rs.getDate("dob").toLocalDate();
        String gender = rs.getString("gender");
        String contact = rs.getString("contact_number");
        String email = rs.getString("email");
        return new Student(id, fn, ln, dob, gender, contact, email);
    }

	
}
