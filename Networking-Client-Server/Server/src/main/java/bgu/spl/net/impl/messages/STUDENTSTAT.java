package bgu.spl.net.impl.messages;

public class STUDENTSTAT extends Message<Integer>
{

    String StudentUserName;
    public STUDENTSTAT(String StudentUserName) {
        super(8);
        this.StudentUserName=StudentUserName;
    }


    @Override
    public Message message_process(Protocol p) {

        if(p.getUser()!=null && p.getUser().isAdmin() && p.getUser().isLogin())
        {
            String stat=database.GetStudentStat(StudentUserName);
            if(stat!=null)
            {
                return new ACK(opcode,stat);//need to return ack
            }
        }
        return new ERR(opcode); //need to return error
    }

    @Override
    public byte[] encodeMessage() {
        return new byte[0];
    }
}
