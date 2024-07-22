package com.span.mockstar.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//TODO: Implement fallback strategy
@FeignClient(value = "gateway", url = "http://localhost:8081", configuration = FeignConfig.class)
public interface GatewayClient {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    String triggerGET(@RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    String triggerGETWithBody(@RequestBody(required = false) String content, @RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    String triggerPOST(@RequestBody(required = false) String content, @RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    String triggerPOSTNoBody(@RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    String triggerPUT(@RequestBody(required = false) String content, @RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    String triggerPUTNoBody(@RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    String triggerDELETE(@RequestBody(required = false) String content, @RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    String triggerDELETENoBody(@RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.OPTIONS, produces = "application/json")
    String triggerOPTIONS(@RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.PATCH, produces = "application/json")
    String triggerPATCH(@RequestBody(required = false) String content, @RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.PATCH, produces = "application/json")
    String triggerPATCHNoBody(@RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.TRACE, produces = "application/json")
    String triggerTRACE(@RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.HEAD, produces = "application/json")
    String triggerHEAD(@RequestHeader HttpHeaders headers);
}
