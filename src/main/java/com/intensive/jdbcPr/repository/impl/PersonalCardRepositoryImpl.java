package com.intensive.jdbcPr.repository.impl;

import com.intensive.jdbcPr.controller.dto.personalCard.PersonalCardDto;
import com.intensive.jdbcPr.entity.PersonalCard;
import com.intensive.jdbcPr.repository.api.PersonalCardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonalCardRepositoryImpl implements PersonalCardRepository {
    private static final String INSERT_QUERY_LIST = "insert into personal_card (male, age, salary," +
        " position, phone_number, current_address values";
    private static final String SELECT_QUERY = "select id as pc_id, age, male, phone_number," +
        " current_address, position, salary\n" +
        "from personal_card;";
    private static final String SELECT_QUERY_LIST =
        "select id as pc_id, age, male, phone_number, current_address, position, salary\n" +
            "from personal_card" +
            " where id = ?;";
    private static final String INSERT_QUERY = "insert into personal_card (male, age, salary," +
        " position, phone_number, current_address) values\n" +
        "(?, ?, ?, ?, ?, ?);";
    private static final String TRUNCATE_QUERY = "truncate table personal_card cascade;";
    private static final String DELETE_QUERY = "delete from personal_card" +
        "where id = ?;";
    private static final String UPDATE_QUERY_SALARY = "update personal_card" +
        "set salary = ?" +
        "where id = ?;";

    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Override
    public List<PersonalCard> getAllPersonalCards() {
        return selectQuery(SELECT_QUERY);
    }

    @Override
    public PersonalCard getExactPersonalCard(Long id) {
        return exactSelectQuery(SELECT_QUERY_LIST, id);
    }

    @Override
    public void savePersonalCardList(List<PersonalCardDto> personalCardList) {
        insertQueryList(personalCardList);
    }

    @Override
    public void save(PersonalCardDto personalCard) {
        insertQuery(INSERT_QUERY, personalCard);
    }

    @Override
    public void deleteExact(Long id) {
        deleteQuery(DELETE_QUERY, id);
    }

    @Override
    public void updatePersonalCardSalary(Integer salary, Long id) {
        updateQuerySalary(UPDATE_QUERY_SALARY, id, salary);
    }

    @Override
    public void deleteAll() {
        truncateQuery(TRUNCATE_QUERY);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

     private void updateQuerySalary(String updateQueryDepartment, Long id, Integer salary) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(updateQueryDepartment);

            statement.setInt(1, salary);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteQuery(String deleteQuery, Long id) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);

            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void truncateQuery(String truncateQuery) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(truncateQuery);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PersonalCard> selectQuery(String selectQuery) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();

            List<PersonalCard> personalCardList = new ArrayList<>();

            while (resultSet.next()) {
                PersonalCard personalCard = PersonalCard.builder()
                    .id(resultSet.getLong("pc_id"))
                    .age(resultSet.getInt("age"))
                    .male(resultSet.getString("male"))
                    .phoneNumber(resultSet.getString("phone_number"))
                    .currentAddress(resultSet.getString("current_address"))
                    .position(resultSet.getString("position"))
                    .salary(resultSet.getInt("salary"))
                    .build();

                personalCardList.add(personalCard);
            }
            return personalCardList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PersonalCard exactSelectQuery(String selectQuery, Long id) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            return PersonalCard.builder()
                .id(resultSet.getLong("pc_id"))
                .age(resultSet.getInt("age"))
                .male(resultSet.getString("male"))
                .phoneNumber(resultSet.getString("phone_number"))
                .currentAddress(resultSet.getString("current_address"))
                .position(resultSet.getString("position"))
                .salary(resultSet.getInt("salary"))
                .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertQuery(String insertQuery, PersonalCardDto personalCard) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(insertQuery);

            statement.setString(1, personalCard.getMale());
            statement.setInt(2, personalCard.getAge());
            statement.setInt(3, personalCard.getSalary());
            statement.setString(4, personalCard.getPosition());
            statement.setString(5, personalCard.getPhoneNumber());
            statement.setString(6, personalCard.getCurrentAddress());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertQueryList(List<PersonalCardDto> personalCardList) {
        StringBuilder insertQuery = new StringBuilder(INSERT_QUERY_LIST);

        for (int i = 0; i < personalCardList.size(); i++) {
            insertQuery.append("\n(?,?,?,?,?,?)");
        }
        insertQuery.append(';');

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(insertQuery.toString());

            for (int i = 0; i < personalCardList.size(); i++) {
                statement.setString(1 + i*4, personalCardList.get(i).getMale());
                statement.setInt(2 + i*4, personalCardList.get(i).getAge());
                statement.setInt(3 + i*4, personalCardList.get(i).getSalary());
                statement.setString(4 + i*4, personalCardList.get(i).getPosition());
                statement.setString(5 + i*4, personalCardList.get(i).getPhoneNumber());
                statement.setString(6 + i*4, personalCardList.get(i).getCurrentAddress());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
