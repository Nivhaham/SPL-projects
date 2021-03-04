package bgu.spl.mics.application.messages;
import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MicroService;

public class WellDoneCast implements Broadcast
{

    public WellDoneCast()
    {
    //    System.out.println("Good Job guys Mission Accomplished");
    }
    public boolean isSub(MicroService m) {
        return false;
    }
}
