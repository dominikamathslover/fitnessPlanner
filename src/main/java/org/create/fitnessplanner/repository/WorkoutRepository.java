package org.create.fitnessplanner.repository;

import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Page<Workout> findByUserUsernameOrderByDateDesc(String username, Pageable pageable);


    @Query("SELECT w FROM Workout w JOIN FETCH w.workoutType WHERE w.user = :user AND w.date < CURRENT_TIMESTAMP ORDER BY w.date")
    List<Workout> findPastWorkoutsWithType(@Param("user") User user);

    @Query("SELECT wt.name, COUNT(w) FROM Workout w JOIN w.workoutType wt WHERE w.user.id = :userId GROUP BY wt.name")
    List<Object[]> countWorkoutTypesByUserId(@Param("userId") Long userId);

    @Query("SELECT wt.name, COUNT(w) FROM Workout w JOIN w.workoutType wt WHERE w.user.id = :userId AND w.date < CURRENT_TIMESTAMP GROUP BY wt.name")
    List<Object[]> countPastWorkoutTypesByUserId(@Param("userId") Long userId);

    @Query("SELECT wt.name, COUNT(w) FROM Workout w JOIN w.workoutType wt WHERE w.user.id = :userId AND w.date > CURRENT_TIMESTAMP GROUP BY wt.name")
    List<Object[]> countFutureWorkoutTypesByUserId(@Param("userId") Long userId);

    List<Workout> findByUserAndDateAfter(User user, LocalDateTime start);

    @Query("SELECT w FROM Workout w JOIN FETCH w.workoutType WHERE w.user = :user AND w.date > CURRENT_TIMESTAMP ORDER BY w.date")
    List<Workout> findFutureWorkoutsWithType(@Param("user") User user);

    @Query("SELECT w FROM Workout w JOIN FETCH w.workoutType WHERE w.user.id = :userId AND w.date BETWEEN :start AND :end")
    List<Workout> findWorkoutsWithTypeBetweenDatesByUserId(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT wt.name, COUNT(w) FROM Workout w JOIN w.workoutType wt WHERE w.user.id = :userId " +
            "AND w.date BETWEEN :startOfMonth AND :now GROUP BY wt.name")
    List<Object[]> countPastWorkoutThisMonth(
            @Param("userId") Long userId,
            @Param("startOfMonth") LocalDateTime startOfMonth,
            @Param("now") LocalDateTime now);

    @Query("SELECT wt.name, COUNT(w) FROM Workout w JOIN w.workoutType wt WHERE w.user.id = :userId " +
            "AND w.date BETWEEN :now AND :endOfMonth GROUP BY wt.name")
    List<Object[]> countFutureWorkoutThisMonth(
            @Param("userId") Long userId,
            @Param("now") LocalDateTime now,
            @Param("endOfMonth") LocalDateTime endOfMonth);


}
