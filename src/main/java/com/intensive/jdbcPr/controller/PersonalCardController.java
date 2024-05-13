package com.intensive.jdbcPr.controller;

import com.intensive.jdbcPr.controller.dto.personalCard.PersonalCardDto;
import com.intensive.jdbcPr.entity.PersonalCard;
import com.intensive.jdbcPr.service.api.PersonalCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/personalCard")
@RequiredArgsConstructor
public class PersonalCardController {
    private final PersonalCardService personalCardService;

    @GetMapping("/getAll")
    public List<PersonalCard> getAllPersonalCards(){
        return personalCardService.getAllPersonalCards();
    }

    @GetMapping("/getExact")
    public PersonalCard getExactPersonalCard(Long id){
        return personalCardService.getExactPersonalCard(id);
    }

    @PostMapping("/saveList")
    public ResponseEntity<String> savePersonalCardList(List<PersonalCardDto> personalCardList){
        personalCardService.savePersonalCardList(personalCardList);
        return ResponseEntity.ok("Personal cards were created");
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(PersonalCardDto personalCard){
        personalCardService.save(personalCard);
        return ResponseEntity.ok("Personal card was created");
    }

    @PostMapping("/deleteExact")
    public ResponseEntity<String> deleteExact(Long id){
        personalCardService.deleteExact(id);
        return ResponseEntity.ok("Personal card was deleted");
    }

    @PostMapping("/updateSalary")
    public ResponseEntity<String> updatePersonalCardSalary(Integer salary, Long id){
        personalCardService.updatePersonalCardSalary(salary, id);
        return ResponseEntity.ok("Personal card was updated");
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAll(){
        personalCardService.deleteAll();
        return ResponseEntity.ok("All personal cards were deleted");
    }
}
