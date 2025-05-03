#!/bin/bash

# Получаем текущую версию из последнего тега
CURRENT_VERSION=$(git describe --tags --abbrev=0 2>/dev/null || echo "v0.0.0")
echo "Текущая версия: $CURRENT_VERSION"

VERSION=$1

# Если версия не указана через аргумент, запрашиваем интерактивно
if [ -z "$VERSION" ]; then
    read -p "Введите новую версию (например, v1.0.1) или нажмите Enter для выхода: " VERSION
    
    if [ -z "$VERSION" ]; then
        echo "Версия не указана. Выход."
        exit 1
    fi
fi

# Проверяем, что мы на ветке main
CURRENT_BRANCH=$(git branch --show-current)
if [ "$CURRENT_BRANCH" != "main" ]; then
    echo "Ошибка: Вы должны быть на ветке main"
    exit 1
fi

# Обновляем main
git pull origin main

# Создаем тег с версией
git tag -a $VERSION -m "Release $VERSION"
git push origin $VERSION

echo "🎉 Создан новый релиз $VERSION"
echo "📦 Тег успешно отправлен в репозиторий"