package org.create.fitnessplanner.repository;

import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUserUsernameOrderByDateDesc(String username);

    @Query("SELECT w FROM Workout w JOIN FETCH w.workoutType WHERE w.user = :user AND w.date < :date ORDER BY w.date")
    List<Workout> findPastWorkoutsWithType(@Param("user") User user, @Param("date") LocalDateTime date);

    @Query("SELECT wt.name, COUNT(w) FROM Workout w JOIN w.workoutType wt WHERE w.user.username = :username GROUP BY wt.name")
    List<Object[]> countWorkoutTypesByUsername(@Param("username") String username);

}
