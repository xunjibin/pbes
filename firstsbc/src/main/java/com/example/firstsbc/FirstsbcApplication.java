package com.example.firstsbc;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class FirstsbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstsbcApplication.class, args);
	}

}

/*
1.@EncryptablePropertySources({@EncryptablePropertySource("classpath:encrypted.properties"),
                                @EncryptablePropertySource("classpath:encrypted2.properties")})或者
@EncryptablePropertySource(name = "EncryptedProperties", value = "classpath:encrypted.properties")
可用来指定少量配置文件加密
@EnableEncryptableProperties指定所有
2.秘钥处理https://blog.csdn.net/alinyua/article/details/82721419
*/
