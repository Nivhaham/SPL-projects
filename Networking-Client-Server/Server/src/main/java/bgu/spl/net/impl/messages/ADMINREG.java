package bgu.spl.net.impl.messages;

public class ADMINREG extends Message<Integer> {
    String username;
    String password;

    public ADMINREG(String username, String password) {
        super(1);
        this.username=username;
        this.password=password;
    }


    @Override
    public Message message_process(Protocol p) {

       if(p.getUser()!=null &&p.getUser().isLogin())
       {
           return new ERR(opcode);
       }

        if(database.admin_reg(username,password))
        {
            return new ACK(opcode,null);
        }

        return new ERR(opcode);
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
