package bgu.spl.net.impl.messages;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ACK extends Message<Integer> {
    private int message_opcode;
    private String server_answer;

    public ACK(int message_opcode,String server_answer) {
        super(12);
        this.message_opcode=message_opcode;
        this.server_answer=server_answer;

    }


    @Override
    public Message message_process(Protocol p) {
        return null;
    }

    @Override
    public byte[] encodeMessage() {

        byte [] ackop=IntToBytes(12);
        byte [] messageOP= IntToBytes(message_opcode);
        byte [] stringmessage;
        if(server_answer!=null) {

           stringmessage = (server_answer+"\0").getBytes();

        }
        else
        {
            stringmessage=stringmessage = ("\0").getBytes();
        }
        byte [] combined=new byte[4+stringmessage.length];

        System.arraycopy(ackop,0,combined,0,2);
        System.arraycopy(messageOP,0,combined,2,2);
        System.arraycopy(stringmessage,0,combined,4,stringmessage.length);
        return combined;

    }
}
