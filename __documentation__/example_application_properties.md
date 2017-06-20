# Example application.properties

```
captchaUrl=https://www.google.com/recaptcha/api/siteverify
captchaSecret=jiougfdj89fgd8ufg8gf8trt
```

## Gmail Example
```
spring.mail.enabled=true
spring.mail.host=smtp.gmail.com
spring.mail.username=example@gmail.com
spring.mail.password=shhMyPassword
spring.mail.port=587
spring.mail.properties.mail.smtp.auth = true;
spring.mail.properties.mail.smtp.starttls.enable = true
spring.mail.properties.mail.smtp.ssl.enable = false
spring.mail.properties.mail.socketFactory.port=25
spring.mail.properties.mail.socketFactory.class=javax.net.ssl.SSLSocketFactory
spring.mail.properties.mail.socketFactory.fallback=false
```
