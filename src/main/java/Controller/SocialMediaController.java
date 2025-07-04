package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();


    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //account endpoints
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        //message endpoints
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    //user handler for registration
    private void registerHandler(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account result = accountService.register(account);
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(400);
        }
    }

    //user login handler
    private void loginHandler(Context ctx) {
        Account credentials = ctx.bodyAsClass(Account.class);
        Account result = accountService.login(credentials.getUsername(), credentials.getPassword());
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(401);
        }
    }

    //message creation handler
    private void createMessageHandler(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message result = messageService.createMessage(message);
        if (result != null) {
            ctx.json(result);
        } else {
            ctx.status(400);
        }
    }

    //retreive all messages
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    //retreive by ID
    private void getMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.json(""); 
        }
    }

    //delete by ID
    private void deleteMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = messageService.deleteMessage(messageId);
        if (deleted != null) {
            ctx.json(deleted);
        } else {
            ctx.json(""); 
        }
    }

    //update text by ID
    private void updateMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message newMessageData = ctx.bodyAsClass(Message.class);
        Message updated = messageService.updateMessage(messageId, newMessageData.getMessage_text());
        if (updated != null) {
            ctx.json(updated);
        } else {
            ctx.status(400);
        }
    }

    //retrieve message by specific user
    private void getMessagesByAccountHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        ctx.json(messages);
    }

}

