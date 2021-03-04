package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.WellDoneCast;
import bgu.spl.mics.application.services.C3POMicroservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusTest {
    /*private C3POMicroservice m;
    private MessageBusImpl bus;
    private AttackEvent attack;
    private WellDoneCast broadmessage;
    private Future<T> future;

    @BeforeEach
    void setUp()
    {
        m = new C3POMicroservice();
        bus = new MessageBusImpl();
        attack = new AttackEvent();
        broadmessage = new WellDoneCast();
        future = new Future<T>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void subscribeEvent()
    {
        assertFalse(attack.isSub(m));
        bus.subscribeEvent(attack.getClass(),m);
        assertTrue(attack.isSub(m));
    }

    @Test
    void subscribeBroadcast()
    {
        assertFalse(attack.isSub(m));
        bus.subscribeBroadcast(broadmessage.getClass(),m);
        assertTrue(attack.isSub(m));
    }

    @Test
    void complete()
    {
        m.complete(attack,true);
        assertEquals(true,future.isDone());
    }

    @Test
    void sendBroadcast()
    {
        int x;
        x = bus.getRegQueue(m).size();
        bus.subscribeBroadcast(broadmessage.getClass(),m);
        bus.sendBroadcast(broadmessage);
        assertEquals(bus.getRegQueue(m).size(),x-1);

    }

    @Test
    void sendEvent()
    {
        int x;
        x = bus.getRegQueue(m).size();
        bus.subscribeEvent(attack.getClass(),m);
        bus.sendEvent(attack);
        assertEquals(bus.getRegQueue(m).size(),x-1);
    }

    @Test
    void register()
    {
        bus.register(m);
        assertTrue(bus.isReg(m));
    }

    @Test
    void unregister()
    {
        bus.register(m);
        bus.unregister(m);
        assertTrue(bus.isReg(m));
    }

    @Test
    void awaitMessage()
    {
        int x;
        x = bus.getRegQueue(m).size();
        bus.register(m);
        bus.subscribeEvent(attack.getClass(), m);
        bus.sendEvent(attack);
        try {
            bus.awaitMessage(m);
        } catch (InterruptedException e) {}
        assertEquals(x-1,bus.getRegQueue(m).size());

    }*/
}