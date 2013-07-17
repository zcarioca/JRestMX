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
 * 
 */
package net.zcarioca.jmx.services;

import java.util.Set;

import javax.management.ObjectInstance;

/**
 * A service used to fetch data about MBeans.
 * 
 * @author Rafael Chargel
 */
public interface MBeanObjectService {
    
    /**
     * Fetches all available MBean object instances.
     * @return Returns a set of object instances.
     */
    public Set<ObjectInstance> fetchAllObjectInstances();
    
    /**
     * Fetches all of the object instance domains.
     * @return Returns all of the object instance domains.
     */
    public Set<String> fetchAllObjectInstanceDomains();
}
