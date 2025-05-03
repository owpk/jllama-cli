HOTFIX_NAME=$1

if [ -z "$HOTFIX_NAME" ]; then
    echo "Укажите название хотфикса в формате: ./hotfix.sh hotfix-description"
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

# Создаем ветку для хотфикса
git checkout -b hotfix/$HOTFIX_NAME