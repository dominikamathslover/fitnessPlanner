# Fitness planner
An application where users can record their activities,
trigger further actions and plan activities.

---

### Getting Started 
**Home Page:** 
http://localhost:8080/  
**H2 Database Console:** 
http://localhost:8080/h2-console

---

## Features & Endpoints

1. View user's training history and basic statistics  
GET http://localhost:8080/{username}/workouts 
2. Add workouts for a user  
GET http://localhost:8080/{username}/add-training  
POST http://localhost:8080/{username}/add-training  
   x-www-form-urlencoded  
   Key : workoutTypeId, date, durationInMinutes
3. Generate a pie chart based on the frequency of each training type   
GET http://localhost:8080/{username}/workouts/chart  
Segments: Different types of training (e.g. cardio, running)  
Purpose: Visualizing the proportion between activity types for a user 

   
## Technologies Used 
Java/ Spring Boot / Maven / JavaScript / H2 Database / Hibernate /
Spring Data JPA / Thymeleaf / Flyway / Bootstrap 5 


