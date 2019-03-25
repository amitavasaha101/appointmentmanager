package com.informationcafe.appointmentmanager.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.informationcafe.appointmentmanager.model.Appointment;

public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, Long> {
	List<Appointment> findByUsername(String username);
}
