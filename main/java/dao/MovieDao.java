package dao;

import connectionManager.ConnectionManager;
import entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDao {

    private static final String MOVIES_TABLE_NAME = "movies";
    private static final String PEOPLE_TABLE_NAME = "peoples";
    private static final String PEOPLE_ROLE_TABLE_NAME = "peoples_role";
    private static final String REVIEW_TABLE_NAME = "review";
    private static final String GENRE_TABLE_NAME = "genres";
    private static final String COUNTRY_TABLE_NAME = "countries";

    private static final Object LOCK = new Object();
    private static MovieDao INSTANCE = null;

    public static MovieDao getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieDao();
                }
            }
        }
        return INSTANCE;
    }

    private Movie createMoviesFromResultSet(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong(PEOPLE_ROLE_TABLE_NAME + ".id_role"));
        role.setName(resultSet.getString(PEOPLE_ROLE_TABLE_NAME + ".role_name"));

        People myPeople = new People();
        myPeople.setId(resultSet.getLong(PEOPLE_ROLE_TABLE_NAME + ".id_people"));
        myPeople.setNamePeople(resultSet.getString(PEOPLE_ROLE_TABLE_NAME + ".name_people"));
        myPeople.setFamilyPeople(resultSet.getString(PEOPLE_ROLE_TABLE_NAME + ".family_people"));
        myPeople.setDateOfBirthPeople(resultSet.getLong(PEOPLE_ROLE_TABLE_NAME + ".date_of_birth_people"));
        myPeople.setRole(role);

        Review myReview = new Review();
        myReview.setId(resultSet.getLong(REVIEW_TABLE_NAME + ".id_review"));
        myReview.setText(resultSet.getString(REVIEW_TABLE_NAME + ".text"));

        Genre myGenre = new Genre();
        myGenre.setId(resultSet.getLong(GENRE_TABLE_NAME + ".genre_id"));
        myGenre.setName(resultSet.getString(GENRE_TABLE_NAME + ".genre_name"));

        Country myCountry = new Country();
        myCountry.setId(resultSet.getLong(COUNTRY_TABLE_NAME + ".country_id"));
        myCountry.setName(resultSet.getString(COUNTRY_TABLE_NAME + ".country_name"));
        return new Movie(
                resultSet.getLong(MOVIES_TABLE_NAME + ".movie_id"),
                resultSet.getString(MOVIES_TABLE_NAME + ".movie_name"),
                resultSet.getLong(MOVIES_TABLE_NAME + ".year"),
                myPeople,
                myReview,
                resultSet.getLong(MOVIES_TABLE_NAME + ".rank"),
                myGenre,
                myCountry);
    }

    public Long save(Movie movie) {
        Long resultMovies = 0L;
        String sql = "INSERT INTO movies (name, year, people_id, review_id, rank, genre_id, country_id) VALUES (?, ?, ? ,? ,? ,? ,?);";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, movie.getNameMovie());
                preparedStatement.setLong(2, movie.getYear());
                preparedStatement.setLong(3, movie.getPeople().getId());
                preparedStatement.setLong(4, movie.getReview().getId());
                preparedStatement.setLong(5, movie.getRank());
                preparedStatement.setLong(6, movie.getGenre().getId());
                preparedStatement.setLong(7, movie.getCountry().getId());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Movie> findById(Long id) {
        String sql = "SELECT movies.id as movie_id, movies.name as movie_name, movies.year, movies.rank,\n" +
                "  review.id as id_review, review.text,\n" +
                "  genres.id as genre_id, genres.name as genre_name,\n" +
                "  countries.id as country_id, countries.name as country_name,\n" +
                "  peoples_role.id_people, peoples_role.name_people, peoples_role.family_people, peoples_role.date_of_birth_people,\n" +
                "  peoples_role.id_role, peoples_role.role_name\n" +
                "from movies movies\n" +
                "  JOIN review review ON movies.review_id=review.id\n" +
                "  JOIN genres genres ON movies.genre_id = genres.id\n" +
                "  JOIN countries countries ON movies.country_id = countries.id\n" +
                "  JOIN (SELECT p.id as id_people, p.name_people, p.family_people, p.date_of_birth_people, r.id as id_role, r.name as role_name\n" +
                "from peoples p JOIN roles r ON p.role_id = r.id) as peoples_role\n" +
                "    ON movies.people_id = peoples_role.id_people WHERE movies.id = ?;";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(createMoviesFromResultSet(resultSet));
                }
            } catch (SQLException e1) {
                System.out.println("-------ОШИБКА В PREPARE STATMENT----");
                e1.printStackTrace();
            }
        } catch (SQLException e2) {
            System.out.println("-----------ЧТО-ТО ПОШЛО НЕ ТАК!!!-------");
            System.out.println("-----------Что-то с SQL- Connection-----");
            e2.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Movie> findAll() {
        List<Movie> moviesList = new ArrayList<>();
        String sql = "SELECT movies.id as movie_id, movies.name as movie_name, movies.year, movies.rank,\n" +
                "  review.id as id_review, review.text,\n" +
                "  genres.id as genre_id, genres.name as genre_name,\n" +
                "  countries.id as country_id, countries.name as country_name,\n" +
                "  peoples_role.id_people, peoples_role.name_people, peoples_role.family_people, peoples_role.date_of_birth_people,\n" +
                "  peoples_role.id_role, peoples_role.role_name\n" +
                "from movies movies\n" +
                "  JOIN review review ON movies.review_id=review.id\n" +
                "  JOIN genres genres ON movies.genre_id = genres.id\n" +
                "  JOIN countries countries ON movies.country_id = countries.id\n" +
                "  JOIN (SELECT p.id as id_people, p.name_people, p.family_people, p.date_of_birth_people, r.id as id_role, r.name as role_name\n" +
                "from peoples p JOIN roles r ON p.role_id = r.id) as peoples_role\n" +
                "    ON movies.people_id = peoples_role.id_people;";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultFindAll = preparedStatement.executeQuery();
                while (resultFindAll.next()) {
                    moviesList.add(createMoviesFromResultSet(resultFindAll));
                }
            }
        } catch (SQLException e) {
            System.out.println("-----------ЧТО-ТО ПОШЛО НЕ ТАК!!!-------");
            e.printStackTrace();
        }
        return moviesList;
    }
}

