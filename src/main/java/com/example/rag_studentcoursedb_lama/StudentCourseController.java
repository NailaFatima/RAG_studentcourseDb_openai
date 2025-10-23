package com.example.rag_studentcoursedb_lama;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class StudentCourseController {
    private final StudentCourseResource studentCourseResource;
    public StudentCourseController(StudentCourseResource studentCourseResource) {
        this.studentCourseResource = studentCourseResource;
    }
    @GetMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestParam String question) {
        System.out.println("Question:"+question);
        String htmlResponse = studentCourseResource.processQuestion(question);
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlResponse);
    }
}
