package hh.project.gymdiary.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
    //SAVE REQUEST URI TO VARIABLE
    String url;

    @Autowired
    private WorkoutRepository wrepo;
    @Autowired
    private CategoryRepository crepo;

    //DISPLAY ALL WEEK NUMBERS
    @RequestMapping(value={"/", "/weeklist"}, method = RequestMethod.GET)
    public String weekRoutine(Model model) {
        model.addAttribute("workouts", wrepo.findAll());
        return "weeklist";
    }

    //DISPLAY WEEKLY ROUTINES
    @RequestMapping(value="/weeklist/{week}")
    public String weekDiary(@PathVariable("week") Long week, Model model) {
        model.addAttribute("workouts", wrepo.findById(week).get());
        model.addAttribute("categories", crepo.findAll());
        return "week";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteWorkout(@PathVariable("id") Long workoutId, Model model) {
        crepo.deleteById(workoutId);
        return "redirect:../weeklist/{id}";
    }

    @RequestMapping(value="/log/{id}")
    public String logWorkout(@PathVariable("id") long workoutId, Model model, HttpServletRequest request) {
        model.addAttribute("workout", wrepo.findById(workoutId));
        model.addAttribute("categories", crepo.findAll());

        //GET REQUEST URI
        url = request.getRequestURI().toString();
        System.out.println(url);
        return "addworkout";
    }

    @RequestMapping(value="/addworkout/{id}")
    public String addRoutine(@PathVariable("id") Long categoryid, Model model, HttpServletRequest request) {
        model.addAttribute("workouts", wrepo.findAll());
        model.addAttribute("workout", new Workout());
        model.addAttribute("category", crepo.findById(categoryid).get());
        return "addworkout";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String saveRoutine(@ModelAttribute Workout newroutine, Model model) {
        wrepo.save(newroutine);

        //REDIRECT TO PREVIOUS PAGE IF NOT NULL
        try {
            //GET WEEK ID FROM REQUEST URI
            List<String> urlList = Arrays.asList(url.split("\\s*/\\s*"));
            String redirectId = urlList.get(2);
            return"redirect:/weeklist/"+ redirectId;
        } catch (Exception e) {
            return"redirect:/weeklist";
        }
    }
    

    @RequestMapping(value="/addweek")
    public String addWeek(Model model) {
        model.addAttribute("workout", new Workout());
        model.addAttribute("categories", crepo.findAll());
        return "addweek";
    }

    @RequestMapping(value="/saveweek", method = RequestMethod.POST)
    public String saveWeek(@ModelAttribute Workout newweek, Model model) {
        wrepo.save(newweek);
        return"redirect:/weeklist";
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
