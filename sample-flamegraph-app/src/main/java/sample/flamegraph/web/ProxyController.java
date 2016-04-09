package sample.flamegraph.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Call a remote service and return the status code
 */
@Controller
@RequestMapping("/proxy")
public class ProxyController {

    private static String REMOTE_URL = System.getenv("REMOTE_URL");

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public int proxy()  throws Exception{

        String url = String.format("%s/remote", REMOTE_URL);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        return con.getResponseCode();
    }


}
