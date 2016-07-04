package ${packageName}.controller.v1;

import ${packageName}.service.Hello;
import ${packageName}.service.HelloService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1")
public class HelloworldController {

    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(HelloworldController.class);

    @Autowired
    private HelloService helloService;

    @Autowired
    private ConversionService conversionService;

    @ApiOperation(value = "sayHello", notes = "A que coucou !")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "your user name", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "message", value = "a little greeting message", dataType = "string",
                    paramType = "query")})
    @RequestMapping
    public HelloResource sayHello(@RequestParam(value = "user", required = false) String user,
            @RequestParam(value = "message", required = false) String message) {
        Hello hello = helloService.sayHello(user, message);
        return conversionService.convert(hello, HelloResource.class);
    }
}
