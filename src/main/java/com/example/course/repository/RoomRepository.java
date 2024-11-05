package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
