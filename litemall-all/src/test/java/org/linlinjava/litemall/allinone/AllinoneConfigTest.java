package org.linlinjava.litemall.allinone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.service.AlaynService;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AllinoneConfigTest {
    @Autowired
    private Environment environment;

    @Autowired
    private AlaynService alaynService;

    @Autowired
    private LitemallOrderService orderService;

    @Test
    public void test() throws IOException {
        System.out.println(orderService);
        LitemallOrder order = orderService.findById(4);

        List<Map<String, Object>> express = alaynService.express(order.getShipChannel(), order.getShipSn());

        System.out.println(express);

//        // 测试获取application-core.yml配置信息
//        System.out.println(environment.getProperty("litemall.express.appId"));
//        // 测试获取application-db.yml配置信息
//        System.out.println(environment.getProperty("spring.datasource.druid.url"));
//        // 测试获取application-wx.yml配置信息
//        System.out.println(environment.getProperty("litemall.wx.app-id"));
//        // 测试获取application-admin.yml配置信息
////        System.out.println(environment.getProperty(""));
//        // 测试获取application.yml配置信息
//        System.out.println(environment.getProperty("logging.level.org.linlinjava.litemall.wx"));
//        System.out.println(environment.getProperty("logging.level.org.linlinjava.litemall.admin"));
//        System.out.println(environment.getProperty("logging.level.org.linlinjava.litemall"));
    }

}
