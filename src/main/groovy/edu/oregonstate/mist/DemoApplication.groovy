package edu.oregonstate.mist

import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*


@Controller
@EnableAutoConfiguration
@SpringBootApplication
class DemoApplication {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

	static void main(String[] args) {
		SpringApplication.run DemoApplication, args
	}
}
