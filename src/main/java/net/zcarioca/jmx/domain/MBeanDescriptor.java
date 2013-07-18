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
package net.zcarioca.jmx.domain;

import java.io.Serializable;
import java.util.Hashtable;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * A wrapper around an MBean Object.
 * 
 * @author Rafael Chargel
 */
public class MBeanDescriptor implements Serializable, Comparable<MBeanDescriptor>
{
    private static final long serialVersionUID = -9095672947395779768L;
    
    private final String className;
    private final ObjectName objectName;
    private final String description;
    
    /**
     * Constructor for an {@link MBeanDescriptor}.
     * 
     * @param objectInstance The {@link ObjectInstance} as provided by an {@link MBeanServer}.
     */
    public MBeanDescriptor(ObjectName objectName, MBeanInfo mbeanInfo) 
    {
        this.className = mbeanInfo.getClassName();
        this.objectName = objectName;
        this.description = mbeanInfo.getDescription();
    }
    
    /**
     * Gets the name of the class for this object.
     */
    public String getClassName() 
    {
        return className;
    }
    
    /**
     * Gets the mbean's description, if one is provided.
     */
    public String getDescription() 
    {
        return description;
    }
    
    /**
     * Gets the mbean's object name.
     */
    @JsonIgnore
    public ObjectName getObjectName() 
    {
        return objectName;
    }
    
    public String getFullName() 
    {
        return objectName.getCanonicalName();
    }
    
    public Hashtable<String, String> getKeyPropertyList()
    {
        return objectName.getKeyPropertyList();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(MBeanDescriptor o) 
    {
        return this.objectName.compareTo(o.objectName);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.objectName);
        if (StringUtils.isNotBlank(getDescription())) 
        {
            sb.append(" (")
              .append(getDescription())
              .append(')');
        }
        return sb.toString();
    }
}
