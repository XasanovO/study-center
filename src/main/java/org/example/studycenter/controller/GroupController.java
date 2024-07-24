package org.example.studycenter.controller;


import lombok.RequiredArgsConstructor;
import org.example.studycenter.entity.Group;
import org.example.studycenter.repo.GroupRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupRepository groupRepository;

    @GetMapping()
    public String groups(Model model) {
        model.addAttribute("groups", groupRepository.findAll());
        return "group";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name) {
        Group group = Group.builder()
                .name(name)
                .build();
        groupRepository.save(group);
        return "redirect:/group";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        groupRepository.deleteById(id);
        return "redirect:/group";
    }

}
