package com.example.rag_studentcoursedb_lama;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDb {
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_course_db?useSSL=false",
                "root", "root"
        );
        System.out.println("Connected successfully!");
    }
}

