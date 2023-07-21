package com.jakomulski.pwctest;

import com.jakomulski.pwctest.models.Route;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/routing")
public class RoutingController {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/{origin}/{destination}")
    public Route getRoute(@PathVariable String origin, @PathVariable String destination) {
        return routingService.findRoute(origin, destination).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Unable to find route"));
    }
}
