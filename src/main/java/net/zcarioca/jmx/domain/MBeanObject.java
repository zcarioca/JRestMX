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
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

/**
 * An further description of an MBean Object.
 * 
 * @author Rafael Chargel 
 */
public class MBeanObject implements Serializable
{
    private static final long serialVersionUID = -782288444306828246L;
    private final MBeanDescriptor descriptor;
    private final Set<MBeanAttribute> attributes;
    
    public MBeanObject(MBeanDescriptor descriptor, SortedSet<MBeanAttribute> attributes)
    {
        this.descriptor = descriptor;
        this.attributes = Collections.unmodifiableSortedSet(attributes);
    }
    
    public MBeanDescriptor getDescriptor() {
        return descriptor;
    }
    
    public Set<MBeanAttribute> getAttributes() {
        return attributes;
    }
}
