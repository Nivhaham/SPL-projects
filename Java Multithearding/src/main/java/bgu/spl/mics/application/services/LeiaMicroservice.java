package bgu.spl.mics.application.services;
import bgu.spl.mics.application.Main;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminationBroadCast;
import bgu.spl.mics.application.passiveObjects.Attack;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
	public  static AtomicInteger num_of_attacks;
    public  static boolean deactivation=false;
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		num_of_attacks=new AtomicInteger();
    }

    public static long setAttacks(Attack[] attacks)
    {
        return attacks.length;
    }


    @Override
    protected void initialize() {
        //todo: might need to create broadcast that send the attacks
        bus.register(this);
        try
        {
            Main.latch.await();
        }
       catch(Exception ex)
        {

        }
        for (Attack x:attacks)
        {
            AttackEvent a=  new AttackEvent(x);
          //  System.out.println(a.getClass()+" leia");
            bus.sendEvent(a);
        }
        subscribeBroadcast(TerminationBroadCast.class,e->{this.terminate();
            diary.setLeiaTerminate(System.currentTimeMillis());
            //System.out.println("leia terminated");
        });
    }
}
