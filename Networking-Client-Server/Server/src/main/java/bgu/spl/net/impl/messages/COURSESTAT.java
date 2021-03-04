package bgu.spl.net.impl.messages;


// Admin
public class COURSESTAT extends Message<Integer>
{ private int course_index;


    public COURSESTAT(int course_index) {

        super(7);
        this.course_index=course_index;
    }


    @Override
    public Message message_process(Protocol p) {
        if(p.getUser()!=null && p.getUser().isAdmin() && p.getUser().isLogin())
        {
            return new ACK(opcode,database.get_course_stat(course_index));//need to return ack with the string that get_course_stat returns
        }
        else
        {
            return new ERR(opcode);//need to return error
        }


    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
