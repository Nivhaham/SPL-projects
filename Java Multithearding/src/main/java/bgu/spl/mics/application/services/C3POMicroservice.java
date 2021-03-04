package bgu.spl.mics.application.services;
import  bgu.spl.mics.application.Main;
import bgu.spl.mics.Callback;
import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Main;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminationBroadCast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.List;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {
    private MicroService C3POMicroservice;
    private Ewoks ewoks_singleton = Ewoks.getInstance();
    public C3POMicroservice()  {
        super("C3PO");
    }
    //MessageBus

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
            diary.setC3POFinish(System.currentTimeMillis());


            complete(att,true); //TODO: finish this
            LeiaMicroservice.num_of_attacks.getAndIncrement();
          sendeactivation();
        });
        subscribeBroadcast(TerminationBroadCast.class, e->{this.terminate();
        diary.setC3POTerminate(System.currentTimeMillis());
          //  System.out.println("c3 terminated");
        });
        Main.latch.countDown();
    }

}
