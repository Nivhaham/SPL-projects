package bgu.spl.net.impl.messages;

public class STUDENTREG extends Message<Integer>
{
    String username;
    String password;

    public STUDENTREG(String username, String password) {
        super(2);
        this.username=username;
        this.password=password;
    }


    @Override
    public Message message_process(Protocol p) {

       // System.out.println("byte");
        if((p.getUser()!=null && !p.getUser().isLogin()) || p.getUser()==null)
        {
            if(database.student_reg(username,password))
            {
                return new ACK(opcode,null);//ack message
            }
        }

        return new ERR(opcode);// return error
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
