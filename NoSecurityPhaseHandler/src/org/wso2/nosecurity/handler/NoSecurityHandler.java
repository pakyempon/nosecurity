/*
 * Copyright The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.nosecurity.handler;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This is no Security handler for Axis2, Carbon based products that uses rampart as security module
 */
public class NoSecurityHandler extends AbstractHandler {

	private static final String RAMPART = "rampart";
    private static final String NO_SECURITY = "nosecurity";
        
    public InvocationResponse invoke(MessageContext messageContext) throws AxisFault {

//    	if(!messageContext.isEngaged(NO_SECURITY)){
//            return InvocationResponse.CONTINUE;
//        }
//
//        System.out.println(" No Security handler is hit......!!!");

        if(!messageContext.isEngaged(RAMPART)){
            return InvocationResponse.CONTINUE;
        }
        
        if("true".equals(messageContext.getProperty("synapse.send"))){
            InputStream stream = new ByteArrayInputStream(getPolicy().getBytes());
            Policy policy = PolicyEngine.getPolicy(stream);
            if(policy != null){
                messageContext.getOptions().getParent().getParent().setProperty("rampartPolicy", null);
                messageContext.setProperty("rampartInPolicy", policy);
            }
        }
        return InvocationResponse.CONTINUE;
        
//        // Disable IN security for Magda Services       
//        if("true".equals(messageContext.getProperty("synapse.send"))){
//            InputStream stream = new ByteArrayInputStream(getPolicy().getBytes());
//            Policy policy = PolicyEngine.getPolicy(stream);
//            if(policy != null){
//                messageContext.getOptions().getParent().getParent().setProperty("rampartPolicy", null);
//                messageContext.setProperty("rampartInPolicy", policy);
//            }
//        }
//        return InvocationResponse.CONTINUE;

        // Disable OUT security for WSO2 AS or any other Axis2 server
        
//        InputStream stream = new ByteArrayInputStream(getPolicy().getBytes());
//        Policy policy = PolicyEngine.getPolicy(stream);
//        if(policy != null){
//            messageContext.setProperty("rampartOutPolicy", policy);
//        }
//        return InvocationResponse.CONTINUE;

    }

    public String getName() {
        return "NoSecurityHandler";
    }

    private String getPolicy(){       
        return "<wsp:Policy wsu:Id=\"emptryPolicy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" " +
                "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
                "<wsp:ExactlyOne><wsp:All><sp:TransportBinding xmlns:sp=\"http://schemas.xmlsoap.org/ws/2005/07/securitypolicy\">" +
                "<wsp:Policy></wsp:Policy></sp:TransportBinding></wsp:All></wsp:ExactlyOne></wsp:Policy>";
    }
}