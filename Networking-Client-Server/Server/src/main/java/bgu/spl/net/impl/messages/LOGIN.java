package bgu.spl.net.impl.messages;

//import bgu.spl.net.srv.User;

public class LOGIN extends Message<Integer>
{
    String username;
    String password;

    public LOGIN(String username, String password) {
        super(3);
        this.username=username;
        this.password=password;
    }

    @Override
    public Message message_process(Protocol p) {

        if(p.getUser()!=null && p.getUser().isLogin())
        {
            return new ERR(opcode);//need to return error because user is already logged in
        }

       p.setUser(database.user_login(username,password));
       if(p.getUser()!=null)
       {
           return new ACK(opcode,null);// need to return ack
       }

        return new ERR(opcode); //need to return error
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
