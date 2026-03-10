package com.globalhub.main.repository;

import com.globalhub.main.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {

    @Query("SELECT t FROM Team t JOIN t.teachers p WHERE p.id = :teacherUuid AND t.isActive = true")
    Page<Team> findAllByTeacherUuid(@Param("teacherUuid") UUID teacherUuid, Pageable pageable);

    @Query("SELECT t FROM Team t WHERE t.day = :day AND t.isActive = true")
    Page<Team> findAllByDay(@Param("day")DayOfWeek day, Pageable pageable);

    @Query("SELECT t.room FROM Team t WHERE t.day = :day AND t.time = :time")
    List<Integer> findOccupiedRooms(@Param("day") DayOfWeek day, @Param("time") LocalTime time);

    @Query("SELECT COUNT(t) > 0 FROM Team t JOIN t.teachers teacher WHERE teacher.id = :teacherId AND t.day = :day AND t.time = :time")
    boolean hasScheduleConflict(
            @Param("teacherId") UUID teacherId,
            @Param("day") DayOfWeek day,
            @Param("time") LocalTime time
    );

    @Query("SELECT COUNT(t) > 0 FROM Team t WHERE t.room = :room AND t.day = :day AND t.time = :time AND t.id != :teamId")
    boolean hasRoomConflictForUpdate(
            @Param("room") Integer room,
            @Param("day") DayOfWeek day,
            @Param("time") LocalTime time,
            @Param("teamId") UUID teamId
    );

    @Query("SELECT COUNT(t) > 0 FROM Team t JOIN t.teachers teacher WHERE teacher.id = :teacherId AND t.day = :day AND t.time = :time AND t.id != :teamId")
    boolean hasTeacherConflictForUpdate(
            @Param("teacherId") UUID teacherId,
            @Param("day") DayOfWeek day,
            @Param("time") LocalTime time,
            @Param("teamId") UUID teamId
    );

}
