package tech.creative.engineering.mcpserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.creative.engineering.mcpserver.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
