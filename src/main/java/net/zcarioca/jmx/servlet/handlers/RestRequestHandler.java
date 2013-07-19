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
package net.zcarioca.jmx.servlet.handlers;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import net.zcarioca.jmx.servlet.processors.RestRequestPathProcessor;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The request handler for REST calls.
 * 
 * @author Rafael Chargel
 */
@Component
public class RestRequestHandler 
{
    @Autowired
    private RestRequestPathProcessor[] processors;
    
    public void respond(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
       try 
       {
           Object value = getResponse(request);
           
           if (value != null)
           {
               response.setContentType(MediaType.APPLICATION_JSON);
               response.setCharacterEncoding("UTF-8");

               BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
               ObjectMapper mapper = new ObjectMapper();
               mapper.writeValue(out, value);

               out.flush();
               out.close();
           }
           else
           {
               response.sendError(HttpServletResponse.SC_NOT_FOUND);
           }
       }
       catch (InvalidObjectException exc)
       {
           response.sendError(HttpServletResponse.SC_BAD_REQUEST);
       }
       catch (IOException exc)
       {
           throw exc;
       }
       catch (Exception exc)
       {
           response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
       }
    }
    
    public Object getResponse(HttpServletRequest request) throws Exception 
    {
        String path = request.getPathInfo();
        if (StringUtils.isBlank(path))
        {
            throw new InvalidObjectException("Path not found");
        }
        for (RestRequestPathProcessor processor : processors)
        {
            if (processor.getPathPattern().matcher(path).matches())
            {
                return processor.getResponse(request, path);
            }
        }
        return null;
    }
    
    public void setProcessors(RestRequestPathProcessor[] processors) 
    {
        this.processors = processors;
    }
    
    public RestRequestPathProcessor[] getProcessors() 
    {
        return processors;
    }
}
