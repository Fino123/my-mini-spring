import com.minis.main.java.beans.BeansException;
import com.minis.main.java.test.AService;
import com.minis.main.java.context.ClassPathXmlApplicationContext;

public class TestAService {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService service = (AService) context.getBean("aservice");
        service.sayHello();
    }
}
