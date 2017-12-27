package dao;

public class UsersDao {

    private static final Object LOCK = new Object();
    private static UsersDao INSTANCE = null;

    public static UsersDao getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new UsersDao();
                }
            }
        }
        return INSTANCE;
    }
//    public void save(Users users) {
//        String sql = "INSERT INTO users (first_name, last_name, review) values (?, ?, ?);";
//        try (Connection connection = ConnectionManager.getConnection()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setString(1, users.getFirstName());
//                preparedStatement.setString(2, users.getLastName());
//                preparedStatement.setString(3, users.getReview());
//                preparedStatement.executeUpdate();
//                connection.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

}
