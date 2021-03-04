package bgu.spl.mics.application.passiveObjects;
import java.util.*;
import java.util.List;
import bgu.spl.mics.JsonInputReader;
import com.google.gson.Gson;
import bgu.spl.mics.Input;
//import com.sun.tools.javac.util.List;
import java.util.*;
/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks
{

    private static class SingletonHolder {
        private static Ewoks instance = new Ewoks();
    }


    private static  int num_of_ewoks;
    private Ewok[]  ewoks;
    //private static final num_of_ewok;


    public Ewoks()
    {
        ewoks=new Ewok[Ewoks.num_of_ewoks];

        for(int i=0;i<ewoks.length;i++)
        {
            ewoks[i]=new Ewok(i+1);
        }
    }
    public static void set_num_of_Ewoks(int num) {
        num_of_ewoks=num;
    }

    public static Ewoks getInstance() {

        return SingletonHolder.instance;
    }

    public void acquire(List<Integer> ewoks_needto_attack)
    {
       //ewoks_needto_attack.sort(Integer::compareTo);

        for(Integer i:ewoks_needto_attack)
        {
            ewoks[i-1].acquire();
        }

    }



    public  void release(List<Integer> ewoks_needto_attack)
    {

        for(Integer i:ewoks_needto_attack)
        {
            ewoks[i-1].release();
        }

    }

    }

