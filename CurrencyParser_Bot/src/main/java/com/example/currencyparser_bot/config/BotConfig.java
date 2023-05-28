package com.example.currencyparser_bot.config;

import lombok.Data;
//Импорт аннотации @Data из проекта Lombok. Аннотация @Data автоматически генерирует геттеры, сеттеры, методы equals(),
// hashCode() и другие стандартные методы, что упрощает написание классов с геттерами и сеттерами.
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//Импорт классов из фреймворка Spring, необходимых для работы с конфигурацией и свойствами.
@Configuration
//Аннотация @Configuration указывает, что этот класс является конфигурационным классом Spring и содержит определения бинов и другую конфигурацию.
@Data
@PropertySource("application.properties")
//Аннотация @PropertySource указывает, что файл свойств application.properties будет использоваться для загрузки значений свойств.
public class BotConfig {

@Value("${bot.name}")
    String botName;
//Аннотация @Value указывает что значение полю botName будет загружено из файла application.properties.

@Value("${bot.token}")
    String token;
// Аннотация @Value указывает что значение полю token будет загружено из файла application.properties.
}
