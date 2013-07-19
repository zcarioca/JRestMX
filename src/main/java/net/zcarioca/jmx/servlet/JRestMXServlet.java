/*
 * Project:  JRestMX
 * 
 * Copyright (C) 2010 zcarioca.net
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.zcarioca.jmx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.zcarioca.jmx.servlet.handlers.RestRequestHandler;

/**
 * A REST servlet for processing JMX requests.
 * 
 * @author Rafael Chargel
 */
public class JRestMXServlet extends HttpServlet 
{
    private static final long serialVersionUID = -8469864271174549203L;
    private static final String appContext = "/META-INF/spring/jrestmx-context.xml";
   
    private ApplicationContext applicationContext;
    private RestRequestHandler requestHandler;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws ServletException 
    {
        ClassPathXmlApplicationContext applicationContext = 
                new ClassPathXmlApplicationContext(appContext);
        
        applicationContext.registerShutdownHook();
        applicationContext.start();
        
        this.applicationContext = applicationContext;
        this.requestHandler = this.applicationContext.getBean(RestRequestHandler.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException 
    {
        this.requestHandler.respond(req, resp);
    }
}
