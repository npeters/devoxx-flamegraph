package sample.flamegraph.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

/**
 * wait 50ms before return OK
 */
@Controller
@RequestMapping("/remote")
public class RemoteController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String remote()  throws Exception{
        Thread.sleep(50);
        return "Ok";
    }


    @RequestMapping(path = "/open", method = RequestMethod.GET)
    @ResponseBody
    public String open(@RequestParam("file") String file)  throws Exception{

         new ProcessBuilder().command("google-chrome",  file).start();

        return "Ok";
    }



}
