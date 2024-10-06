package com.example.springProject.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data // Lombok generates getters, setters, toString, equals, and hashCode
@Component
@Entity
@Scope("prototype")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

    @Id
    @Column(name = "cityID")
    private Integer cityId;

    @Column(name = "cityName", length = 100)
    private String cityName;
}
