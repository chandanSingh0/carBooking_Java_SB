package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.model.Licence;

public interface LicenceRepository extends JpaRepository<Licence, Integer> {

}
