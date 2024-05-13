package com.intensive.jdbcPr.controller;

import com.intensive.jdbcPr.controller.dto.department.DepartmentDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.service.api.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/getAll")
    public List<Department> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/getExact")
    public Department getExact(@RequestParam String name) {
        return departmentService.getExact(name);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(DepartmentDto department) {
        departmentService.save(department);
        return ResponseEntity.ok("New department was created");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam String oldName, @RequestParam String newName) {
        departmentService.update(oldName, newName);
        return ResponseEntity.ok("Department was updated");
    }

    @PostMapping("/deleteExact")
    public ResponseEntity<String> deleteExact(@RequestParam String name) {
        departmentService.deleteExact(name);
        return ResponseEntity.ok("Department was deleted");
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        departmentService.deleteAll();
        return ResponseEntity.ok("All departments were deleted");
    }
}
