package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Main;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminationBroadCast;
import java.lang.Math;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
     long duration;
    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration=duration;
    }

    @Override
    protected void initialize() {

        bus.register(this);

       subscribeEvent(BombDestroyerEvent.class,e->{
           try {
               Thread.sleep(duration);
           } catch (Exception exception) {

           }
           complete(e,true);
           sendBroadcast(new TerminationBroadCast());
       });
        subscribeBroadcast(TerminationBroadCast.class,e->{this.terminate();
        diary.setLandoTerminate(System.currentTimeMillis());
        diary.setAttacks_difference(Math.abs(diary.getC3POFinish()-diary.getHanSoloFinish()));
          //  System.out.println("lando terminated");
        });
        Main.latch.countDown();
    }
}
