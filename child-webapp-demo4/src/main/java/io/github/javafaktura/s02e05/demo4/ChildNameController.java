package io.github.javafaktura.s02e05.demo4;

import io.github.javafaktura.s02e05.child.core.model.ChildNameStats;
import io.github.javafaktura.s02e05.child.core.model.Gender;
import io.github.javafaktura.s02e05.child.core.model.ParentChoice;
import io.github.javafaktura.s02e05.child.core.model.ParentPreferences;
import io.github.javafaktura.s02e05.child.core.model.Popularity;
import io.github.javafaktura.s02e05.child.core.service.ChildNameMemoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ChildNameController {

    private final ChildNameMemoryService childNameService;

    public ChildNameController(ChildNameMemoryService childNameChooser) {
        this.childNameService = childNameChooser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public String index(@RequestParam(required = false) Gender gender,
            @RequestParam(required = false) Popularity popularity,
            Model model) {
        ChildNameStats randomChildNameStats = childNameService.getRandom(new ParentPreferences(gender, popularity));
        model.addAttribute("child", randomChildNameStats);
        model.addAttribute("choice", new ParentChoice(randomChildNameStats.getName()));
        return "index";
    }

    @GetMapping(value = "/{name}")
    public String name(@PathVariable String name, Model model) {
        ChildNameStats childNameStats = childNameService.lookFor(name.toUpperCase());
        model.addAttribute("child", childNameStats);
        model.addAttribute("choice", new ParentChoice(name.toUpperCase()));
        return "index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("boys", childNameService.getAll(new ParentPreferences(Gender.MALE)));
        model.addAttribute("girls", childNameService.getAll(new ParentPreferences(Gender.FEMALE)));
        return "all";
    }

    @PostMapping(value = "/choice")
    public String choose(@ModelAttribute ParentChoice choice) {
        childNameService.add(choice.getName().toUpperCase());
        return "redirect:/all";
    }
}
