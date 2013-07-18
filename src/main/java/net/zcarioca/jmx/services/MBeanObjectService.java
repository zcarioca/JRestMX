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

import net.zcarioca.jmx.domain.MBeanDescriptor;

/**
 * A service used to fetch data about MBeans.
 * 
 * @author Rafael Chargel
 */
public interface MBeanObjectService {
    
    /**
     * Fetches all available MBean object instances.
     * @return Returns a set of object instances. Returns NULL if none are found.
     */
    public Set<MBeanDescriptor> fetchAllMBeans();
    
    /**
     * Finds all of the MBeans with a given domain.
     * @param domain The domain.
     * @return Returns all of the MBeans with a given domain. Returns NULL if none are found.
     */
    public Set<MBeanDescriptor> findMBeansByDomain(String domain);
    
    /**
     * Gets all of the MBeans by type.
     * @param domain The MBean 'Domain'.
     * @param type The MBean 'Type'.
     * @return Returns all of the mbeans with a given type. Returns NULL if none are found.
     */
    public Set<MBeanDescriptor> findMBeansByType(String domain, String type);
    
    /**
     * Fetches all of the object instance domains.
     * @return Returns all of the object instance domains.
     */
    public Set<String> fetchAllDomains();
}
