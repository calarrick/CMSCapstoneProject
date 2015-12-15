/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thesoftwareguild.lawinorder.exception;

import javax.servlet.http.HttpServletResponse;
  import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author calarrick
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException

{
    
    String missingUrl;
    String message;

    
    
    
    public ResourceNotFoundException(String missingUrl){
        
        this.missingUrl = missingUrl;
        this.message = missingUrl;
        
        
        
    }

    public ResourceNotFoundException(){
        
    }
    
    
}


