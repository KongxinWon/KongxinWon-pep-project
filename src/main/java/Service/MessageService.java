package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {

    private final MessageDAO messageDAO;
    private final AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    //create new messages
    public Message createMessage(Message message) {
        if (message == null) return null;

        String text = message.getMessage_text();
        if (text == null || text.isBlank() || text.length() > 255) {
            return null;
        }

        if (accountDAO.getAccountById(message.getPosted_by()) == null) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    //retrieve all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    //retrieve messages by ID
    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    //delete messages by ID
    public Message deleteMessage(int messageId) {
        Message existing = messageDAO.getMessageById(messageId);
        if (existing == null) return null;

        boolean deleted = messageDAO.deleteMessageById(messageId);
        if (deleted) return existing;
        else return null;
    }

    //updates message texts by ID
    public Message updateMessage(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return null;
        }

        Message existing = messageDAO.getMessageById(messageId);
        if (existing == null) return null;

        boolean updated = messageDAO.updateMessageText(messageId, newText);
        if (updated) {
            existing.setMessage_text(newText);
            return existing;
        }
        return null;
    }

    //retrieve messages with account ID
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}
