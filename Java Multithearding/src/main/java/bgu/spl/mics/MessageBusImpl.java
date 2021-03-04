package bgu.spl.mics;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private static class SingletonHolder {
		private static MessageBusImpl instance = new MessageBusImpl();
	}
	private ConcurrentHashMap<MicroService, BlockingQueue<Message>> microservice_to_messages;
	private ConcurrentHashMap<Class<? extends Message>,Queue<MicroService>> messagetype_to_microservice;
	private ConcurrentHashMap<Event, Future> event_to_future;


	public MessageBusImpl()
	{
		microservice_to_messages = new ConcurrentHashMap<MicroService, BlockingQueue<Message>>();
		messagetype_to_microservice = new ConcurrentHashMap<Class<? extends Message>,Queue<MicroService>>();
		event_to_future = new ConcurrentHashMap<Event, Future>();

	}
	public static MessageBusImpl getInstance(){
		return SingletonHolder.instance;
	}

	@Override
	public  <T> void  subscribeEvent(Class<? extends Event<T>> type, MicroService m)
	{
	//	System.out.println(type);
		messagetype_to_microservice.putIfAbsent(type,new ConcurrentLinkedQueue<>());

			Queue<MicroService> current = messagetype_to_microservice.get(type);
		synchronized (current){
			if(!current.contains(m))
				current.add(m);
		}

	}

	@Override
	public  void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m)
	{

		messagetype_to_microservice.putIfAbsent(type,new ConcurrentLinkedQueue<>());

			Queue<MicroService> current = messagetype_to_microservice.get(type);
		synchronized (current){
			if(!current.contains(m))
				current.add(m);
		}


    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result)
	{
		/*
		Future future = new Future();
		future.resolve(result);
		event_to_future.put(e,future);*/
		event_to_future.get(e).resolve(result);

	}

	@Override
	public  void sendBroadcast(Broadcast b)
	{
	//	System.out.println("enter sendBroadcast ");

		Queue<MicroService> x;
		if(messagetype_to_microservice.containsKey(b.getClass())) {
			x = messagetype_to_microservice.get(b.getClass());
			//TODO: Check if this synchronized is good
			synchronized (x) {
				for (MicroService i : x) {
					//TODO: Check if this synchronized is good


					if (microservice_to_messages.containsKey(i)) {
						microservice_to_messages.get(i).add(b);
					}

				}
			}
		}
	//	System.out.println("exit sendBroadcast ");
	}
	
	@Override
	public   <T> Future<T> sendEvent(Event<T> e)
	{
		//System.out.println("enter sendEvent ");
		Future<T> future = new Future<>();
		event_to_future.put(e,future);
		//System.out.println(	e.getClass()+" send event");
		//System.out.println(	messagetype_to_microservice.get(e.getClass()));
		synchronized (this){//TODO:not good
		if(messagetype_to_microservice.containsKey(e.getClass()) && !messagetype_to_microservice.get(e.getClass()).isEmpty() ) {
			Queue<MicroService> x = messagetype_to_microservice.get(e.getClass());
			synchronized (x) {

				MicroService m = messagetype_to_microservice.get(e.getClass()).poll();
				if(microservice_to_messages.get(m)!=null) {
					microservice_to_messages.get(m).add(e);
					messagetype_to_microservice.get(e.getClass()).add(m);
					//		System.out.println("Added message");
				}
			}
			return future;
		}
		}
        return null;
}


	@Override
	public void register(MicroService m)
	{
		microservice_to_messages.putIfAbsent(m,new LinkedBlockingQueue<Message>());
	}

	@Override
	public void unregister(MicroService m)
	{
		//TODO: maybe need to synchronized while other things wanna do staff while m's queue is being delete
		microservice_to_messages.remove(m);



		for(Queue<MicroService> x:messagetype_to_microservice.values())
		{
			x.remove(m);
		}


	}

	@Override
	public  Message awaitMessage(MicroService m) throws InterruptedException , IllegalStateException
    {

    	   if(microservice_to_messages.containsKey(m))
			return	getInstance().microservice_to_messages.get(m).take();
    	   return null;
	}

}
