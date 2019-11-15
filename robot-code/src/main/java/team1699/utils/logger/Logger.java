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

    private Logger(){
        alwaysLogged = new ArrayList<>();
        toLog = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        while(running){
            //TODO log
        }
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
