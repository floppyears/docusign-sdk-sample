package edu.oregonstate.mist

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("demo")
class SampleConfiguration {
    String username
    String password
    String integratorKey
    String basePath
    String templateId
    String roleName
    String email
    String name

}
