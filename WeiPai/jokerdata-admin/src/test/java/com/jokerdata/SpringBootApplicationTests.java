package com.jokerdata;

import com.aliyuncs.exceptions.ClientException;
import com.jokerdata.common.push.config.MessagePush;
import com.jokerdata.service.app.IJPushService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:/application-dev.yml"})
public abstract class SpringBootApplicationTests {


}
