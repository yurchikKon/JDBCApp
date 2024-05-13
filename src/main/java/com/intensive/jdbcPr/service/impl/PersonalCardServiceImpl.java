package com.intensive.jdbcPr.service.impl;

import com.intensive.jdbcPr.controller.dto.personalCard.PersonalCardDto;
import com.intensive.jdbcPr.entity.PersonalCard;
import com.intensive.jdbcPr.repository.api.PersonalCardRepository;
import com.intensive.jdbcPr.service.api.PersonalCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonalCardServiceImpl implements PersonalCardService {
    private final PersonalCardRepository personalCardRepository;

    public List<PersonalCard> getAllPersonalCards() {
        return personalCardRepository.getAllPersonalCards();
    }

    public PersonalCard getExactPersonalCard(Long id) {
        return personalCardRepository.getExactPersonalCard(id);
    }

    public void savePersonalCardList(List<PersonalCardDto> personalCardList) {
        personalCardRepository.savePersonalCardList(personalCardList);
    }

    public void save(PersonalCardDto personalCard) {
        personalCardRepository.save(personalCard);
    }

    public void deleteExact(Long id) {
        personalCardRepository.deleteExact(id);
    }

    public void updatePersonalCardSalary(Integer salary, Long id) {
        personalCardRepository.updatePersonalCardSalary(salary, id);
    }

    public void deleteAll() {
        personalCardRepository.deleteAll();
    }
}
