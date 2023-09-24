package engine.repository;

import engine.models.quiz.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long>, PagingAndSortingRepository<CompletedQuiz, Long> {
    @Query(value = "SELECT * FROM completed_quiz WHERE user_id = :user_id",
    countQuery = "SELECT count(*) FROM users",
    nativeQuery = true)
    Page<CompletedQuiz> findCompletedQuizByUserId(@Param("user_id") Long userId, Pageable pageable);
}
