package com.example.currencyparser_bot.config;

import java.io.IOException; // Импорт класса IOException из пакета java.io. Этот класс используется для обработки исключений, связанных с вводом-выводом (I/O).
import java.util.HashMap; // Импорт класса HashMap из пакета java.util. HashMap является реализацией интерфейса Map и используется для хранения пар ключ-значение.
import java.util.Map; // Импорт интерфейса Map из пакета java.util. Интерфейс Map представляет структуру данных, в которой данные хранятся в виде пар ключ-значение.

import org.jsoup.Jsoup; // Импорт класса Jsoup из пакета org.jsoup. Jsoup является библиотекой Java для парсинга HTML-страниц.
import org.jsoup.nodes.Document; // Импорт класса Jsoup из пакета org.jsoup. Jsoup является библиотекой Java для парсинга HTML-страниц.
import org.jsoup.nodes.Element; // Импорт класса Element из пакета org.jsoup.nodes. Element представляет элемент HTML и предоставляет методы для доступа к его атрибутам и содержимому.
import org.jsoup.select.Elements; // Импорт класса Element из пакета org.jsoup.nodes. Element представляет элемент HTML и предоставляет методы для доступа к его атрибутам и содержимому.

public class Parser{ //Объявление публичного класса Parser.
    private static final String URL = "https://www.cbr.ru/scripts/XML_daily.asp?date_req="; //Объявление и инициализация константы URL, которая содержит URL-адрес сайта ЦБ РФ для получения текущих курсов валют.

    public static Map<String, Double> getCurrencyRate() throws IOException { //Объявление публичного статического метода getCurrencyRate, который возвращает объект Map<String, Double> с курсами валют.
        Map<String, Double> currencyRate = new HashMap<>(); //Создание объекта HashMap, который будет содержать курсы валют. Используется интерфейс Map для обобщения типов.

        Document doc = Jsoup.connect(URL).get(); //Получение HTML-страницы по URL-адресу URL с помощью библиотеки Jsoup и создание объекта Document, который содержит ее содержимое.
        Elements elements = doc.getElementsByTag("Valute"); //Получение списка элементов с тегом "Valute" из объекта Document.

        for (Element element : elements) { //Начало цикла for-each, который перебирает все элементы списка elements.
            String charCode = element.getElementsByTag("CharCode").text(); //Получение текстового содержимого элемента с тегом "CharCode" и сохранение его в переменную charCode.
            double rate = Double.parseDouble(element.getElementsByTag("Value").text().replace(',', '.'));
            //Получение текстового содержимого элемента с тегом "Value", замена запятой на точку и преобразование полученной строки в число типа double. Результат сохраняется в переменной rate.
            currencyRate.put(charCode, rate);
            //Добавление в объект HashMap пары ключ-значение, где ключ - charCode, а значение - rate.
        }

        return currencyRate; //Добавление в объект HashMap пары ключ-значение, где ключ - charCode, а значение - rate.
    }
    public static double getDollarRate() throws IOException { //Вызов метода getCurrencyRate для получения объекта HashMap с курсами валют и сохранение его в переменную currencyRate.
        Map<String, Double> currencyRate = getCurrencyRate();
        return currencyRate.get("USD");
    }

    public static double getEuroRate() throws IOException { // Вызов метода getCurrencyRate для получения объекта HashMap с курсами валют и сохранение его в переменную currencyRate.
        Map<String, Double> currencyRate = getCurrencyRate();
        return currencyRate.get("EUR");
    }
        public static double getCNYRate() throws IOException { //Вызов метода getCurrencyRate для получения объекта HashMap с курсами валют и сохранение его в переменную currencyRate.
            Map<String, Double> currencyRate = getCurrencyRate();
            return currencyRate.get("CNY");
        }
}
