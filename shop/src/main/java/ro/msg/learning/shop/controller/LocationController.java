package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.LocationDto;
import ro.msg.learning.shop.service.LocationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable UUID id) {
        LocationDto locationDto = locationService.getLocationById(id);
        return ResponseEntity.ok(locationDto);
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        LocationDto createdLocation = locationService.createLocation(locationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable UUID id, @RequestBody LocationDto locationDto) {
        LocationDto updatedLocation = locationService.updateLocation(id, locationDto);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
