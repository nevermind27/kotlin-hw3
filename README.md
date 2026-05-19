Так как на рабочем проекте используется спринг и для таких сервисов его часто используют,
решила поупражняться в создании проекта на нем.


https://start.spring.io/ - здесь сгенерила шаблон для SpringBoot веб-приложения.

Для расчета положения спутника использовала библиотеку predict4java (https://github.com/g4dpz/predict4java)

Данные о положении беру из https://celestrak.org/NORAD/documentation/gp-data-formats.php в TLE формате.

## Запуск
Для проверки нужно запустить (порт 8080 не должен быть занят):
```
./gradlew bootRun
```

И в соседнем терминале:
```
curl http://localhost:8080/predict?minutes=1
```