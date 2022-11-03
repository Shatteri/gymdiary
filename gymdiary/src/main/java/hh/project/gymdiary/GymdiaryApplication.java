package hh.project.gymdiary;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.project.gymdiary.domain.Category;
import hh.project.gymdiary.domain.CategoryRepository;
import hh.project.gymdiary.domain.Workout;
import hh.project.gymdiary.domain.WorkoutRepository;

@SpringBootApplication
public class GymdiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymdiaryApplication.class, args);
	}

	@Bean
	public CommandLineRunner workoutDiary(WorkoutRepository wrepo, CategoryRepository crepo) {
		return (args) -> {
			crepo.save(new Category("Legs"));
			crepo.save(new Category("Chest"));
			crepo.save(new Category("Biceps"));
			
			wrepo.save(new Workout(1, "Test", 3, 10, 20.5, crepo.findByName("Legs").get(0)));
			wrepo.save(new Workout(2, "Test2", 3, 10, 30.1, crepo.findByName("Legs").get(0)));
			wrepo.save(new Workout(3, "Test3", 3, 10, 40.2, crepo.findByName("Legs").get(0)));
		};
	}

}
