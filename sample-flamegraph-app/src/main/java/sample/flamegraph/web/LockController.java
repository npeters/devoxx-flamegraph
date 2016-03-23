package sample.flamegraph.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/lock")
public class LockController {

    private volatile double cpt = 0;


    public void doUpdate() throws Exception{
        cpt = cpt + Math.acos(ThreadLocalRandom.current().nextDouble());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public double lock() throws Exception {
          synchronized (this) {
              for (int i=0;i<100_000;i++) {
                  doUpdate();
          }
        }
        return cpt;
    }


    public static void main(String[] args) throws Exception {

        LockController lock = new LockController();
        ExecutorService executor =  Executors.newFixedThreadPool(100);
        for (int i=0;i<1_000_000;i++) {
            executor.execute(() -> {
                try {
                    lock.lock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.awaitTermination(10, TimeUnit.MINUTES);

    }

}