package tech.creative.engineering.chatclient.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StudentInfo
{
    private String studentId;
    private String studentName;
    private String enrollmentNumber;
    private String address;
    private String currentClass;
    private List<Enrollment> enrollments;
}

