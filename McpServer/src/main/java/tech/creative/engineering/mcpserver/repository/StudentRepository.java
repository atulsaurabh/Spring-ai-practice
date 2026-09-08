package tech.creative.engineering.mcpserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.creative.engineering.mcpserver.entity.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
    Optional<Student> findByEnrollmentNumber(String enrollmentNumber);
}
