package com.mosn;

import com.mosn.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqSpringbootOrderProduceApplicationTests {


    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        orderService.makeOrder("1", "2", 12);
    }

    @Test
    void testDirect() {
        orderService.makeDirectOrder("1", "2", 122);
    }

    @Test
    void testTopic() {
        orderService.makeTopicOrder("1", "2", 1122);
    }

    @Test
    void ttlTest() {
        for (int i = 0; i < 11; i++) {
            orderService.ttlOrder("1", "2", 112122);

        }
    }

    @Test
    void ttlMessageTest() {
        orderService.ttlMessageOrder("1", "2", 2122);
    }

}
