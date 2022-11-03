package hh.project.gymdiary.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.project.gymdiary.domain.CategoryRepository;
import hh.project.gymdiary.domain.Workout;
import hh.project.gymdiary.domain.WorkoutRepository;

@Controller
public class WorkoutController {
    
    @Autowired
    private WorkoutRepository wrepo;
    @Autowired
    private CategoryRepository crepo;

    //DISPLAY ALL 
    @RequestMapping(value={"/", "/diary"})
    public String workoutDiary(Model model) {
        model.addAttribute("workouts", wrepo.findAll());
        return "diary";
    }

    //DISPLAY ALL WEEK NUMBERS
    @RequestMapping(value="/routine", method = RequestMethod.GET)
    public String weekRoutine(Model model) {
        model.addAttribute("workouts", wrepo.findAll());
        return "routine";
    }

    //DISPLAY WEEKLY ROUTINES
    @RequestMapping(value="/routine/{week}")
    public String weekDiary(@PathVariable("week") Long week, Model model) {
        model.addAttribute("workouts", wrepo.findById(week).get());
        model.addAttribute("categories", crepo.findAll());
        return "week";
    }




    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteWorkout(@PathVariable("id") Long workoutId, Model model) {
        crepo.deleteById(workoutId);
        return "redirect:../diary";
    }

    @RequestMapping(value="/log/{id}")
    public String logWorkout(@PathVariable("id") long workoutId, Model model) {
        model.addAttribute("workout", wrepo.findById(workoutId));
        model.addAttribute("categories", crepo.findAll());
        return "addworkout";
    }

    @RequestMapping(value="/add")
    public String addRoutine(Model model) {
        model.addAttribute("workout", new Workout());
        model.addAttribute("categories", crepo.findAll());
        return "addworkout";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String saveRoutine(@ModelAttribute Workout newroutine, Model model) {
        wrepo.save(newroutine);
        return"redirect:/routine";
    }


    //REST
    @RequestMapping(value="/json", method = RequestMethod.GET)
    public @ResponseBody List<Workout> workoutListRest() {
        return (List<Workout>) wrepo.findAll();
    }

    //REST BY ID
    @RequestMapping(value="/json/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Workout> findWorkoutRest(@PathVariable("id") Long id) {
        return wrepo.findById(id);
    }
}
