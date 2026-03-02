package com.uniovi.sdi.grademanager.repositories;
import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarksRepository extends JpaRepository<Mark, Long> {
    Page<Mark> findAll(Pageable pageable);

    @Modifying
    @Query("UPDATE Mark SET resend = :resend WHERE id = :id")
    void updateResend(@Param("resend") Boolean resend, @Param("id") Long id);

    Page<Mark> findAllByUserOrderByIdAsc(Pageable pageable, User user);

    @Query("SELECT m FROM Mark m WHERE " +
            "LOWER(m.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(m.user.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(m.user.dni) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<Mark> searchByDescriptionNameAndDni(Pageable pageable, @Param("searchText") String searchText);

    @Query("SELECT m FROM Mark m WHERE m.user = :user AND " +
            "(LOWER(m.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(m.user.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(m.user.dni) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Mark> searchByUser(Pageable pageable, @Param("user") User user, @Param("searchText") String searchText);
}
