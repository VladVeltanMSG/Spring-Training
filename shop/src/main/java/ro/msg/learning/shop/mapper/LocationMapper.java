package ro.msg.learning.shop.mapper;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Location;
import ro.msg.learning.shop.dto.LocationDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {
    public void toEntity(LocationDto locationDto, Location location) {
        location.setName(locationDto.getName());
        location.setCountry(locationDto.getCountry());
        location.setCity(locationDto.getCity());
        location.setCounty(locationDto.getCounty());
        location.setStreetAddress(locationDto.getStreetAddress());
    }

    public LocationDto toDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setName(location.getName());
        locationDto.setCountry(location.getCountry());
        locationDto.setCity(location.getCity());
        locationDto.setCounty(location.getCounty());
        locationDto.setStreetAddress(location.getStreetAddress());
        return locationDto;
    }

    public List<LocationDto> toDtoList(List<Location> locations) {
        return locations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
