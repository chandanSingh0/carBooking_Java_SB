package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.model.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer>{

}
