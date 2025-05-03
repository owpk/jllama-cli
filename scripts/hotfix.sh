#!/bin/bash

HOTFIX_NAME=$1

if [ -z "$HOTFIX_NAME" ]; then
    echo "Укажите название хотфикса в формате: ./hotfix.sh hotfix-description"
    exit 1
fi

# Сохраняем текущую ветку
CURRENT_BRANCH=$(git branch --show-current)
echo "Текущая ветка: $CURRENT_BRANCH"

# Переключаемся на main только если мы не на ней
if [ "$CURRENT_BRANCH" != "main" ]; then
    echo "Переключаемся на ветку main..."
    git checkout main
    git pull origin main
else
    echo "Уже находимся на ветке main"
    git pull origin main
fi

# Создаем ветку hotfix
git checkout -b "hotfix/$HOTFIX_NAME"
echo "Создана ветка hotfix/$HOTFIX_NAME"