package com.intensive.jdbcPr.repository.impl;

import com.intensive.jdbcPr.controller.dto.department.DepartmentDto;
import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.controller.dto.project.ProjectDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.entity.Employee;
import com.intensive.jdbcPr.entity.Project;
import com.intensive.jdbcPr.repository.api.ProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {

    private static final String UPDATE_QUERY = "update project\n" +
        "set name = ?\n" +
        "where name = ?;";
    private static final String SELECT_QUERY = "select id, name, company\n" +
        "from project;";
    private static final String EXACT_SELECT_QUERY = "select id, name, company\n" +
        "from project\n" +
        "where name = ?;";
    private static final String INSERT_QUERY = "insert into project (name, company) values\n" +
        "(?, ?);";
    private static final String DELETE_QUERY = "delete from project\n" +
        "where name = ?;";
    private static final String TRUNCATE_QUERY = "truncate tale project cascade";
    private static final String SELECT_EMPLOYEE_BY_PROJECT_QUERY = "select e.first_name as firstName, e.last_name as lastName, e.department_id as dId, e.personal_card_id as pcId\n" +
        "from project p\n" +
        "inner join employee_project ep\n" +
        "on p.id = ep.project_id\n" +
        "inner join employee e\n" +
        "on e.id = ep.employee_id\n" +
        "where p.name = ?;";

    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Override
    public List<Project> getAll() {
        return selectQuery(SELECT_QUERY);
    }

    @Override
    public Project getExact(String name) {
        return exactSelectQuery(EXACT_SELECT_QUERY, name);
    }

    @Override
    public void save(ProjectDto project) {
        insertQuery(INSERT_QUERY, project);
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

    @Override
    public List<EmployeeDto> getAllEmployeesByProject(String name) {
        return selectEmployeeByProjectQuery(SELECT_EMPLOYEE_BY_PROJECT_QUERY, name);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private List<EmployeeDto> selectEmployeeByProjectQuery(String query, String name) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            List<EmployeeDto> employeeList = new ArrayList<>();

            while (resultSet.next()) {
                EmployeeDto employee = new EmployeeDto();
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName("lastName");
                employee.setDepartmentId(resultSet.getLong("dId"));
                employee.setPersonalCardId(resultSet.getLong("pcId"));

                employeeList.add(employee);
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    private void insertQuery(String query, ProjectDto project) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, project.getName());
            statement.setString(2, project.getCompany());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Project exactSelectQuery(String query, String name) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            Project project = new Project();

            project.setId(resultSet.getLong("id"));
            project.setName(resultSet.getString("name"));
            project.setCompany(resultSet.getString("company"));

            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Project> selectQuery(String query) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            List<Project> projectList = new ArrayList<>();

            while (resultSet.next()) {
                Project project = new Project();

                project.setId(resultSet.getLong("id"));
                project.setName(resultSet.getString("name"));
                project.setCompany(resultSet.getString("company"));

                projectList.add(project);
            }
            return projectList;
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
