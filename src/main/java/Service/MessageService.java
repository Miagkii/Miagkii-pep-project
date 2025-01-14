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

    public Message create(Message message) {

        if(accountService.accountIsPresent(message.posted_by) == false) {
            return null;
        }
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

    public List<Message> getAll(){
        List<Message> allMessages = messageDAO.getAll();
        return allMessages;
    }
    
    
}
