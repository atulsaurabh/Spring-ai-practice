package tech.creative.engineering.mcpserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SUBJECT")
@Getter
@Setter
@NoArgsConstructor
public class Subject
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBJECT_ID")
    private Long subjectId;
    @Column(name = "SUBJECT_NAME")
    private String subjectName;
    private String subjectCode;

    public Subject(String subjectName, String subjectCode) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

}
