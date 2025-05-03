#!/bin/bash

VERSION=$1

if [ -z "$VERSION" ]; then
    echo "Укажите версию в формате: ./release.sh X.Y.Z"
    exit 1
fi

echo "Начинаю создание релиза версии $VERSION"

# Проверяем, что мы на ветке main
CURRENT_BRANCH=$(git branch --show-current)
if [ "$CURRENT_BRANCH" != "main" ]; then
    echo "Ошибка: Вы должны быть на ветке main"
    exit 1
fi

# Проверяем, что нет незакоммиченных изменений
if [ -n "$(git status --porcelain)" ]; then
    echo "Ошибка: Есть незакоммиченные изменения"
    exit 1
fi

# Обновляем main
git pull origin main

# Создаем релизную ветку
git checkout -b release/$VERSION

# Обновляем версию
./gradlew release -PnewVersion=$VERSION

# Коммитим изменения
git add .
git commit -m "chore: bump version to $VERSION"

# Создаем тег
git tag -a "v$VERSION" -m "Release version $VERSION"

# Мерджим в main
git checkout main
git merge release/$VERSION

# Пушим изменения
git push origin main --tags

# Удаляем релизную ветку
git branch -d release/$VERSION

echo "Релиз версии $VERSION успешно создан"