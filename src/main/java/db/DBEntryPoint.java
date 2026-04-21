package db;

import model.Article;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBEntryPoint {

    private final DBInterface dbManager;
    private static final Logger logger = LoggerFactory.getLogger(DBEntryPoint.class);
    public DBEntryPoint(DBInterface dbManager){
        this.dbManager = dbManager;
    }

    public  void addUser(User newUser){
        String sql = "INSERT INTO users (USERID,PASSWORD,NAME,EMAIL) VALUES (?,?,?,?)";
        Connection conn = null;
        PreparedStatement preppedStmt = null;

        try{
            conn = dbManager.getConnection();
            preppedStmt = conn.prepareStatement(sql);
            preppedStmt.setString(1, newUser.getUserId());
            preppedStmt.setString(2, newUser.getPassword());
            preppedStmt.setString(3, newUser.getName());
            preppedStmt.setString(4, newUser.getEmail());
            int affectedCount = preppedStmt.executeUpdate();
            if(affectedCount > 0){
                logger.info("Successfully added user {}", newUser.getUserId());
            }

        }catch (SQLException e) {
            logger.error("SQLException while executing addUser");
            throw new RuntimeException(e);
        }
        finally{
            if(preppedStmt != null){
                try{preppedStmt.close();} catch(SQLException e){ logger.error("SQLException while closing prepared statement");}
            }
            if(conn != null){
                dbManager.returnConnection(conn);
            }
        }

    }

    public User findUserById(String id){
        String sql = "SELECT * FROM users WHERE USERID = ?";
        Connection conn = null;
        PreparedStatement preppedStmt = null;
        ResultSet resultSet = null;

        try{
            conn = dbManager.getConnection();
            preppedStmt = conn.prepareStatement(sql);
            preppedStmt.setString(1, id);
            resultSet = preppedStmt.executeQuery();


            if(resultSet.next()){
                String userId = resultSet.getString("USERID");
                String password = resultSet.getString("PASSWORD");
                String username = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                return  new User(userId,password,username,email);
            }


        }catch (SQLException e) {
            logger.error("SQLException while executing addUser");
            throw new RuntimeException(e);
        }
        finally{
            if(preppedStmt != null){
                try{preppedStmt.close();} catch(SQLException e){ logger.error("SQLException while closing prepared statement");}
            }
            if(resultSet != null){
                try{resultSet.close();} catch(SQLException e){ logger.error("SQLException while closing result set");}
            }
            if(conn != null){
                dbManager.returnConnection(conn);
            }
        }
        return null;
    }

    public Collection<User> findAllUser(){
        String sql = "SELECT * FROM users";
        Connection conn = null;
        PreparedStatement preppedStmt = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        try{
            conn = dbManager.getConnection();
            preppedStmt = conn.prepareStatement(sql);
            resultSet = preppedStmt.executeQuery();


            while(resultSet.next()){
                String userId = resultSet.getString("USERID");
                String password = resultSet.getString("PASSWORD");
                String username = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                User tmpUser =  new User(userId,password,username,email);
                userList.add(tmpUser);
            }

            return userList;

        }catch (SQLException e) {
            logger.error("SQLException while executing addUser");
            throw new RuntimeException(e);
        }
        finally{
            if(preppedStmt != null){
                try{preppedStmt.close();} catch(SQLException e){ logger.error("SQLException while closing prepared statement");}
            }
            if(resultSet != null){
                try{resultSet.close();} catch(SQLException e){ logger.error("SQLException while closing result set");}
            }
            if(conn != null){
                dbManager.returnConnection(conn);
            }
        }
    }

    public void addArticle(Article article){
        String sql = "INSERT INTO ARTICLES (AUTHORID,TITLE,CONTENT,CREATEDAT,USERNAME) VALUES (?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement preppedStmt = null;

        try{
            conn = dbManager.getConnection();
            preppedStmt = conn.prepareStatement(sql);
            preppedStmt.setInt(1, article.getAuthorId());
            preppedStmt.setString(2, article.getTitle());
            preppedStmt.setString(3, article.getContent());
            preppedStmt.setDate(4, article.getCreatedAt());
            int affectedCount = preppedStmt.executeUpdate();
            if(affectedCount > 0){
                logger.info("Successfully added user {}", newUser.getUserId());
            }

        }catch (SQLException e) {
            logger.error("SQLException while executing addUser");
            throw new RuntimeException(e);
        }
        finally{
            if(preppedStmt != null){
                try{preppedStmt.close();} catch(SQLException e){ logger.error("SQLException while closing prepared statement");}
            }
            if(conn != null){
                dbManager.returnConnection(conn);
            }
        }

    }

}
