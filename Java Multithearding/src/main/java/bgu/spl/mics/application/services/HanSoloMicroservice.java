package bgu.spl.mics.application.services;


import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Main;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminationBroadCast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.List;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {
    private MicroService HanSoloMicroservice;
    private Ewoks ewoks_singleton = Ewoks.getInstance();


    public HanSoloMicroservice() {

        super("Han");
    }


    @Override
    protected void initialize()
    {
        bus.register(this);
        subscribeEvent(AttackEvent.class,att->{
            List<Integer> resources = att.getAttack().getserials();
            ewoks_singleton.acquire(resources);
            try {
                Thread.sleep(att.getAttack().getDuration());


            } catch (Exception e) {

            }
            diary.totalAttacks.getAndIncrement();
            diary.setHanSoloFinish(System.currentTimeMillis());
            complete(att,true);//TODO: finish this
            LeiaMicroservice.num_of_attacks.getAndIncrement();
            sendeactivation();
                }
        ) ;
        subscribeBroadcast(TerminationBroadCast.class, e->{this.terminate();
        diary.setHanSoloTerminate(System.currentTimeMillis());
          // System.out.println("han terminated");

        }
            );
        Main.latch.countDown();

    }
}
