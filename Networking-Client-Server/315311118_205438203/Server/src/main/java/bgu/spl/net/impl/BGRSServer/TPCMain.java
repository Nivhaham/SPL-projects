package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.messages.Protocol;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.EncoderDecoderImp;
import bgu.spl.net.srv.Server;

import javax.print.attribute.standard.Severity;

public class TPCMain {

    public static void main(String[] args) {

        Database data=Database.getInstance();
       // int port= Integer.parseInt(args[0]);
        int port= Integer.parseInt("7777");
        Server.threadPerClient(port
        ,()->new Protocol(),()->new EncoderDecoderImp()).serve();
    }
}
