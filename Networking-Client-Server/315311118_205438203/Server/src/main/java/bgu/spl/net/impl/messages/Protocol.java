package bgu.spl.net.impl.messages;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.User;

public class Protocol implements MessagingProtocol<Message>
{

    private Database database;
    private User user;
    private  boolean terminate; //need to check if what we did with shouldTerminate() is good

    public User getUser() {
        return user;
    }


    public Protocol()
    {
        database=Database.getInstance();
        user=null;
        terminate=false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message process(Message msg)
    {
        return msg.message_process(this);
    }
    ;

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate()
    {
       return terminate;
    }


}


