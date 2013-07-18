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

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import net.zcarioca.jmx.domain.MBeanDescriptor;
import net.zcarioca.jmx.services.MBeanObjectService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link MBeanObjectService}.
 * 
 * @author Rafael Chargel
 */
@Service
@ManagedResource(objectName="net.zcarioca.jmx:type=JRestMX,name=MBeanObjectService", description = "The MBean Object Service")
class MBeanObjectServiceImpl implements MBeanObjectService 
{
    protected final Logger log = LoggerFactory.getLogger(getClass());
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
    public Set<MBeanDescriptor> fetchAllMBeans() 
    {
        return toMBeanObjectSet(getPlatformMBeanServer().queryMBeans(null, null), null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<MBeanDescriptor> findMBeansByDomain(final String domain) 
    {
        return toMBeanObjectSet(getPlatformMBeanServer().queryMBeans(null, null), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                ObjectInstance objInst = (ObjectInstance)object;
                return StringUtils.equalsIgnoreCase(domain, objInst.getObjectName().getDomain());
            }
        });
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<MBeanDescriptor> findMBeansByType(final String domain, final String type) 
    {
        return toMBeanObjectSet(getPlatformMBeanServer().queryMBeans(null, null), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                ObjectInstance objInst = (ObjectInstance)object;
                ObjectName name = objInst.getObjectName();
                if (StringUtils.equalsIgnoreCase(domain, objInst.getObjectName().getDomain()))
                {
                    String objectType = name.getKeyProperty("type");
                    return StringUtils.equalsIgnoreCase(type, objectType);
                }
                return false;
            }
        });
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @ManagedAttribute(description = "The list of object instance domains.")
    public Set<String> fetchAllDomains() 
    {
        return new HashSet<String>(Arrays.asList(getPlatformMBeanServer().getDomains()));
    }
    
    
    // --------------------------------------------------------------
    // INTERNALS
    // --------------------------------------------------------------
    
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
    
    private Set<MBeanDescriptor> toMBeanObjectSet(Set<ObjectInstance> set, Predicate predicate) 
    {
        if (set == null)
        {
            return null;
        }
        if (predicate != null)
        {
            CollectionUtils.filter(set, predicate);
        }
        if (CollectionUtils.isNotEmpty(set))
        {
            Set<MBeanDescriptor> mbeans = new HashSet<MBeanDescriptor>(set.size());
            for (ObjectInstance objectInstance : set) 
            {
                mbeans.add(toMBeanObject(objectInstance));
            }
            return mbeans;
        }
        return null;
    }

    private MBeanDescriptor toMBeanObject(ObjectInstance objectInstance) 
    {
        try
        {
            MBeanInfo info = mbeanServer.getMBeanInfo(objectInstance.getObjectName());
            return new MBeanDescriptor(objectInstance.getObjectName(), info);
        }
        catch (Exception exc)
        {
            log.warn("Could not find information for MBean: " + objectInstance.getObjectName(), exc);
        }
        return null;
    }
}
