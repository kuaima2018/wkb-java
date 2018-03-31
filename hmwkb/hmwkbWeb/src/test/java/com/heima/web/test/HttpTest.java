package com.heima.web.test;

//import org.apache.commons.httpclient.HttpClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/5/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext*-test.xml")
@TransactionConfiguration(defaultRollback =false)
public class HttpTest {

    @Autowired
    @Qualifier("payRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("mmsId")
    private String mmsTemplate;

    //@Test
    public void testContent()
    {
        System.out.println(String.format(mmsTemplate,"xxzk"));
    }
    //@Test
    public void testHttp() throws Exception
    {
        //HttpURLConnection  httpURLConnection=new HttpURLConnection();
        //HttpClient httpClient = new HttpClient();
         //0,2015051413272588766858734,0,1,0,提交成功
        //HttpMethod method = postMethod("");
        ResponseEntity<String> response=null;
        String url="http://sms.1xinxi.cn/asmx/smsservice.aspx?name=13918130440&pwd=F8DA05B7553AD634DECC7D9EF582&content=";

        String strContent="【工作宝】您的验证码是：7788，在30分钟内有效。本软件完全免费，请放心使用。祝工作愉快！";
        url+=strContent;// java.net.URLEncoder.encode(strContent,"utf-8");
        url+="&mobile=13774313550&type=pt";
        response=restTemplate.postForEntity(url,null,String.class);
        System.out.println(response.getBody());
    }

   /* private static HttpMethod postMethod(String url) throws IOException {
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");
        NameValuePair[] param = { new NameValuePair("startCity","杭州"),
                new NameValuePair("lastCity","沈阳"),
                new NameValuePair("userID",""),
                new NameValuePair("theDate","") } ;
        post.setRequestBody(param);
        post.releaseConnection();
        return post;
    }*/

    private static final String targetURL = "http://61.129.70.198:8080/workTool/user/login";

    @Test
    public void testPost() {

        try {

            URL targetUrl = new URL(targetURL);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            String input = "{\"userName\":\"13918130440\",\"password\":\"c19ed5249e2e3fcf495ed8c2b355c64c\"}";

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));

            String output;
            System.out.println("Output from Server:\n");
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
