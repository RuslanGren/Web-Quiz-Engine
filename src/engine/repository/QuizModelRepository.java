package engine.repository;

import engine.models.QuizModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizModelRepository extends JpaRepository<QuizModel, Long> {
}
