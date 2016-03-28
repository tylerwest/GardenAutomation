package ca.tylerwest.greenhouse.web.auth;

import ca.tylerwest.greenhouse.web.auth.beans.Role;

public interface AccessControl {

    public boolean signIn(String username, String password);

    public boolean isUserSignedIn();

    public boolean isUserInRole(Role role);

    public String getPrincipalName();
}
