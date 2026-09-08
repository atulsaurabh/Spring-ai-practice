package tech.creative.engineering.mcpserver.db.boot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.creative.engineering.mcpserver.entity.Enrollment;
import tech.creative.engineering.mcpserver.entity.Student;
import tech.creative.engineering.mcpserver.entity.Subject;
import tech.creative.engineering.mcpserver.repository.EnrollmentRepository;
import tech.creative.engineering.mcpserver.repository.StudentRepository;
import tech.creative.engineering.mcpserver.repository.SubjectRepository;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(
            StudentRepository studentRepo,
            SubjectRepository subjectRepo,
            EnrollmentRepository enrollmentRepo) {

        return args -> {
            studentRepo.deleteAll();
            subjectRepo.deleteAll();
            enrollmentRepo.deleteAll();
            // 1. Create Subjects
            Subject math = new Subject("Mathematics", "MATH101");
            Subject physics = new Subject("Physics", "PHYS101");
            Subject chemistry = new Subject("Chemistry", "CHEM101");
            Subject cs = new Subject("Computer Science", "CS101");

            subjectRepo.saveAll(List.of(math, physics, chemistry, cs));

            // 2. Create Students
            Student john = new Student("John Doe", "Stepan Road 3/2 Goa","9","GJ09202615234545");
            Student emma = new Student("Emma Watson", "Park Street 43 lane Kolkata","10","KOL102616321747");
            Student alex = new Student("Alex Rivera", "Whatson Road 3/6 Chaurangi Lane New Delhi","9","DEL092026323834547");
            Student priya = new Student("Priya Sharma", "Denbi Road 35/118 RajaBazar Patna","10","BH102026321747111");

            studentRepo.saveAll(List.of(john, emma, alex, priya));

            // 3. Create Enrollments with Marks & Grades
            LocalDate examDate = LocalDate.now().minusDays(15);

            List<Enrollment> records = List.of(
                    // John's marks
                    createEnrollment(john, math, 88.5, "A", examDate),
                    createEnrollment(john, physics, 74.0, "B", examDate),
                    createEnrollment(john, cs, 92.0, "A+", examDate),

                    // Emma's marks
                    createEnrollment(emma, math, 95.0, "A+", examDate),
                    createEnrollment(emma, chemistry, 89.0, "A", examDate),
                    createEnrollment(emma, cs, 98.0, "A+", examDate),

                    // Alex's marks
                    createEnrollment(alex, physics, 65.5, "C", examDate),
                    createEnrollment(alex, chemistry, 71.0, "B", examDate),
                    createEnrollment(alex, math, 58.0, "D", examDate),

                    // Priya's marks
                    createEnrollment(priya, cs, 96.5, "A+", examDate),
                    createEnrollment(priya, math, 91.0, "A", examDate),
                    createEnrollment(priya, physics, 84.5, "B+", examDate),
                    createEnrollment(priya, chemistry, 88.0, "A", examDate)
            );

            enrollmentRepo.saveAll(records);
            System.out.println("Dummy student, subject, and enrollment data seeded successfully.");
        };
    }

    private Enrollment createEnrollment(Student student, Subject subject, Double marks, String grade, LocalDate date) {
        Enrollment enrollment = new Enrollment(student, subject, marks);
        enrollment.setGrade(grade);
        enrollment.setExamDate(date);
        return enrollment;
    }
}
