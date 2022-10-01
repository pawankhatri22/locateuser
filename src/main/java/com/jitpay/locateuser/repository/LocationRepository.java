package com.jitpay.locateuser.repository;

import com.jitpay.locateuser.entity.Location;
import com.jitpay.locateuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findTop1ByUserOrderByCreatedOnDesc(User userId);
    List<Location> findByUserAndCreatedOnBetween(User user, LocalDateTime fromDate, LocalDateTime toDate);
}
