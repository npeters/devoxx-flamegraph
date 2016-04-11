package sample.flamegraph.tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.text.*;
import java.util.concurrent.*;

/**
 * Inspire by https://github.com/zhenyun/JavaGCworkload
 */
public class GCLoad {
    static int threadNum = 1;
    static int duration = 300; // seconds;  Program will exit after duration of seconds.

    static int referenceSize = 1024 * 5;  // each reference object size;
    static int countDownSize = 1000 * 100;
    static int eachRemoveSize = 1000 * 50; // remove # of elements each time.


    public static String getPid() {
        String jvmName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return jvmName.split("@")[0];
    }

    public static void main(String[] args)
            throws IOException {

        String pid = getPid();
        System.out.println("App pid: " + pid);
        Files.write(Paths.get("/tmp/gcload.pid"),Collections.singletonList(pid), StandardOpenOption.CREATE);


        if (args.length > 0) {
            duration = Integer.parseInt(args[0]);
            threadNum = Integer.parseInt(args[1]);
        }

        for (int i = 0; i < threadNum; i++) {
            LoadThread thread = new LoadThread();
            thread.start();
        }

    }
}

class LoadThread extends Thread {
    long timeZero = System.currentTimeMillis();
    long finishedUnit = 0;

    public LoadThread() {
    }

    public void run() {
        Queue<String> q = new ArrayBlockingQueue<String>(GCLoad.countDownSize);

        finishedUnit = 0;
        long prevTime = timeZero;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSS");
        for (int i = 0; ; i = i + 1) {
            // Simulate object use to force promotion into OldGen and then GC
            if (q.size() >= GCLoad.countDownSize) {
                for (int j = 0; j < GCLoad.eachRemoveSize; j++) {
                    q.remove();
                }
                finishedUnit++;

                // every 1000 removal is counted as 1 unit.
                long curTime = System.currentTimeMillis();
                long timeDiff = curTime - prevTime;
                prevTime = curTime;

                long totalTime = curTime - timeZero;
                if (totalTime > GCLoad.duration * 1000) {
                    System.exit(0);
                }


                Date dNow = new Date();

                System.out.println(ft.format(dNow) + " finished Units = " + finishedUnit);
            }
            char[] srcArray = new char[GCLoad.referenceSize];
            String emptystr = new String(srcArray);
            String str = emptystr.replace('\0', 'a');
            q.add(str);
        }
    }
}
