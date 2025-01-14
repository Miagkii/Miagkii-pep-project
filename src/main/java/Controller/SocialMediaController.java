package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::loginAccount);
        app.post("/messages", this::createdMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);

        return app;
    }
    
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registerAccount = accountService.registration(account);
        if (registerAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(registerAccount));
        }
    }

    private void loginAccount(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedAccount = accountService.login(account);
        if(loggedAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(loggedAccount));
        }

    }

    private void createdMessageHandler(Context ctx) throws JsonProcessingException { 
        ObjectMapper mapper = new ObjectMapper();
        Message mapMessage = mapper.readValue(ctx.body(), Message.class);
        Message message = messageService.create(mapMessage);
        if(message == null){
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(message));
        }
       
    }

    public void getAllMessagesHandler(Context ctx) {
        List<Message> allMessages = messageService.getAll();
        ctx.json(allMessages);
    }


}