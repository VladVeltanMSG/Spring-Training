package ro.msg.learning.shop.domain;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.util.UUID;


@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
}
