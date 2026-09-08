package tech.creative.engineering.mcpserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "ENROLLMENT",
        uniqueConstraints = @UniqueConstraint(name = "UNIQUE_ENROLLMENT", columnNames = {"STUDENT_ID", "SUBJECT_ID"})
)
@NoArgsConstructor
public class Enrollment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENROLLMENT_ID")
    private Long enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STUDENT_ID", nullable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SUBJECT_ID", nullable = false)
    @JsonIgnore
    private Subject subject;
    private LocalDate examDate;

    public Enrollment(Student student, Subject subject, double markObtained) {
        this.student = student;
        this.subject = subject;
        this.markObtained = markObtained;
    }

    private double markObtained;
    private String grade;
}
