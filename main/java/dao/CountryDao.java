package dao;

import connectionManager.ConnectionManager;
import entity.Country;

import java.sql.*;
import java.util.*;

public class CountryDao {
    private static final String COUNTRY_TABLE_NAME = "countries";
    private static final Object LOCK = new Object();
    private static CountryDao INSTANCE = null;

    public static CountryDao getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new CountryDao();
                }
            }
        }
        return INSTANCE;
    }

    public Long save(Country country) {
        Long countryId = null;
        String sql = "INSERT INTO countries (name) values (?);";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, country.getName());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    countryId = generatedKeys.getLong(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryId;
    }


    public Optional<Country> findById(Long id) {
        String sql = "select * from countries where id =?;";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(new Country(
                            resultSet.getLong("id"),
                            resultSet.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Country createCountryFromResultSet(ResultSet resultSet) throws SQLException {
        return new Country(
                resultSet.getLong(COUNTRY_TABLE_NAME + ".id"),
                resultSet.getString(COUNTRY_TABLE_NAME + ".name"));
    }

    public List<Country> findAll() {
        List<Country> countryList = new ArrayList<>();
        String sql = "select * from countries;";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet myResultSet = preparedStatement.executeQuery();
                while (myResultSet.next()) {
                    countryList.add(createCountryFromResultSet(myResultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }

    public Set<Country> getUniqueAll() {
        Set<Country> countrySet = new HashSet<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT * FROM countries;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                countrySet.add(new Country(
                        resultSet.getLong("id"),
                        resultSet.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }return countrySet;
    }
}
