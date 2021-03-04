package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.messages.Protocol;
import bgu.spl.net.srv.Database;
import bgu.spl.net.srv.EncoderDecoderImp;
import bgu.spl.net.srv.Reactor;
import bgu.spl.net.srv.Server;

public class ReactorMain {

    public static void main(String[] args) {

        int ThreadsNum=Integer.parseInt(args[1]);
       // int port=Integer.parseInt("7777");
        int port= Integer.parseInt(args[0]);
        Database data=Database.getInstance();
        Server ReactorServer=new Reactor(ThreadsNum,port,()-> new Protocol(),()->new EncoderDecoderImp());

        ReactorServer.serve();

    }
}
