#!/bin/bash

FEATURE_NAME=$1

if [ -z "$FEATURE_NAME" ]; then
    echo "Укажите название фичи в формате: ./feature.sh feature-name"
    exit 1
fi

# Проверяем, что мы на ветке main
CURRENT_BRANCH=$(git branch --show-current)
if [ "$CURRENT_BRANCH" != "main" ]; then
    echo "Ошибка: Вы должны быть на ветке main"
    exit 1
fi

# Обновляем main
git pull origin main

# Создаем ветку для новой фичи
git checkout -b feature/$FEATURE_NAME

echo "Создана новая ветка feature/$FEATURE_NAME"
echo "Можете начинать разработку!"