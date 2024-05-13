package com.intensive.jdbcPr.repository.impl;

import com.intensive.jdbcPr.controller.dto.employee.EmployeesWithProjectsDto;
import com.intensive.jdbcPr.controller.dto.employee.EmployeeDto;
import com.intensive.jdbcPr.entity.Department;
import com.intensive.jdbcPr.entity.Employee;
import com.intensive.jdbcPr.entity.PersonalCard;
import com.intensive.jdbcPr.entity.Project;
import com.intensive.jdbcPr.repository.api.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String SELECT_EMPLOYEES_WITH_PROJECTS = "select e.first_name as firstName, e.last_name as " +
        "lastName, p.name as projectName, p.company as projectCompany\n" +
        "from employee e\n" +
        "left join employee_project ep\n" +
        "on e.id = ep.employee_id\n" +
        "left join project p\n" +
        "on ep.project_id = p.id;";
    private static final String UPDATE_EMPLOYEE_PERSONAL_CARD_QUERY = "update personal_card\n" +
        "set position = ?\n" +
        "where id = (\n" +
        "select personal_card_id\n" +
        "from employee\n" +
        "where first_name = ? and last_name = ?\n" +
        ");";
    private static final String INSERT_QUERY_LIST = "insert into employee (first_name, last_name," +
        " department_id, personal_card_id values";
    private static final String SELECT_QUERY = "select d.id as dep_id, d.name as name, p.id as pc_id, p.age as age," +
        " p.male as male, p.phone_number as phone_number,\n" +
        "p.current_address as current_address, p.position as position, p.salary as salary, e.id as id,\n" +
        "e.first_name as first_name, e.last_name as last_name\n" +
        "from employee e\n" +
        "left join department d\n" +
        "on d.id = e.department_id\n" +
        "left join personal_card p\n" +
        "on p.id = personal_card_id;";
    private static final String SELECT_QUERY_LIST =
        "select d.id as dep_id, d.name as name, p.id as pc_id, p.age as age, p.male as male, p.phone_number as phone_number,\n" +
            "p.current_address as current_address, p.position as position, p.salary as salary, e.id as id,\n" +
            "e.first_name as first_name, e.last_name as last_name\n" +
            "from employee e\n" +
            "left join department d\n" +
            "on d.id = e.department_id\n" +
            "left join personal_card p\n" +
            "on p.id = personal_card_id\n" +
            "where e.first_name = ? and e.last_name = ?;";
    private static final String INSERT_QUERY = "insert into employee (first_name, last_name," +
        " department_id, personal_card_id) values\n" +
        "(?, ?, ?, ?);";
    private static final String TRUNCATE_QUERY = "truncate table employee cascade;";
    private static final String DELETE_QUERY = "delete from employee" +
        "where first_name = ? and last_name = ?;";
    private static final String UPDATE_QUERY_DEPARTMENT = "update employee" +
        "set department_id = ?" +
        "where first_name = ? and last_name = ?;";
    private static final String JOIN_EMPLOYEE_PROJECT_QUERY = "select p.id as id, p.name as name, p.company as company\n" +
        "from employee e\n" +
        "left join employee_project ep\n" +
        "on e.id = ep.employee_id\n" +
        "left join project p\n" +
        "on p.id = ep.project_id\n" +
        "where e.first_name = ? and e.last_name = ?;";

    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Override
    public List<Employee> getAllEmployees() {
        return selectQuery(SELECT_QUERY);
    }

    @Override
    public Employee getExactEmployee(String firstName, String lastName) {
        return exactSelectQuery(SELECT_QUERY_LIST,
            firstName, lastName);
    }

    @Override
    public void saveEmployeeList(List<EmployeeDto> employeeList) {
        insertQueryList(employeeList);
    }

    @Override
    public void save(EmployeeDto employee) {
        insertQuery(INSERT_QUERY, employee);
    }

    @Override
    public void deleteExact(String firstName, String lastName) {
        deleteQuery(DELETE_QUERY, firstName, lastName);
    }

    @Override
    public void updateEmployeeDepartment(String firstName, String lastName, Long departmentId) {
        updateQueryDepartment(UPDATE_QUERY_DEPARTMENT, firstName, lastName, departmentId);
    }

    @Override
    public void deleteAll() {
        truncateQuery(TRUNCATE_QUERY);
    }

    @Override
    public void updateEmployeePersonalCardPosition(String firstName, String lastName, String position) {
        updateEmployeePersonalCardQuery(UPDATE_EMPLOYEE_PERSONAL_CARD_QUERY, firstName, lastName, position);
    }

    @Override
    public List<Project> getAllProjectsByEmployee(String firstName, String lastName) {
        return joinEmployeeProjectQuery(JOIN_EMPLOYEE_PROJECT_QUERY, firstName, lastName);
    }

    @Override
    public List<EmployeesWithProjectsDto> getAllEmployeesWithProjects() {
        return getAllEmployeeWithProjectsQuery(SELECT_EMPLOYEES_WITH_PROJECTS);
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private List<EmployeesWithProjectsDto> getAllEmployeeWithProjectsQuery(String query) {
        try(Connection connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            List<EmployeesWithProjectsDto> employeesWithProjectsList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EmployeesWithProjectsDto employeesWithProjectsDto = new EmployeesWithProjectsDto();
                employeesWithProjectsDto.setFirstName(resultSet.getString("firstName"));
                employeesWithProjectsDto.setLastName(resultSet.getString("lastName"));
                employeesWithProjectsDto.setProjectName(resultSet.getString("projectName"));
                employeesWithProjectsDto.setProjectCompany(resultSet.getString("projectCompany"));
                employeesWithProjectsList.add(employeesWithProjectsDto);
            }
            return employeesWithProjectsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateEmployeePersonalCardQuery(String query, String firstName, String lastName, String position) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, position);
            statement.setString(2, firstName);
            statement.setString(3, lastName);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Project> joinEmployeeProjectQuery(String query, String firstName, String lastName) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();
            List<Project> projects = new ArrayList<>();

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong("id"));
                project.setName(resultSet.getString("name"));
                project.setCompany(resultSet.getString("company"));

                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateQueryDepartment(String updateQueryDepartment, String firstName, String lastName, Long departmentId) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(updateQueryDepartment);

            statement.setLong(1, departmentId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteQuery(String deleteQuery, String firstName, String lastName) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);

            statement.setString(1, firstName);
            statement.setString(2, lastName);

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

    private List<Employee> selectQuery(String selectQuery) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(selectQuery);

            ResultSet resultSet = statement.executeQuery();
            List<Employee> employeeList = new ArrayList<>();

            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getLong("dep_id"));
                department.setName(resultSet.getString("name"));

                PersonalCard personalCard = PersonalCard.builder()
                    .id(resultSet.getLong("pc_id"))
                    .age(resultSet.getInt("age"))
                    .male(resultSet.getString("male"))
                    .phoneNumber(resultSet.getString("phone_number"))
                    .currentAddress(resultSet.getString("current_address"))
                    .position(resultSet.getString("position"))
                    .salary(resultSet.getInt("salary"))
                    .build();

                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setDepartment(department);
                employee.setPersonalCard(personalCard);

                employeeList.add(employee);
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee exactSelectQuery(String selectQuery, String firstName, String lastName) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            Department department = new Department();
            department.setId(resultSet.getLong("dep_id"));
            department.setName(resultSet.getString("name"));

            PersonalCard personalCard = PersonalCard.builder()
                .id(resultSet.getLong("pc_id"))
                .age(resultSet.getInt("age"))
                .male(resultSet.getString("male"))
                .phoneNumber(resultSet.getString("phone_number"))
                .currentAddress(resultSet.getString("current_address"))
                .position(resultSet.getString("position"))
                .salary(resultSet.getInt("salary"))
                .build();

            Employee employee = new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setDepartment(department);
            employee.setPersonalCard(personalCard);

            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertQuery(String insertQuery, EmployeeDto employee) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(insertQuery);

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setLong(3, employee.getDepartmentId());
            statement.setLong(4, employee.getPersonalCardId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertQueryList(List<EmployeeDto> employeeList) {
        StringBuilder insertQuery = new StringBuilder(INSERT_QUERY_LIST);

        for (int i = 0; i < employeeList.size(); i++) {
            insertQuery.append("\n(?,?,?,?)");
        }
        insertQuery.append(';');

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(insertQuery.toString());

            for (int i = 0; i < employeeList.size(); i++) {
                statement.setString(1 + i * 4, employeeList.get(i).getFirstName());
                statement.setString(2 + i * 4, employeeList.get(i).getFirstName());
                statement.setLong(3 + i * 4, employeeList.get(i).getDepartmentId());
                statement.setLong(4 + i * 4, employeeList.get(i).getPersonalCardId());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
