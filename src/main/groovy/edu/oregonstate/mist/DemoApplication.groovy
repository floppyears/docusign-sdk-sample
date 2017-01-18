package edu.oregonstate.mist

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*


@Controller
@EnableAutoConfiguration
@SpringBootApplication

class DemoApplication {
    private final SampleConfiguration properties;

    @Autowired
    DemoApplication(SampleConfiguration properties) {
        this.properties = properties
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        println "edu.oregonstate.mist " + properties.basePath
        DocuSignExample.run(properties)
        return "Hello World!";
    }

	static void main(String[] args) {
		SpringApplication.run DemoApplication, args
	}
}
