package bgu.spl.net.impl.messages;

import bgu.spl.net.srv.Database;

import java.io.Serializable;

public abstract class Message<T> implements Serializable
{
    protected int opcode;
    protected Database database;
    public Message(int opcode){
        this.opcode = opcode;
        this.database=  Database.getInstance();
    }

    public int getOpcode(){
        return opcode;
    }

    public byte[] IntToBytes(int num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }

    public abstract Message message_process(Protocol p);

    public abstract byte[] encodeMessage();






}