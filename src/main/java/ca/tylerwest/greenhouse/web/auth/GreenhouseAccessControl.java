package ca.tylerwest.greenhouse.web.auth;

import java.io.InputStream;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import ca.tylerwest.greenhouse.web.auth.beans.Authentication;
import ca.tylerwest.greenhouse.web.auth.beans.Role;
import ca.tylerwest.greenhouse.web.auth.beans.User;

public class GreenhouseAccessControl implements AccessControl {
	
	private Authentication auth;

	public GreenhouseAccessControl() {
		try {
			load(getClass().getClassLoader().getResourceAsStream("auth.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
    private void load(InputStream resourceAsStream) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Authentication.class);
		Unmarshaller um = context.createUnmarshaller();
		auth = um.unmarshal(new StreamSource(resourceAsStream), Authentication.class).getValue();
	}

	@Override
    public boolean signIn(String username, String password) {
        if (username == null || username.isEmpty())
            return false;
        
        Optional<User> result = auth.getUsers().stream().filter(u -> u.getName().equals(username) && u.getPassword().equals(password)).findFirst();
        if (result.isPresent())
        {
        	CurrentUser.set(result.get());
        	return true;
        }
        else
        	return false;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().equals(CurrentUser.NOT_LOGGED_IN_USER);
    }

    @Override
    public boolean isUserInRole(Role role) {
    	return CurrentUser.get().getRoles().stream().anyMatch(r -> r.equals(role));
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get().getName();
    }

}
