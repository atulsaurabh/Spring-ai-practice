package tech.creative.engineering.mcpserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STUDENT")
@Setter
@Getter
@NoArgsConstructor
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "STUDENT_NAME")
    private String studentName;
    private String studentAddress;
    private String currentClass;
    @Column(name = "ENROLLMENT_NUMBER",unique = true)
    private String enrollmentNumber;

    public Student(String studentName, String studentAddress, String currentClass, String enrollmentNumber) {
        this.studentName = studentName;
        this.studentAddress = studentAddress;
        this.currentClass = currentClass;
        this.enrollmentNumber = enrollmentNumber;
    }



    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Enrollment> enrollments=new ArrayList<>();

    public void addSubjectWithMarks(Subject subject, Double marks)
    {
        Enrollment enrollment = new Enrollment();
        enrollment.setMarkObtained(marks);
        enrollment.setSubject(subject);
        enrollment.setStudent(this);
        enrollments.add(enrollment);
    }
}
