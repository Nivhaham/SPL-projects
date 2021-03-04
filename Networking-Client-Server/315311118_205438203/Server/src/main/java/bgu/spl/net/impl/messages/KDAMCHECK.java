package bgu.spl.net.impl.messages;

import java.util.Vector;

public class KDAMCHECK extends Message<Integer>
{

    int course_index;

    public KDAMCHECK(int course_index) {
        super(6);
        this.course_index=course_index;
    }


    @Override
    public Message message_process(Protocol p) {
        if(p.getUser()!=null && p.getUser().isLogin() && !p.getUser().isAdmin() && database.isExist(course_index)) {


            Vector<Integer> x = database.get_kdam_courses_of_course(course_index);
            if (x != null) {
                return new ACK(opcode,x.toString().replaceAll(" ",""));// return ack with x.toString();
            }
            else
            {
                return new ACK(opcode,null);
            }

        }

        return new ERR(opcode);
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
