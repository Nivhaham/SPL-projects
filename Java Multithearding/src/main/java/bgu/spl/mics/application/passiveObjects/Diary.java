package bgu.spl.mics.application.passiveObjects;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 */
public class Diary
{
    private static class SingletonHolder {
        private static Diary instance = new Diary();
    }
    public AtomicInteger totalAttacks = new AtomicInteger(0);
    public long HanSoloFinish;
    public long C3POFinish;
    public long R2D2Deactivate;
    public long LeiaTerminate;
    public long HanSoloTerminate;
    public long C3POTerminate;
    public long R2D2Terminate;
    public long LandoTerminate;
    public long Attacks_difference;

    public Diary(){
    }
    public static Diary getInstance(){
        return SingletonHolder.instance;
    }
    public void setTotalAttacks(AtomicInteger totalAttacks) {
        this.totalAttacks = totalAttacks;
    }

    public void setHanSoloFinish(long hanSoloFinish) {
        HanSoloFinish = hanSoloFinish;
    }

    public void setC3POFinish(long c3POFinish) {
        C3POFinish = c3POFinish;
    }

    public void setR2D2Deactivate(long r2D2Deactivate) {
        R2D2Deactivate = r2D2Deactivate;
    }

    public void setLeiaTerminate(long leiaTerminate) {
        LeiaTerminate = leiaTerminate;
    }

    public void setHanSoloTerminate(long hanSoloTerminate) {
        HanSoloTerminate = hanSoloTerminate;
    }

    public void setC3POTerminate(long c3POTerminate) {
        C3POTerminate = c3POTerminate;
    }

    public void setR2D2Terminate(long r2D2Terminate) {
        R2D2Terminate = r2D2Terminate;
    }

    public void setLandoTerminate(long landoTerminate) {
        LandoTerminate = landoTerminate;
    }

    public void setAttacks_difference(long attacks_difference) { Attacks_difference = attacks_difference; }


    public AtomicInteger getNumberOfAttacks() {
        return totalAttacks;
    }

    public long getHanSoloFinish() {
        return HanSoloFinish;
    }

    public long getC3POFinish() {
        return C3POFinish;
    }

    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }

    public long getLeiaTerminate() {
        return LeiaTerminate;
    }

    public long getHanSoloTerminate() {
        return HanSoloTerminate;
    }

    public long getC3POTerminate() {
        return C3POTerminate;
    }

    public long getLandoTerminate() {
        return LandoTerminate;
    }

    public long getR2D2Terminate() {
        return R2D2Terminate;
    }


    public void resetNumberAttacks()
    {
        totalAttacks.set(0);

    }

    public String toString() {
        String excpected_output = "There are : " + totalAttacks +  " attacks "+
                " HanSolo and C3PO finished after "+ Attacks_difference + " milliseconds one after another " +
                " Our Great Heroes have done it "
                ;
        return excpected_output;
    }



}
