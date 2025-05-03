#!/bin/bash

# Функция для обычной сборки
regular_build() {
    # Очищаем предыдущую сборку
    ./gradlew clean

    # Запускаем тесты
    ./gradlew test

    # Собираем проект
    ./gradlew build

    echo "Сборка завершена успешно"
}

# Функция для нативной сборки
native_build() {
    # Очищаем предыдущую сборку
    ./gradlew clean

    # Запускаем тесты
    ./gradlew test

    # Собираем нативный образ
    ./gradlew nativeCompile

    echo "Нативная сборка завершена успешно"
}

# Проверяем аргументы командной строки
if [ "$1" == "--native" ]; then
    echo "Запуск нативной сборки..."
    native_build
else
    echo "Запуск обычной сборки..."
    regular_build
fi