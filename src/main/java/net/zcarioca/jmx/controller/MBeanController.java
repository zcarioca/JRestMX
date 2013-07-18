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
package net.zcarioca.jmx.controller;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.zcarioca.jmx.domain.MBeanDescriptor;
import net.zcarioca.jmx.services.MBeanObjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * REST Controller for the MBean Services.
 * 
 * @author Rafael Chargel
 */
@Component
@Path("/jmx-rest")
public class MBeanController
{
    @Autowired
    private MBeanObjectService mbeanObjectService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getMBeanDomains()
    {
        return new TreeSet<String>(mbeanObjectService.fetchAllDomains());
    }

    @GET
    @Path("{domainName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<MBeanDescriptor> getMBeanObjects(@PathParam("domainName") String domainName)
    {
        return mbeanObjectService.findMBeansByDomain(domainName);
    }

    @GET
    @Path("{domainName}/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<MBeanDescriptor> getMBeanObjects(@PathParam("domainName") String domainName,
            @PathParam("type") String type)
    {
        return mbeanObjectService.findMBeansByType(domainName, type);
    }
    
    // ---------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------
    
    public void setMBeanObjectService(MBeanObjectService mBeanObjectService) 
    {
        this.mbeanObjectService = mBeanObjectService;
    }
    
    public MBeanObjectService getMBeanObjectService() 
    {
        return mbeanObjectService;
    }
    
    public static final class Test
    {
        private String name;
        
        public Test(String name)
        {
            this.name = name;
        }
        
        /**
         * @return the name
         */
        public String getName() {
            return name;
        }
    }
}
