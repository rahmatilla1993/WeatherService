package com.company;

import com.company.config.JPAConfig;
import com.company.dao.AuthUserDao;
import com.company.dao.WeatherDao;
import com.company.entity.AuthUser;
import com.company.entity.WeatherData;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        try (var applicationContext = new AnnotationConfigApplicationContext(JPAConfig.class)) {
            WeatherDao weatherDao = applicationContext.getBean(WeatherDao.class);
            AuthUserDao authUserDao = applicationContext.getBean(AuthUserDao.class);
            AuthUser authUser = authUserDao.findById(2L).get();
            List<WeatherData> data = weatherDao.findAllBySubscribeLocation(authUser, null);
            data.forEach(System.out::println);
        }
    }
}
