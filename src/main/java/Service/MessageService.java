package Service;

import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.ArrayList;
import java.util.List;


public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public Message create(Message message) {
        
        int id = message.posted_by;
        
        if(message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
            return null;
        }
        if (message.getMessage_text().length() > 254) {
            return null;
        }
        try {
            Message createdMessage = messageDAO.insert(message);
            return createdMessage;
        } catch (Exception e) {
            System.out.println("Message creation failed");
        }
        return null;
    }
    public Message getById(int id) {
        try {
            Message message = messageDAO.getById(id);
            return message;
        } catch (Exception e) {
            System.out.println("Message retrieval failed");
        }
        return null;
    }

    
}
