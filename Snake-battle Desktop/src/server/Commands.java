package server;

public final class Commands {
	public static String getSnake = "getsnake";
	/**
	 * Если нужно отправить\были отправлены команды (список команд)
	 */
	public static String actions = "actions";

	public static int SNAKE_COUNT_FIELDS = 1;

	/**
	 * Получить мозг
	 */
	public static final String GET_MIND = "GET_MIND";

	/**
	 * Все данные отправлены
	 */
	public static final String END_SENDING = "END_SENDING";

	public static final String COMMAND_NOT_SUPPORTED = "COMMAND_NOT_SUPPORTED";

	public static final int MAX_W = 10;
}
