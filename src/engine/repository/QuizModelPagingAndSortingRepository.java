package engine.repository;

import engine.models.quiz.QuizModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizModelPagingAndSortingRepository extends PagingAndSortingRepository<QuizModel, Long> {
    Page<QuizModel> findAll(Pageable pageable);
}
