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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import net.zcarioca.jmx.domain.MBeanDescriptor;
import net.zcarioca.jmx.services.MBeanObjectService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the {@link MBeanObjectServiceImpl}.
 * 
 * @author Rafael Chargel
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/spring/TEST-jrestmx-context.xml"})
public class MBeanObjectServiceImplTest {
    
    protected final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MBeanObjectService service;

    @Test
    public void testFetchAllMBeans() {
        Set<MBeanDescriptor> mbeans = this.service.fetchAllMBeans();
        
        assertNotNull(mbeans);
        assertTrue(mbeans.size() > 0);
        
        boolean foundClass = false;
        for (MBeanDescriptor objInst : mbeans) 
        {
            log.debug(objInst.toString());
            if (objInst.getClassName().equals(MBeanObjectServiceImpl.class.getName()))
            {
                foundClass = true;
            }
        }
        
        assertTrue(foundClass);
    }
    
    @Test
    public void testFindMBeansByDomain()
    {
        Set<MBeanDescriptor> mbeans = this.service.findMBeansByDomain("net.zcarioca.jmx");
        assertNotNull(mbeans);
        assertEquals(1, mbeans.size());
    }
    
    @Test
    public void testFindMBeansByDomainNULL()
    {
        Set<MBeanDescriptor> mbeans = this.service.findMBeansByDomain("net.zcarioca.NOT_EXIST");
        assertNull(mbeans);
    }
    
    @Test
    public void testFindMBeansByType()
    {
        Set<MBeanDescriptor> mbeans = this.service.findMBeansByType("net.zcarioca.jmx", "JRestMX");
        assertNotNull(mbeans);
        assertEquals(1, mbeans.size());
    }
    
    @Test
    public void testFindMBeansByTypeNULL()
    {
        Set<MBeanDescriptor> mbeans = this.service.findMBeansByType("net.zcarioca.jmx", "JRestMX_NOT_EXIST");
        assertNull(mbeans);
    }
    
    @Test
    public void testFetchAllDomains() {
        Set<String> domains = this.service.fetchAllDomains();
        assertNotNull(domains);
        assertTrue(domains.contains("net.zcarioca.jmx"));
    }

}
