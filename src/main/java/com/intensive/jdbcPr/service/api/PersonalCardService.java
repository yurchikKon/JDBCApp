package com.intensive.jdbcPr.service.api;

import com.intensive.jdbcPr.controller.dto.personalCard.PersonalCardDto;
import com.intensive.jdbcPr.entity.PersonalCard;

import java.util.List;

public interface PersonalCardService {
    List<PersonalCard> getAllPersonalCards();

    PersonalCard getExactPersonalCard(Long id);

    void savePersonalCardList(List<PersonalCardDto> personalCardList);

    void save(PersonalCardDto personalCard);

    void deleteExact(Long id);

    void updatePersonalCardSalary(Integer salary, Long id);

    void deleteAll();
}
