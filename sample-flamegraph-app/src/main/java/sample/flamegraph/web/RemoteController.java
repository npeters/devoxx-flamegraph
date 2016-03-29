package sample.flamegraph.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/remote")
public class RemoteController {

    int WAIT = 5;
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String remote()  throws Exception{

       //if  (ThreadLocalRandom.current().nextInt() % 10 == 0 ){
           Thread.sleep(WAIT*10);
       //}
        return "Ok";

    }


}
