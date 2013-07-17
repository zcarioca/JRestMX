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
package net.zcarioca.jmx.services.impl;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import net.zcarioca.jmx.services.MBeanObjectService;

/**
 * Implementation of the {@link MBeanObjectService}.
 * 
 * @author Rafael Chargel
 */
@Service
@ManagedResource(objectName="net.zcarioca.jmx:type=JRestMX,name=MBeanObjectService", description = "The MBean Object Service")
class MBeanObjectServiceImpl implements MBeanObjectService 
{
    private static final long startTime = System.currentTimeMillis();
    
    @Autowired(required = false)
    private MBeanServer mbeanServer;
    
    @ManagedAttribute(description = "The time in milliseconds since the service was registered.")
    public long getMillisSinceStarted() 
    {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ObjectInstance> fetchAllObjectInstances() 
    {
        return getPlatformMBeanServer().queryMBeans(null, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @ManagedAttribute(description = "The list of object instance domains.")
    public Set<String> fetchAllObjectInstanceDomains() 
    {
        return new HashSet<String>(Arrays.asList(getPlatformMBeanServer().getDomains()));
    }
    
    private MBeanServer getPlatformMBeanServer() 
    {
        synchronized (mbeanServer) 
        {
            if (mbeanServer == null) 
            {
                // find the JBoss mbean server, if it exists
                Collection<MBeanServer> mbeanServers = MBeanServerFactory.findMBeanServer(null);
                if (mbeanServers != null)
                {
                    if (mbeanServers.size() == 1)
                    {
                        return mbeanServers.iterator().next();
                    }
                    else if (mbeanServers.size() > 1)
                    {
                        for (MBeanServer mbeanServer : mbeanServers)
                        {
                            String domain = mbeanServer.getDefaultDomain();
                            if (StringUtils.contains(domain, "jboss"))
                            {
                                return mbeanServer;
                            }
                        }
                    }
                }
                mbeanServer = ManagementFactory.getPlatformMBeanServer();
            }
            return mbeanServer;
        }
    }

}
