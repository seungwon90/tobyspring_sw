package springbook.trash;

import springbook.trash.ConnectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *  2019 08 09
 *  sw
 *  D사 독자적인 Connection설정
 */
public class DConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {

        return null;
    }
}
