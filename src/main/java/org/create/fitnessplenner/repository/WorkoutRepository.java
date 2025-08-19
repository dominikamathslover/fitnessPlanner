package org.create.fitnessplenner.repository;

import org.create.fitnessplenner.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Query("SELECT w FROM Workout w JOIN FETCH w.workoutType WHERE w.user.username = :username")
    List<Workout> findWorkoutsWithTypeByUsername(@Param("username") String username);

}
