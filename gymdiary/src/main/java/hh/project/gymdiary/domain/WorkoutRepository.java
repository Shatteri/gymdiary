package hh.project.gymdiary.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    List<Workout> findByExercise(String exercise);
    Iterable<Workout> findAll();
}
