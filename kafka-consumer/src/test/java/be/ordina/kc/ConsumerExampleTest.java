package be.ordina.kc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import be.ordina.kc.ConsumerExample;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ConsumerExample.class)
public class ConsumerExampleTest {

	@Test
	public void contextLoads() {
	}

}
