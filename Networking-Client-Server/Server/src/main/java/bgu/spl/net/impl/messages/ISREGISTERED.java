package bgu.spl.net.impl.messages;

public class ISREGISTERED extends Message<Integer>
{
    private int course_index;
    public ISREGISTERED(int course_index) {
        super(9);
        this.course_index=course_index;
    }


    @Override
    public Message message_process(Protocol p) {

        if(p.getUser()!=null && p.getUser().isLogin() && !p.getUser().isAdmin() && database.isExist(course_index))
        {

            return new ACK(opcode,database.is_registered(course_index,p.getUser().getUsername()));//return ack message with the string return from is_registered
        }
        return new ERR(opcode);//return error
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
