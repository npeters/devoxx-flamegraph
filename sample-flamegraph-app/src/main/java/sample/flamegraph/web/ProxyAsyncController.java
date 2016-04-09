package sample.flamegraph.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ning.http.client.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Future;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Call a remote service and return the status code
 */
@Controller
@RequestMapping("/proxy-async")
public class ProxyAsyncController  {

    private static String REMOTE_URL = System.getenv("REMOTE_URL");

    private Logger log = LoggerFactory.getLogger(ProxyAsyncController.class);

    private AsyncHttpClient asyncHttpClient =
            new AsyncHttpClient(
            new AsyncHttpClientConfig.Builder()
                .setAllowPoolingConnections(true)
                    .setMaxConnections(50)
                    .setMaxConnectionsPerHost(50)
                    .build());

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<ResponseEntity<?>> proxyAsync() {

        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        String url = String.format("%s/remote", REMOTE_URL);


        Future<Integer> fResult = asyncHttpClient.prepareGet(url).execute(
                new AsyncCompletionHandler<Integer>(){

                    @Override
                    public Integer onCompleted(Response response) throws Exception{
                        // Do something with the Response
                        ResponseEntity<String> responseEntity =
                                new ResponseEntity<>("proxy: "+response.getStatusCode(), HttpStatus.OK);
                        deferredResult.setResult(responseEntity);

                        return response.getStatusCode();
                    }

                    @Override
                    public void onThrowable(Throwable t){
                        ResponseEntity<Void> responseEntity =
                                new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
                        deferredResult.setResult(responseEntity);
                    }
                });

        return deferredResult;
    }


}
