package sample.flamegraph.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequestMapping("/proxy")
public class ProxyController {

    private static String REMOTE_SERVER = "192.168.33.1";

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public int proxy()  throws Exception{

        String url = String.format("http://%s:9000/remote", REMOTE_SERVER);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        return con.getResponseCode();
    }


}
