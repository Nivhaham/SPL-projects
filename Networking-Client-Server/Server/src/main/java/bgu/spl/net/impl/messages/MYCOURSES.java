package bgu.spl.net.impl.messages;

public class MYCOURSES extends Message<Integer>
{


    public MYCOURSES() {
        super(11);
    }


    @Override
    public Message message_process(Protocol p) {
        if(p.getUser()!=null && p.getUser().isLogin() && !p.getUser().isAdmin())
        {
            p.getUser().getCoursesString();
            return new ACK(opcode, p.getUser().getCoursesString());// return ack with the course string
        }
        return new ERR(opcode); //return error message
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
