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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public int proxy()  throws Exception{

        String url = "http://localhost:9000/remote";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        return con.getResponseCode();
    }


}
