package tech.creative.engineering.mcpserver.tool;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;
import tech.creative.engineering.mcpserver.entity.Student;
import tech.creative.engineering.mcpserver.repository.StudentRepository;

import java.util.Optional;

@Component
public class DbTools
{
    private final StudentRepository studentRepository;

    public DbTools(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @McpTool(name = "studentFinder",description = "Find student by enrollment number")
    public Student findStudentByEnrollmentNumber(@McpToolParam(description = "Enrollment number of the student", required = false) String enrollmentNumber)
    {
        Optional<Student> optionalStudent=studentRepository.findByEnrollmentNumber(enrollmentNumber);
        if (optionalStudent.isPresent())
        {
            return optionalStudent.get();
        }
        else {
            throw new IllegalArgumentException("Student not found with enrollment number: " + enrollmentNumber);
        }
    }
}
