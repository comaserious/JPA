package com.ohgiraffers.springdatajpa.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper(){
        /*필기.
        *  Entity 클래스에서 setter 사용을 지양한다
        *  그렇다면 값을 어떻게 넣는가?
        *  private 필드에 접근할 수 있도록 설정이 필요하다.
        *  Entity 와 DTO 간의 변환을 용이하게 만들도록 도와주는 라이브러리이다.*/
        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }
}
