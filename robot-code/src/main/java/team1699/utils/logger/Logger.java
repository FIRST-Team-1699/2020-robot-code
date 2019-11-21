package team1699.utils.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Logger implements Runnable{

    private static Logger instance;

    public Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    private final List<Loggable> alwaysLogged;
    private final Queue<String> toLog;
    private boolean running = false;
    private Thread thread;
    private int ticks = 0;
    private final FileWriter writer; 

    private Logger(){
        alwaysLogged = new ArrayList<>();
        toLog = new ConcurrentLinkedQueue<>();
	try{
		writer = new FileWriter("/home/lvuser/logs/" + Timestamp.from(Instant.now()).toString() + ".txt"); 
	}catch (Expection e){
		e.printStackTrace();
	}
    }

    @Override
    public void run() {
        while(running){
	    //Writes to the log every 20 ticks
	    if(ticks % 20 == 0){
		   //Log
		   for(Loggable l : alwaysLogged){
			  writer.write(l.getLogOutput());
		   }
		   writer.write(toLog.poll());
		   writer.flush(); //We only want to flush where the writer has stuff in it
	    }
	    ticks++;
        }
    }

    public synchronized void close(){
	    writer.close();
    }

    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
