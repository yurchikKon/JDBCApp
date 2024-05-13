package com.intensive.jdbcPr.service.impl;

import com.intensive.jdbcPr.controller.dto.personalCard.PersonalCardDto;
import com.intensive.jdbcPr.entity.PersonalCard;
import com.intensive.jdbcPr.repository.api.PersonalCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PersonalCardServiceImplTest {

    @Mock
    PersonalCardRepository personalCardRepository;

    @InjectMocks
    PersonalCardServiceImpl personalCardService;

    private static final PersonalCard FIRST_PERSONAL_CARD = new PersonalCard(1L, "m",
        22, 22000, "manager", "88888888888", "Moscow");
    private static final PersonalCard SECOND_PERSONAL_CARD = new PersonalCard(2L, "m",
        22, 22000, "manager", "88888888888", "Moscow");
    private static final PersonalCardDto FIRST_CARD_DTO = new PersonalCardDto("m",
        22, 22000, "manager", "88888888888", "Moscow");
    private static final PersonalCardDto SECOND_CARD_DTO = new PersonalCardDto("m",
        21, 22000, "manager", "88888888888", "Moscow");

    @Test
    void getAllPersonalCards() {
        when(personalCardRepository.getAllPersonalCards()).thenReturn(of(FIRST_PERSONAL_CARD, SECOND_PERSONAL_CARD));

        List<PersonalCard> expected = of(FIRST_PERSONAL_CARD, SECOND_PERSONAL_CARD);

        assertEquals(expected, personalCardService.getAllPersonalCards());
    }

    @Test
    void getExactPersonalCard() {
        when(personalCardRepository.getExactPersonalCard(FIRST_PERSONAL_CARD.getId())).thenReturn(FIRST_PERSONAL_CARD);

        assertEquals(FIRST_PERSONAL_CARD, personalCardService.getExactPersonalCard(FIRST_PERSONAL_CARD.getId()));
    }

    @Test
    void savePersonalCardList() {
        personalCardService.savePersonalCardList(of(FIRST_CARD_DTO, SECOND_CARD_DTO));

        verify(personalCardRepository, times(1)).savePersonalCardList(of(FIRST_CARD_DTO, SECOND_CARD_DTO));
    }

    @Test
    void save() {
        personalCardService.save(FIRST_CARD_DTO);

        verify(personalCardRepository, times(1)).save(FIRST_CARD_DTO);
    }

    @Test
    void deleteExact() {
        personalCardService.deleteExact(FIRST_PERSONAL_CARD.getId());

        verify(personalCardRepository, times(1)).deleteExact(FIRST_PERSONAL_CARD.getId());
    }

    @Test
    void updatePersonalCardSalary() {
        personalCardService.updatePersonalCardSalary(FIRST_CARD_DTO.getSalary(), FIRST_PERSONAL_CARD.getId());

        verify(personalCardRepository, times(1))
            .updatePersonalCardSalary(FIRST_CARD_DTO.getSalary(), FIRST_PERSONAL_CARD.getId());
    }

    @Test
    void deleteAll() {
        personalCardService.deleteAll();

        verify(personalCardRepository, times(1)).deleteAll();
    }
}