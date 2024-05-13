package com.intensive.jdbcPr.repository.impl;

import com.intensive.jdbcPr.controller.dto.department.DepartmentDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.repository.api.DepartmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private static final String UPDATE_QUERY = "update department\n" +
        "set name = ?\n" +
        "where name = ?;";
    private static final String SELECT_QUERY = "select id, name\n" +
        "from department;";
    private static final String EXACT_SELECT_QUERY = "select id, name\n" +
        "from department\n" +
        "where name = ?;";
    private static final String INSERT_QUERY = "insert into department (name) values\n" +
        "(?);";
    private static final String DELETE_QUERY = "delete from department\n" +
        "where name = ?;";
    private static final String TRUNCATE_QUERY = "truncate tale department cascade";

    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Override
    public List<Department> getAll() {
        return selectQuery(SELECT_QUERY);
    }

    @Override
    public Department getExact(String name) {
        return exactSelectQuery(EXACT_SELECT_QUERY, name);
    }

    @Override
    public void save(DepartmentDto department) {
        insertQuery(INSERT_QUERY, department);
    }

    @Override
    public void update(String oldName, String newName) {
        updateQueryDepartment(UPDATE_QUERY, oldName, newName);
    }

    @Override
    public void deleteExact(String name) {
        deleteQuery(DELETE_QUERY, name);
    }

    @Override
    public void deleteAll() {
        truncateQuery(TRUNCATE_QUERY);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void truncateQuery(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteQuery(String query, String name) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, name);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertQuery(String query, DepartmentDto department) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, department.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Department exactSelectQuery(String query, String name) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            Department department = new Department();
            department.setId(resultSet.getLong("id"));
            department.setName(resultSet.getString("name"));

            return department;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Department> selectQuery(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            List<Department> departmentList = new ArrayList<>();

            while (resultSet.next()) {
                Department department = new Department();

                department.setId(resultSet.getLong("id"));
                department.setName(resultSet.getString("name"));

                departmentList.add(department);
            }
            return departmentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateQueryDepartment(String query, String oldName, String newName) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, newName);
            statement.setString(2, oldName);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
