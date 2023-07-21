package com.jakomulski.pwctest;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.jakomulski.pwctest.models.Country;
import com.jakomulski.pwctest.models.Route;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class RoutingService {

    private final Map<Country, Map<Country, Country>> countryToPredecessors = new HashMap<>();
    private final Map<String, Country> cca3ToCountry;
    private final Graph<Country> countriesGraph;

    public RoutingService(CountryRepository countryRepository) {
        cca3ToCountry = new HashMap<>();
        List<Country> countries = countryRepository.getAll();
        countries.forEach(c -> cca3ToCountry.put(c.cca3(), c));
        ImmutableGraph.Builder<Country> graphBuilder = GraphBuilder.undirected().immutable();
        for (Country country : countries) {
            graphBuilder.addNode(country);
            for (String cca3 : country.borders()) {
                if (cca3ToCountry.containsKey(cca3)) {
                    graphBuilder.putEdge(country, cca3ToCountry.get(cca3));
                }
            }
        }
        countriesGraph = graphBuilder.build();
    }

    public Optional<Route> findRoute(String cca3Origin, String cca3Destination) {
        if (!cca3ToCountry.containsKey(cca3Origin) || !cca3ToCountry.containsKey(cca3Destination)) {
            return Optional.empty();
        }
        Country start = cca3ToCountry.get(cca3Origin);
        Country end = cca3ToCountry.get(cca3Destination);
        if (!countryToPredecessors.containsKey(end)) {
            search(end);
        }
        List<String> route = findRouteFromPredecessors(start, end);
        return route.size() <= 1 ? Optional.empty() : Optional.of(new Route(route));
    }

    private List<String> findRouteFromPredecessors(Country start, Country end) {
        return Stream.iterate(start, Objects::nonNull, countryToPredecessors.get(end)::get).map(Country::cca3).toList();
    }

    private void search(Country root) {
        ArrayDeque<Country> queue = new ArrayDeque<>();
        Set<Country> visited = new HashSet<>();
        visited.add(root);
        Map<Country, Country> predecessors = new HashMap<>();
        for (Country current = root; current != null; current = queue.poll()) {
            for (Country neighbour : countriesGraph.adjacentNodes(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    predecessors.put(neighbour, current);
                }
            }
        }
        countryToPredecessors.put(root, predecessors);
    }
}
