package com.intensive.jdbcPr.controller;

import com.intensive.jdbcPr.controller.dto.department.DepartmentDto;
import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.controller.dto.project.ProjectDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.entity.Project;
import com.intensive.jdbcPr.service.api.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/getAll")
    public List<Project> getAll() {
        return projectService.getAll();
    }

    @GetMapping("/getExact")
    public Project getExact(@RequestParam String name) {
        return projectService.getExact(name);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(ProjectDto project) {
        projectService.save(project);
        return ResponseEntity.ok("New project was created");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam String oldName, @RequestParam String newName) {
        projectService.update(oldName, newName);
        return ResponseEntity.ok("Project was updated");
    }

    @PostMapping("/deleteExact")
    public ResponseEntity<String> deleteExact(@RequestParam String name) {
        projectService.deleteExact(name);
        return ResponseEntity.ok("Project was deleted");
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        projectService.deleteAll();
        return ResponseEntity.ok("All projects were deleted");
    }

    @GetMapping("/getAllEmployeesByProject")
    public List<EmployeeDto> getAllEmployeesByProject(@RequestParam String name) {
        return projectService.getAllEmployeesByProject(name);
    }
}
