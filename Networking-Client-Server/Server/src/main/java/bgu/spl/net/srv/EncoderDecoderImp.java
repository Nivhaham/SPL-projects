package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.messages.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncoderDecoderImp implements MessageEncoderDecoder<Message> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    int zerotoreach = -1;
    int numofzero = 0;
    int opcode = 0;


    public Message decodeNextByte(byte nextByte) {

        pushByte(nextByte);

        if(nextByte=='\0'&& len>2)
        {
            numofzero++;
        }

        if (shouldfinish()) {

            return popMessage();
        }



        if (len == 2) {

            this.opcode = ByteToInt(CreateByteArr(bytes[0], bytes[1]));
            SetOpsettings();
            if(opcode==4)
            {
                Resetsettings();
                return new LOGOUT();
            }
            if (opcode==11)
            {
                Resetsettings();
                return  new MYCOURSES();
            }
        }
        return null; //not a line yet
    }


    public byte[] encode(Message message) {
        return message.encodeMessage(); //uses utf8 by default
    }

    private void pushByte(byte nextByte) {

        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    public int ByteToInt(byte[] byteArr) {
        int result = (int) ((byteArr[0] & 0xff) << 8);
        result += (int) (byteArr[1] & 0xff);
        return result;
    }

    public byte[] IntToBytes(int num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }


    private byte[] CreateByteArr(byte one, byte two) {
        byte[] op = new byte[2];
        op[0] = one;
        op[1] = two;
        return op;

    }


    private void SetOpsettings() {


        if (opcode == 1 || opcode == 2 || opcode == 3) {
            this.zerotoreach = 2;
        }

        if (opcode == 8) {
            this.zerotoreach = 1;
        }


    }

    private void Resetsettings() {
        this.len = 0;
        this.numofzero = 0;
        this.zerotoreach = -1;
        this.opcode = 0;
    }
//5 6 7 9 10

    private boolean shouldfinish() {


        if (opcode == 1 || opcode == 2 || opcode == 3 || opcode == 8) {
            if (numofzero == zerotoreach) {
                return true;
            }
        }

        if (opcode >= 5 && opcode <= 10 && opcode != 8) {
            if (len == 4) {
                return true;
            }
        }

        return false;

    }



    public Message popMessage() {
        if (opcode == 1) {
            String[] details = UserAndPass();
            return new ADMINREG(details[0], details[1]);
        }
        if (opcode == 2) {
            String[] details = UserAndPass();
            return new STUDENTREG(details[0], details[1]);
        }
        if (opcode == 3) {
            String[] details = UserAndPass();
            return new LOGIN(details[0], details[1]);
        }
        if (opcode == 5) {

            return new COURSEREG(getCourseNum());
        }
        if(opcode==6)
        {
            return new KDAMCHECK(getCourseNum());
        }
        if (opcode==7)
        {
            return new COURSESTAT(getCourseNum());
        }
        if(opcode==8)
        {
            String[] details = UserAndPass();
            return new STUDENTSTAT(details[0]);
        }
        if(opcode==9)
        {
            return new ISREGISTERED(getCourseNum());
        }

            return new UNREGISTER(getCourseNum()); //opcode=10

    }

    private String[] UserAndPass() {

        String message = new String(bytes, 2, len, StandardCharsets.UTF_8);
        Resetsettings();
        return message.split("\0");

    }

    private int getCourseNum()
    {
        Resetsettings();
      return ByteToInt(CreateByteArr(bytes[2],bytes[3]));
    }


}