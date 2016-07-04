package ${packageName}.controller.v1;

import ${packageName}.service.Hello;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HelloResourceConverter implements Converter<Hello, HelloResource> {

    @Override
    public HelloResource convert(Hello source) {
        HelloResource helloResource = new HelloResource();
        helloResource.setMessage(source.getMessage());
        helloResource.setUser(source.getUser());
        return helloResource;
    }
}
