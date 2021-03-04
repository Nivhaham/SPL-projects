package bgu.spl.mics.application;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import bgu.spl.mics.*;

import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import bgu.spl.mics.application.messages.*;
import com.google.gson.Gson;
import java.util.concurrent.CountDownLatch;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static CountDownLatch latch= new CountDownLatch(4);

	public static void main(String[] args) throws IOException
	{



		//C:\Users\xfaro\Desktop\StarWars\SPL211\input.json
		//"C:\\Users\\niv_d\\IdeaProjects\\Assignment2\\SPL211\\input.json"
		Diary dairy = Diary.getInstance();
		JsonInputReader reader = new JsonInputReader();

	//	Input input = reader.getInputFromJson("C:\\Users\\xfaro\\Desktop\\StarWars\\SPL211\\input.json");
		Input input = reader.getInputFromJson(args[0]);
		Gson expected_output = new Gson();
		String output;//=("C:\\Users\\xfaro\\Desktop\\StarWars\\SPL211\\output.json"); // args[1]
		//FileWriter writer = new FileWriter(output);
		//expected_output.toJson(dairy,writer);

		Attack[] attacks = input.getAttacks();
		long R2D2Duration = input.getR2D2();
		long LandoDuration = input.getLando();
		//int num_of_ewoks = input.getEwoks();
		Ewoks.set_num_of_Ewoks(input.getEwoks());
		long termination_time ;
		long startingTime=System.currentTimeMillis();


		Thread HanSolo = new Thread(new HanSoloMicroservice());
		Thread C3PO = new Thread(new C3POMicroservice());
		Thread R2D2 = new Thread(new R2D2Microservice(R2D2Duration));
		Thread Lando = new Thread(new LandoMicroservice(LandoDuration));
		Thread Leia = new Thread(new LeiaMicroservice(attacks));



		// Leia need to sleep at first because need to let other subscribe for events first
		Leia.start();
		HanSolo.start();
		C3PO.start();
		R2D2.start();
		Lando.start();


		try {


			Leia.join();
			HanSolo.join();
			C3PO.join();
			R2D2.join();
			Lando.join();
		} catch (InterruptedException e) {

		}


		//System.out.println(dairy.toString());
		termination_time=System.currentTimeMillis()-startingTime;
		//System.out.println("All threads terminate "+termination_time+ " milliseconds later.");
		output = expected_output.toJson(dairy.toString())+"All threads terminate "+termination_time+" milliseconds later.";
		System.out.println(output);
		FileWriter x=	new FileWriter(args[1]);
		//FileWriter x=	new FileWriter("C:\\Users\\xfaro\\Desktop\\StarWars\\SPL211\\output.json");
		expected_output.toJson(output,x);
		x.flush();
		x.close();

	}
}
