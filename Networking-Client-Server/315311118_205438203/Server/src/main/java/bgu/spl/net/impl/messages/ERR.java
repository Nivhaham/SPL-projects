package bgu.spl.net.impl.messages;

public class ERR extends Message<Integer> {
    private int messageOP;

    public ERR(int messageOP) {
        super(13);
        this.messageOP = messageOP;

    }


    @Override
    public Message message_process(Protocol p) {
        return null;
    }

    @Override
    public byte[] encodeMessage() {
        byte[] ackop = IntToBytes(13);
        byte[] message_opcode = IntToBytes(messageOP);
        byte[] combined = new byte[4];
        combined[0] = ackop[0];
        combined[1] = ackop[1];
        combined[2] = message_opcode[0];
        combined[3] =message_opcode[1];

        System.out.println(combined[0]);
        System.out.println(  combined[1]);
        System.out.println(  combined[2]);
        System.out.println(  combined[3]);
        return combined;



    }
}
