package spectatorclient.Communication;

public class MessageException extends Exception{

    /**
     * Message with exception.
     */
    private Message msg;

    /**
     * Instantiation of message.
     *
     *   @param errorMessage text with the error message
     *   @param msg message with the exception
     */
    public MessageException(String errorMessage, Message msg) {
        super(errorMessage);
        this.msg = msg;
    }

    /**
     * Obtaining the message.
     *
     *   @return mensage with error
     */
    public Message getMessageVal() {
        return (msg);
    }
}
