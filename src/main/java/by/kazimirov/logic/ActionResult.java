package by.kazimirov.logic;

/**
 *
 */
public enum ActionResult {
    SUCCESS_REGISTER,
    SUCCESS_ADD_TRACK,
    SUCCESS_ADD_ALBUM,
    SUCCESS_ADD_ARTIST,
    SUCCESS_ADD_GENRE,
    SUCCESS_ADD_BONUS,
    SUCCESS_ADD_COMMENT,
    SUCCESS_CHANGE_AVATAR,
    SUCCESS_CHANGE_PERSONAL_INFO,
    SUCCESS_CHANGE_USERNAME,
    SUCCESS_CHANGE_EMAIL,
    SUCCESS_CHANGE_PASSWORD,
    SUCCESS_PURCHASE,

    FAILURE_ADD_TRACK,
    FAILURE_ADD_COMMENT,
    FAILURE_EMPTY_COMMENT,
    FAILURE_INVALID_USERNAME,
    FAILURE_INVALID_EMAIL,
    FAILURE_INVALID_PASSWORD,
    FAILURE_INVALID_NEW_PASSWORD,
    FAILURE_PASSWORDS_NOT_EQUALS,
    FAILURE_USERNAME_NOT_UNIQUE,
    FAILURE_EMAIL_NOT_UNIQUE,
    FAILURE_EMPTY_CART,
    FAILURE_AVATAR_NOT_CHOSE,
    FAILURE_NOT_OLD_PASSWORD,
    FAILURE_NOT_NEW_UNIQUE_PASSWORD,

    FAILURE_ARTIST_NOT_UNIQUE,
    FAILURE_ALBUM_NOT_UNIQUE,
    FAILURE_TRACK_NOT_UNIQUE,
    FAILURE_GENRE_NOT_UNIQUE,
    FAILURE_BONUS_NOT_UNIQUE;
}