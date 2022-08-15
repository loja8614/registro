package com.officehours.registry.repository;

import com.officehours.registry.model.Registry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistryRepository extends CrudRepository<Registry,Integer> {
    Registry save(Registry registry);

    @Query("SELECT r FROM Registry r WHERE r.people.id = :peopleId")
    List<Registry> findByPeopleId(@Param("peopleId") UUID peopleId);

    @Query("SELECT r FROM Registry r WHERE r.car.vin = :carVin")
    List<Registry> findByCarVin(@Param("carVin") String vin);

    @Query("SELECT r FROM Registry r WHERE r.car.vin = :carVin and r.people.id = :peopleId")
    List<Registry> findByPeopleIdCarVin(@Param("carVin") String vin,
                                        @Param("peopleId") UUID peopleId);
}
