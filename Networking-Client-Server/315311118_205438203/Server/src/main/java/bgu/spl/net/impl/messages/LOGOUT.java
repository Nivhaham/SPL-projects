package bgu.spl.net.impl.messages;


public class LOGOUT extends Message<Integer>
{
    public LOGOUT() {
        super(4);
    }


    @Override
    public Message message_process(Protocol p) {
        if (p.getUser()!=null && p.getUser().isLogin()==true)
        {
            p.getUser().setLogin(false);
            p.setTerminate(false);
            return new ACK(opcode,null);
            //return ACK;
        }

        return new ERR(opcode);      //return ERR;  not even logged in
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
