# nosecurity
 <!-- FIX: Needed for skipping WS-Security in response path in combination with NoSecurityHandler (WSO2 Support Ticket:DIGIPOLISGENTPROD-56) -->
            <!-- Do Nothing -->
            <property name="NoSecurityProperty" scope="axis2-client" type="STRING" value="true"/>
            <!-- FIX: Needed for determining origin of request, which is needed for determining if response needs to be send back to client -->
             <parameter name="engagedModules">nosecurity</parameter>
