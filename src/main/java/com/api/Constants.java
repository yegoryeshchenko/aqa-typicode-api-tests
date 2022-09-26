package com.api;

import java.util.regex.Pattern;

public final class Constants {

    public static final String DELPHINE_USER_NAME = "Delphine";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

}
