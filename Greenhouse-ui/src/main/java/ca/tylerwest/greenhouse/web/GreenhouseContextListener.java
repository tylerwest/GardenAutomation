/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.tylerwest.greenhouse.web;

import ca.tylerwest.greenhouse.Greenhouse;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Tyler
 */
@WebListener
public class GreenhouseContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (!Greenhouse.getInstance().isInitialized()) {
            Greenhouse.getInstance().startup();
            System.out.println("-----GREENHOUSE INITIALIZED");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (Greenhouse.getInstance().isInitialized()) {
            Greenhouse.getInstance().shutdown();
            System.out.println("-----GREENHOUSE SHUTDOWN");
        }
    }

}
