package ca.tylerwest.greenhouse.web.views;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import ca.tylerwest.greenhouse.web.GreenhouseUI;

@SuppressWarnings("serial")
public class LoginScreen extends Window {

    private TextField username;
    private PasswordField password;
    private Button login;
    private LoginListener loginListener;

    public LoginScreen(LoginListener loginListener) {
        this.loginListener = loginListener;
        buildUI();
        username.focus();
    }

    private void buildUI() {
        Component loginForm = buildLoginForm();
        setContent(loginForm);
        setWidth("400px");
        center();
        setModal(true);
        setResizable(false);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();
        loginForm.setSizeUndefined();
        loginForm.setMargin(true);

        username = new TextField("Username");
        username.setWidth(15, Unit.EM);
        
        password = new PasswordField("Password");
        password.setWidth(15, Unit.EM);
        password.setDescription("Write anything");
        
        loginForm.addComponent(username);
        loginForm.addComponent(password);

        login = new Button("Login");
        login.setDisableOnClick(true);
        login.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login();
                } finally {
                    login.setEnabled(true);
                }
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginForm.addComponent(login);
        return loginForm;
    }

    private void login() {
        if (GreenhouseUI.get().getAccessControl().signIn(username.getValue(), password.getValue())) {
            loginListener.loginSuccessful();
        } else {
            showNotification(new Notification("Login failed",
                    "Please check your username and password and try again.",
                    Notification.Type.WARNING_MESSAGE));
            username.focus();
        }
    }

    private void showNotification(Notification notification) {
        notification.setDelayMsec(2000);
        notification.setPosition(Position.BOTTOM_RIGHT);
        notification.show(Page.getCurrent());
    }

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}
