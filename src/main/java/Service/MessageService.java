package Service;

import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.ArrayList;
import java.util.List;


public class MessageService {

    private AccountService accountService;
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    private Message validateMessage (Message message){
        
        if(message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
            return null;
        }
        if (message.getMessage_text().length() > 254) {
            return null;
        }
        return message;
    }

    public Message create(Message message) {

        if (validateMessage(message) == null) {
            return null; 
        }
        if(accountService.accountIsPresent(message.posted_by) == false) {
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
            if(message == null){
                return null;
            }
            return message;
        } catch (Exception e) {
            System.out.println("Message retrieval failed");
        }
        return null;
    }

    public List<Message> getAll(){
        List<Message> allMessages = messageDAO.getAll();
        return allMessages;
    }
    
    public Message delete (int id) {
        try {
            Message presentMessage = getById(id);
            if(presentMessage == null) {
                return null;
            } else {
                messageDAO.delete(presentMessage);
                return presentMessage;
            }
                
        } catch (Exception e) {
            System.out.println("Message cannot be deleted");
        }
        return null;
    }

    public Message update(Message message) {
        int id = message.message_id;
        if(validateMessage(message) == null) {
            return null;
        }
        if(getById(id) == null){
            return null;
        }
        try {
            messageDAO.update(id, message);
            Message updatedMessage = getById(id);
            return updatedMessage;
        } catch (Exception e) {
            System.out.println("Message cannot be updeted");
        }
        return null;
        
    }
}
