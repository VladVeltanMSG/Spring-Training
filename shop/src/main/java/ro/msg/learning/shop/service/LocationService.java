package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.dto.LocationDto;
import ro.msg.learning.shop.exception.DuplicateResourceException;
import ro.msg.learning.shop.exception.ResourceNotFoundException;
import ro.msg.learning.shop.mapper.LocationMapper;
import ro.msg.learning.shop.repository.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {

    public static final String LOCATION_WITH_NAME = "Location with name '";
    public static final String ALREADY_EXISTS = "' already exists.";
    public static final String LOCATION_WITH_ID = "Location with ID '";
    public static final String NOT_FOUND = "' not found.";
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationMapper locationMapper;

    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locationMapper.toDtoList(locations);
    }

    public LocationDto getLocationById(UUID id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LOCATION_WITH_ID + id + NOT_FOUND));
        return locationMapper.toDto(location);
    }

    public LocationDto createLocation(LocationDto locationDto) {
        Location existingLocation = locationRepository.findByName(locationDto.getName());
        if (existingLocation != null) {
            throw new DuplicateResourceException(LOCATION_WITH_NAME + locationDto.getName() + ALREADY_EXISTS);
        }
        Location location = new Location();
        locationMapper.toEntity(locationDto, location);
        location = locationRepository.save(location);
        return locationMapper.toDto(location);
    }

    public LocationDto updateLocation(UUID id, LocationDto locationDto) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LOCATION_WITH_ID + id + NOT_FOUND));

        locationMapper.toEntity(locationDto, existingLocation);
        Location updatedLocation = locationRepository.save(existingLocation);
        return locationMapper.toDto(updatedLocation);
    }

    public void deleteLocation(UUID id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException(LOCATION_WITH_ID + id + NOT_FOUND);
        }
        locationRepository.deleteById(id);
    }

    public Optional<Location> findById(UUID locationId) {
        return locationRepository.findById(locationId);
    }

    public boolean existsById(UUID locationId) {
        return locationRepository.existsById(locationId);
    }
}
