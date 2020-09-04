package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    UserEntity findByEmailVerificationToken(String token);

    @Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            countQuery = "select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            nativeQuery = true)
    Page<UserEntity> findAllUserWithConfirmedEmailAddress(Pageable pageableRequest);

    @Query(value = "select * from Users u where u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    @Query(value = "select * from Users u where u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

    @Query(value = "select * from Users u where u.first_name like %:keyword% or u.last_name like %:keyword%", nativeQuery = true)
    List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);

    @Query(value = "select u.first_name, u.last_name from Users u where u.first_name like %:keyword% or u.last_name like %:keyword%", nativeQuery = true)
    List<Object[]> findUserFistNameAndLastNameByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(value = "update users set EMAIL_VERIFICATION_STATUS = :emailVerificationStatus where user_id = :userId", nativeQuery = true)
    void updateUserEmailVerification(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);

    @Query("select user from UserEntity user where user.userId = :userId")
    UserEntity findUserEntityByUserId(@Param("userId") String userId);

    @Query("select user.firstName, user.lastName from UserEntity user where user.userId = :userId")
    List<Object[]> getuserEntityFullNameById(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query("update UserEntity user set user.emailVerificationStatus = :emailVerificationStatus where user_id = :userId")
    void updateUserEntityEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);

}
