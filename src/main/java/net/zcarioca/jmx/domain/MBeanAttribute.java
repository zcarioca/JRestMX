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

import javax.management.MBeanAttributeInfo;

/**
 * An mbean attribute
 * 
 * @author Rafael Chargel
 */
public class MBeanAttribute implements Serializable, Comparable<MBeanAttribute>
{
    private static final long serialVersionUID = 3179164034489406082L;
    private final String name;
    private final String type;
    private final String description;
    private final Object value;
    
    public MBeanAttribute(MBeanAttributeInfo info, Object value)
    {
        this.name = info.getName();
        this.type = info.getType();
        this.description = info.getDescription();
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getType() {
        return type;
    }
    
    public Object getValue() {
        return value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(MBeanAttribute o) 
    {
        return name.compareToIgnoreCase(o.name);
    }
}
