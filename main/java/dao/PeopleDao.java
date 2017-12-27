package dao;

import connectionManager.ConnectionManager;
import entity.People;
import entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeopleDao {
    private static final String PEOPLE_TABLE_NAME = "peoples";
    private static final String ROLE_NAME_TABLE = "roles";

    private static final Object LOCK = new Object();
    private static PeopleDao INSTANCE = null;

    public static PeopleDao getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new PeopleDao();
                }
            }
        }
        return INSTANCE;
    }

    public Long save(People people) {
        Long resultId = 0L;
        String sql = "INSERT INTO peoples (name_people, family_people, date_of_birth_people, role_id) VALUE (?,?,?,?);";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, people.getNamePeople());
                preparedStatement.setString(2, people.getFamilyPeople());
                preparedStatement.setLong(3, people.getDateOfBirthPeople());
                preparedStatement.setLong(4, people.getRole().getId());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()){
                    resultId = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultId;
    }

    private People createPeoleFromResultSet(ResultSet resultSet) throws SQLException {

        Role role = new Role();
        role.setId(resultSet.getLong(ROLE_NAME_TABLE + ".id"));
        role.setName(resultSet.getString(ROLE_NAME_TABLE + ".name"));

        return new People(
                resultSet.getLong(PEOPLE_TABLE_NAME + ".id"),
                resultSet.getString(PEOPLE_TABLE_NAME + ".name_people"),
                resultSet.getString(PEOPLE_TABLE_NAME + ".family_people"),
                resultSet.getLong(PEOPLE_TABLE_NAME + ".date_of_birth_people"),
                role);
    }

    public List<People> findAll(){
        List<People> peopleList = new ArrayList<>();
        String sql  = "select peoples.id, peoples.name_people, peoples.family_people, peoples.date_of_birth_people, roles.id, roles.name\n" +
                "from peoples  as peoples JOIN roles as roles ON peoples.role_id = roles.id;";
        try (Connection connection = ConnectionManager.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    peopleList.add(createPeoleFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return peopleList;
    }
}
