package bgu.spl.net.impl.messages;

public class UNREGISTER extends Message<Integer>
{

    private int course_index;
    public UNREGISTER( int course_index) {

        super(9);
        this.course_index=course_index;

    }


    @Override
    public Message message_process(Protocol p) {
        if(p.getUser()!=null && p.getUser().isLogin() && !p.getUser().isAdmin()&& database.isExist(course_index))
        {
            if(database.is_registered(course_index,p.getUser().getUsername()).equals("REGISTERED"))
            {
                if(database.unregister(course_index,p.getUser().getUsername()))
                {
                    return new ACK(opcode,null); //return ack
                }
            }
        }


        return new ERR(opcode); //return error
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
