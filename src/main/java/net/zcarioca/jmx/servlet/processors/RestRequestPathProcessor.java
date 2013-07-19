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
package net.zcarioca.jmx.servlet.processors;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Processes a sing REST path request.
 * 
 * @author Rafael Chargel
 */
@RequestMapping
public interface RestRequestPathProcessor 
{
    /**
     * Gets the pattern that matches the requested paths.
     * @return Returns the pattern for this processor.
     */
    public Pattern getPathPattern();
    
    /**
     * Determines if the provided path matches the requested pattern.
     * @param path The pattern to match.
     * @return Returns true if the path is valid.
     */
    public boolean isValidPath(String path);
    
    /**
     * Returns the response from this path processor.
     * @param request The servlet request.
     * @param path The path.
     * @return Returns the response.
     */
    public Object getResponse(HttpServletRequest request, String path);
}
