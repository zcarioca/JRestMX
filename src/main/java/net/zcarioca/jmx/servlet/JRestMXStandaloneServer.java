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

import java.net.URI;

import net.zcarioca.jmx.controller.MBeanController;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * A standalone JRestServer
 * 
 * @author Rafael Chargel 
 */
public class JRestMXStandaloneServer
{
    private static final String APP_CONTEXT = "/META-INF/spring/jrestmx-context.xml";
    
    private ApplicationContext ctx;
    HttpServer server;
    
    
    public JRestMXStandaloneServer() 
    {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(APP_CONTEXT, getClass());
        ctx.registerShutdownHook();
        
        this.ctx = ctx;
    }
    
    public void start()
    {
        ResourceConfig config = new ResourceConfig();
//        config.packages("net.zcarioca.jmx.controller");
        config.register(new JacksonFeature());
        config.registerInstances(ctx.getBean(MBeanController.class));
        
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"), config);
    }
    
    public static void main(String[] args) throws Exception 
    {
        
        JRestMXStandaloneServer server = new JRestMXStandaloneServer();
        
        server.start();
        System.in.read();
        
    }
}
