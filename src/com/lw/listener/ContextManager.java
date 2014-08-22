package com.lw.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lw.util.ChargeManager;

public class ContextManager implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed");
		ChargeManager.getInstance().close();
	}

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized");
	}

}
