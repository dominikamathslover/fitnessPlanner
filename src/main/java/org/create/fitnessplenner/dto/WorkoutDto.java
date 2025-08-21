package org.create.fitnessplenner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkoutDto {
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationInMinutes;

    @NotNull(message = "Workout date is required")
    private LocalDateTime date;

    @NotNull(message = "User ID is required")
    private Integer workoutTypeId;

}
