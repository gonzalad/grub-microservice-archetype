package ${packageName}.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HelloService {

    public Hello sayHello(String user, String message) {
        return new Hello(user, message);
    }

}
