package hh.project.gymdiary.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer week;
    private String exercise;
    private Integer sets;
    private Integer reps;
    private Double weight;

    @ManyToOne
    @JsonIgnore
@JoinColumn(name = "categoryid")
    private Category category;

    public Workout() {}

    public Workout(Integer week, String exercise, Integer sets, Integer reps, double weight, Category category) {
        super();
        this.week = week;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    @Override
    public String toString() {
        if (this.category != null) {
            return "Workout [id=" + id + ", week=" + week + ", exercise=" + exercise + ", sets=" + sets + ", reps=" + reps + ", category" + this.getCategory() + "]"; 
        } else {
            return "Workout[id=" + id + ", week=" + week + ", exercise=" + exercise + ", sets=" + sets + ", reps=" + reps + "]";
        }
    }
}
