package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    public Message insert(Message message) {
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int genId = rs.getInt(1);
                return new Message(genId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return message;
    }

    public List<Message> getAll() {
        String sql = "SELECT * FROM message;";
        List<Message> messages= new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                                        rs.getInt("posted_by"),
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getById(int id) {
        String sql = "SELECT * FROM message WHERE message_id = ?;";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                                        rs.getInt("posted_by"),
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                return message;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void delete(Message message) {
        String sql = "DELETE FROM message WHERE message_id = ?";
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getMessage_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void update(int id, Message message) {
       String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
       try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, id);
            ps.executeUpdate();
       } catch (SQLException e) {
            System.out.println(e.getMessage());
       } 
    } 
     
    public List<Message> getByAccount(Account account) {
        String sql = "SELECT * FROM message WHERE posted_by = ?;";
        List<Message> messages = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account.getAccount_id());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                                        rs.getInt("posted_by"),
                                        rs.getString("message_text"),
                                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    

}
