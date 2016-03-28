package ca.tylerwest.greenhouse.web.auth;

import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;

import ca.tylerwest.greenhouse.web.auth.beans.User;

public final class CurrentUser {
	
	public static final User NOT_LOGGED_IN_USER = new User();

    public static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = CurrentUser.class
            .getCanonicalName();

    private CurrentUser() {
    }

    public static User get() {
        User currentUser = (User) getCurrentHttpSession().getAttribute(
                CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        if (currentUser == null) {
            return NOT_LOGGED_IN_USER;
        } else {
            return currentUser;
        }
    }

    private static WrappedSession getCurrentHttpSession() {
        VaadinSession s = VaadinSession.getCurrent();
        if (s == null) {
            throw new IllegalStateException(
                    "No session found for current thread");
        }
        return s.getSession();
    }

    public static void set(User currentUser) {
        if (currentUser == null) {
            getCurrentHttpSession().removeAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        } else {
            getCurrentHttpSession().setAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY, currentUser);
        }
    }

}
