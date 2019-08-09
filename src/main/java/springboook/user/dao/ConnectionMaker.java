package springboook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 2019 08 09
 * sw
 * ConnectionMaker 인터페이스
 * (템플릿 메소드)전략패턴을 위한 인터페이스 생성
 */
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;

}
