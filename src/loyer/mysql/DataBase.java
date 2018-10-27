package loyer.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mySQL8.0连接帮助类
 * @author Loyer
 *
 */
public class DataBase {
  private String URL = null;
  private final String USER = "root";
  private String PWD = null;
  private PreparedStatement ptmt = null;
  private Connection conn = null;
  private ResultSet rs = null;
  /**
   * 构造方法
   * @param databaseName 数据库名
   * @param password 数据库密码
   */
  public DataBase(String databaseName, String password) {
    this.URL = "jdbc:mysql://localhost:3306/"+databaseName+"?useSSL=false&serverTimezone=UTC";
    this.PWD = password;
  }
  /**
   * 连接到数据库
   * @throws Exception 
   * 
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public Connection getConnection() throws Exception {
    Connection connection = null; // 连接对象
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(URL, USER, PWD);
    } catch (ClassNotFoundException | SQLException e) {
      throw new Exception("数据库连接失败："+ e.getMessage());
    }
    return connection;
  }

  /**
   * 提供数据库查询方法
   * 
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public ResultSet Search(String sql, String str[]) throws Exception {
    try {
      conn = getConnection();
      ptmt = conn.prepareStatement(sql);
      if (str != null) {
        for (int i = 0; i < str.length; i++) {
          ptmt.setString(i + 1, str[i]);
        }
      }
      rs = ptmt.executeQuery();
    } catch (Exception e) {
      throw new Exception("操作数据库失败:"+e.getMessage());
    } 
    return rs;
  }

  /**
   * 提供数据库增删修改方法
   * 
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public int AddU(String sql, String str[]) throws Exception {
    int getBack = 0;
    try {
      conn = getConnection();
      ptmt = conn.prepareStatement(sql);
      if (str != null) {
        for (int i = 0; i < str.length; i++) {
          ptmt.setString(i + 1, str[i]);
        }
      }
      getBack = ptmt.executeUpdate();
    } catch (Exception e) {
      throw new Exception("操作数据库失败:"+e.getMessage());
    } 
    return getBack;
  }

  /**
   * 数据库关闭连接方法
   * @throws SQLException 
   */
  public void close() throws SQLException {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        throw new SQLException("数据库关闭失败:"+e.getMessage());
      }
    }
    if (ptmt != null) {
      try {
        ptmt.close();
      } catch (SQLException e) {
        throw new SQLException("数据库关闭失败:"+e.getMessage());
      }
    }
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new SQLException("数据库关闭失败:"+e.getMessage());
      }
    }
  }
}
