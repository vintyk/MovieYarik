package dao;

import connectionManager.ConnectionManager;
import entity.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreDao {
    private static final String GENRES_TABLE_NAME = "genres";
    private static final Object LOCK = new Object();
        private static GenreDao INSTANCE = null;

        public static GenreDao getInstance() {
            if (INSTANCE == null) {
                synchronized (LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = new GenreDao();
                    }
                }
            }
            return INSTANCE;
        }

        public Long save (Genre genre) {
            Long keyId = 0L;
            String sql = "INSERT INTO genres (name) values (?);";
            try (Connection connection = ConnectionManager.getConnection()) {
               try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                   preparedStatement.setString(1, genre.getName());
                   preparedStatement.executeUpdate();
                   ResultSet myResultKey = preparedStatement.getGeneratedKeys();
                    if (myResultKey.next()){
                        keyId = myResultKey.getLong(1);
                    }

               }
            } catch (SQLException e) {
                e.printStackTrace();
            }return keyId;
        }

        public Optional<Genre> findBy(Long id) {
            String sql = "select * from genres where id =?;";
           try (Connection connection = ConnectionManager.getConnection()) {
               try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                   preparedStatement.setLong(1, id);
                   ResultSet resultFindById = preparedStatement.executeQuery();
                   if (resultFindById.next()){
                       return Optional.of(new Genre(id, resultFindById.getString("name")));
                   }

               }

           } catch (SQLException e) {
               e.printStackTrace();
           }
           return Optional.empty();
        }

    private Genre createGenreFromResultSet(ResultSet resultSet) throws SQLException {
        return new Genre(
                resultSet.getLong(GENRES_TABLE_NAME+ ".id"),
                resultSet.getString(GENRES_TABLE_NAME + ".name"));
    }

        public List<Genre> findAll() {
          List<Genre> genreList =  new ArrayList<>();
          String sql = "select * from genres;";
            try (Connection connection = ConnectionManager.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    ResultSet resultFindAll = preparedStatement.executeQuery();
                    while (resultFindAll.next()) {
                       genreList.add(createGenreFromResultSet(resultFindAll));
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }return genreList;
        }

}
