package bgu.spl.mics.application.passiveObjects;
/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;
	public Ewok(int ewok_serial_num)
    {
        serialNumber=ewok_serial_num;
        available=true;
    }

  
    /**
     * Acquires an Ewok
     */
    public synchronized void acquire()
    {

        try {
            while(!available) {
                wait();
            }
            available=true;
        }
        catch (Exception e) {

        }
    }

    /**
     * release an Ewok
     */
    public synchronized void release()
    {

        this.available=true;
        notifyAll();
    }

    public Ewok getewok()
    {
        return this;
    }

    public int getSerialNumber()
    {
        return serialNumber;
    }
    public boolean getavailable()
    {
        return available;
    }

}
