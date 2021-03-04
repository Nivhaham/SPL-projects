package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.Database;

import javax.xml.crypto.Data;
import java.util.Vector;

public class COURSEREG extends Message<Integer>
{
    private int course_index;

    public COURSEREG(int course_index) {
        super(5);
        this.course_index=course_index;
    }


    @Override
    public Message message_process(Protocol p) {

        Database DB=Database.getInstance();

        if(p.getUser()!=null && p.getUser().isLogin() && !p.getUser().isAdmin() && database.isExist(course_index) ) {
            Vector<Integer> kdams = database.get_kdam_courses_of_course(course_index);
                if (p.getUser().gotKdams(kdams)) {
                    if (database.course_reg(course_index, p.getUser().getUsername())) {
                        return new ACK(opcode,null);
                    }

                }



        }


        return new ERR(opcode);
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
