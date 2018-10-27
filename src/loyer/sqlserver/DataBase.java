package loyer.sqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLserver2017连接帮助类
 * @author Loyer
 *
 */
public class DataBase {
//数据库连接字符串
  private final static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  private String url = null;
  private final static String username = "sa";
  private String password = null;
  private Connection connection = null;
  private ResultSet res = null;
  private PreparedStatement pstmt = null;
  
  public DataBase(String databaseName, String password) {
    this.url = "jdbc:sqlserver://localhost:1433;databaseName="+databaseName+"";
    this.password = password;
  }
  /**
   * 提供数据库连接方法
   * @return getConnectionCommon()
   * @throws Exception
   */
  public Connection getConnection() throws Exception {
    return getConnectionCommon();
  }
  /**
   * 定义数据库连接方法
   * @return connection
   * @throws Exception
   */
  private Connection getConnectionCommon() throws Exception {
    Connection connection = null;  //连接对象
    try {
      //加载驱动
      Class.forName(driver);
      connection = DriverManager.getConnection(url, username, password);
    } catch(ClassNotFoundException e) {
      throw new Exception("JDBC驱动加载失败");
    } catch(SQLException e) {
      throw new Exception("无法连接到数据库");
    }
    return connection;
  }
  /**
   * 提供数据库查询方法
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public ResultSet Search(String sql, String str[]) throws Exception {
    try {
      connection = getConnection();
      pstmt = connection.prepareStatement(sql);
      if(str != null) {
        for(int i = 0; i < str.length; i++) {
          pstmt.setString(i + 1, str[i]);
        }
      }
      res = pstmt.executeQuery();
    } catch(Exception e) {
      throw new Exception("数据库操作失败："+e.getMessage());
    }
    return res;
  }
  /**
   * 提供数据库增删修改方法
   * @param sql
   * @param str
   * @return
   * @throws Exception
   */
  public int AddU(String sql, String str[]) throws Exception {
    int getBack = 0;
    try {
      connection = getConnection();
      pstmt = connection.prepareStatement(sql);
      if(str != null) {
        for(int i = 0; i < str.length; i++) {
          pstmt.setString(i + 1, str[i]);
        }
      }
      getBack = pstmt.executeUpdate();
    } catch(Exception e) {
      throw new Exception("数据库操作失败："+e.getMessage());
    }
    return getBack;
  }
 /**
  * 数据库关闭连接方法
  * @throws SQLException 
  */
  public void close() throws SQLException {
    if(res != null) {
      try {
        res.close();
      } catch(SQLException e) {
        throw new SQLException("数据库关闭失败:"+e.getMessage());
      }
    }
    if(pstmt != null) {
      try {
        pstmt.close();
      } catch(SQLException e) {
        throw new SQLException("数据库关闭失败:"+e.getMessage());
      }
    }
    if(connection != null) {
      try {
        connection.close();
      } catch(SQLException e) {
        throw new SQLException("数据库关闭失败:"+e.getMessage());
      }
    }
  }
}
