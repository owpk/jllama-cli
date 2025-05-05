package org.owpk.llm.provider.auth;

public interface ApiKeyProvider {

	/**
	 * Метод для получения API ключа
	 *
	 * @return API ключ
	 */
	String getApiKey();

	/**
	 * Метод для проверки срока действия API ключа
	 *
	 * @return true, если API ключ действителен, иначе false
	 */
	boolean isApiKeyValid();

}
