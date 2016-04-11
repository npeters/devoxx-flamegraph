package sample.flamegraph.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/myLock")
public class LockController {

    // on place cpt en attribut de la class pour pas le JIT optimise la methode myLock
    volatile int cpt = 0;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public double myLock() throws Exception {

        synchronized (this) {
            for (int i=0;i<1_000_000;i++) {
                if (i % 2 == 0){
                    cpt = 1;
                }else{
                    cpt = -1;
                }
            }
        }
        return cpt;
    }


    public static void main(String[] args) throws Exception {
        String pid = getPid();
        Files.write(Paths.get("/tmp/lock.pid"), Collections.singletonList(pid), StandardOpenOption.CREATE);

        LockController lock = new LockController();

        while (true) {
            List<Thread> list = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                Thread t = new Thread(() -> {
                    try {
                        long start = System.currentTimeMillis();
                        lock.myLock();
                        long duration = System.currentTimeMillis() - start;
                        System.out.println(String.format("%s", duration));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                t.start();
                list.add(t);
            }
            list.forEach( t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }  );

        }

    }



    public static String getPid() {
        String jvmName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return jvmName.split("@")[0];
    }
}
