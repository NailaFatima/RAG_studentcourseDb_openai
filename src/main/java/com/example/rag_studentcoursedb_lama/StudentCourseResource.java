package com.example.rag_studentcoursedb_lama;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentCourseResource {
    private final OpenAiChatModel openAiChatModel;
    private final JdbcTemplate jdbcTemplate;
    public StudentCourseResource(OpenAiChatModel openAiChatModel, JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.openAiChatModel = openAiChatModel;
    }
    public String processQuestion(String question) {
        // Logic to process the question using openaichatmodel and jdbcTemplate
        String prompt = """
                You have access to a database with tables:
                            Students(student_id, first_name, last_name, email, date_of_birth)
                            Courses(course_id,course_code, course_name, course_description, credits name)
                            enrollments(enrollment_id, student_id, course_id, enrollment_date)
                                
                            User question: "%s"
                                
                            1. Return only a valid SQL SELECT query based on the question.
                            2. For complex queries, use JOINs to combine tables as needed.
                            3. For queries involving dates, ensure proper date formatting.
                            4. For queries requiring aggregations, use appropriate SQL functions (e.g., COUNT, AVG).
                            5. For queries requiring calculation, use chain of thoughts, break down the question into smaller tasks, understand them, and then construct the overall query, use SQL arithmetic operations as applicable.
                            DO NOT explain anything, only raw SQL. No markdown, no comments.
                        """.formatted(question);
        ChatResponse response = openAiChatModel.call(new Prompt(prompt));
        String sql = cleanSql(response.getResult().getOutput().getContent());
        System.out.println("Generated SQL:"+sql);
        if (!sql.trim().toLowerCase().startsWith("select")) {
            throw new IllegalArgumentException("Only SELECT queries are allowed.");
        }

        return executeQueryAndBuildHtml(sql);
    }
    private String cleanSql(String raw) {
        return raw.replaceAll("```sql", "")
                .replaceAll("```", "")
                .trim();
    }

    private String executeQueryAndBuildHtml(String sql) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        if (rows.isEmpty()) {
            return "<p>No results found.</p>";
        }

        StringBuilder html = new StringBuilder("<table class='table-auto' border='1'><tr>");
        for (String column : rows.get(0).keySet()) {
            html.append("<th >").append(column).append("</th>");
        }
        html.append("</tr>");
        for (Map<String, Object> row : rows) {
            html.append("<tr >");
            for (Object value : row.values()) {
                html.append("<td>").append(value != null ? value.toString() : "").append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }

}
