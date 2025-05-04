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

# Сохраняем текущую ветку
CURRENT_BRANCH=$(git branch --show-current)
echo "Текущая ветка: $CURRENT_BRANCH"

# Переключаемся на main только если мы не на ней
if [ "$CURRENT_BRANCH" != "main" ]; then
    echo "Переключаемся на ветку main..."
    git checkout main
    
    # Обновляем main
    git pull origin main
    
    # Делаем merge текущей ветки в main
    echo "Выполняем merge ветки $CURRENT_BRANCH в main..."
    git merge $CURRENT_BRANCH
else
    echo "Уже находимся на ветке main"
    git pull origin main
fi

# Создаем тег с версией
git tag -a $VERSION -m "Release $VERSION"
git push origin $VERSION
git push origin main

echo "🎉 Создан новый релиз $VERSION"
echo "📦 Тег успешно отправлен в репозиторий"

# Возвращаемся на исходную ветку
git checkout $CURRENT_BRANCH
echo "Вернулись на исходную ветку: $CURRENT_BRANCH"
