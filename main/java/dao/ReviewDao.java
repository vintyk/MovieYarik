package dao;

import connectionManager.ConnectionManager;
import entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDao {

    private static final Object LOCK = new Object();
        private static ReviewDao INSTANCE = null;
    private static final String REVIEW_TABLE_NAME = "review";

        public static ReviewDao getInstance() {
            if (INSTANCE == null) {
                synchronized (LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = new ReviewDao();
                    }
                }
            }
            return INSTANCE;
        }

    public Long saveReview(Review review) {
        Long keyId = 0L;
        String sql = "INSERT INTO review (text) values (?);";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, review.getText());
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

    public Optional<Review> findById(Long id) {
        String sql = "select * from review where id =?;";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                ResultSet resultFindById = preparedStatement.executeQuery();
                if (resultFindById.next()){
                    return Optional.of(new Review(id, resultFindById.getString("text")));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Review createReviewFromResultSet(ResultSet resultSet) throws SQLException {
        return new Review(
                resultSet.getLong(REVIEW_TABLE_NAME+ ".id"),
                resultSet.getString(REVIEW_TABLE_NAME + ".text"));
    }

    public List<Review> findAllReview() {
        List<Review> reviewList =  new ArrayList<>();
        String sql = "select * from review;";
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultFindAllReview = preparedStatement.executeQuery();
                while (resultFindAllReview.next()) {
                    reviewList.add(createReviewFromResultSet(resultFindAllReview));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }return reviewList;
    }

}