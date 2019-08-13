package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        StatementStrategy st = new AddStatement(user);
        jdbcContextWithStatementStrategy(st);
    }

    public User get(String id) throws SQLException {
        Connection c = this.dataSource.getConnection();
        PreparedStatement ps = c
                .prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        rs.close();
        ps.close();
        c.close();

        if (user == null) throw new EmptyResultDataAccessException(1);

        return user;
    }
        public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
    }

    public int getCount() throws SQLException  {
        Connection c = this.dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

    //하나의 메소드로 추출해서 재사용성을 높이고 결합도를 낮추면서 응집도를 높인다.
    // 기존에 deleteAll 메소드에 있던 내용이 다 빠지고 deleteAll 메소드가 짧아진다. (가독성이좋다?)
    // 전략패턴의 컨텍스트 - PreparedStatement를 실행하는 jdbc의 흐름.
    //                전략 - PreparedStatemtnt를 생성하는 것.

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e){}
            }
            if(c != null){
                try{
                    c.close();
                } catch (SQLException e){}
            }
        }
    }

}
