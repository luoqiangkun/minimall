package org.linlinjava.litemall.db.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AlaynService {

    private static final Log logger = LogFactory.getLog(AlaynService.class);

    public List<Map<String, Object>> express(String com, String code) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpGet = new HttpGet("https://www.baidu.com/s?mod=1&isid=8f6de4de00815165&wd=%E7%99%BE%E5%BA%A6%E5%BF%AB%E9%80%92%E6%9C%8D%E5%8A%A1"); // 替换为你的URL
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        httpGet.setHeader("Referer", "https://www.baidu.com");
        HttpResponse response = httpClient.execute(httpGet, context);
        String result = EntityUtils.toString(response.getEntity());

        Pattern pattern = Pattern.compile("tokenV2[^\\\"]+\"");
        Matcher matcher = pattern.matcher(result);
        if (!matcher.find()) {
            logger.error("没有找到匹配的token");
            throw new RuntimeException("没有找到匹配的token");
        }
        String matchedToken = matcher.group();
        String token = matchedToken.substring(8, matchedToken.length() - 1);

        System.out.println("https://alayn.baidu.com/express/appdetail/get_detail?query_from_srcid=51151&nu=" + code +"&com=" + com + "&tokenV2=" + token);
        HttpGet httpGet2 = new HttpGet("https://alayn.baidu.com/express/appdetail/get_detail?query_from_srcid=51151&nu=" + code +"&com=" + com + "&tokenV2=" + token); // 替换为你的URL
        httpGet2.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        httpGet2.setHeader("Referer", "https://www.baidu.com");
        httpGet2.setHeader("Host", "alayn.baidu.com");
        HttpResponse response2 = httpClient.execute(httpGet2, context);
        System.out.println(response2);

        String result2 = EntityUtils.toString(response2.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(result2, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) map.get("data");
            List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("context");
            return list;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("物流查询失败");
        }
    }
}
