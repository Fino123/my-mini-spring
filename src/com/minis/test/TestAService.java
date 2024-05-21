import com.minis.main.java.AService;
import com.minis.main.java.ClassPathXmlApplicationContext;

public class TestAService {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService service = (AService) context.getBean("aservice");
        service.sayHello();
    }
}
