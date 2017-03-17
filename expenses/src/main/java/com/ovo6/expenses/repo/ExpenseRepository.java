package com.ovo6.expenses.repo;

import com.ovo6.expenses.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Repository for expenses.
 * Some methods are not exported to REST API and custom implementation is used from ExpensesController.
 * User manager cannot user this API.
 */
@PreAuthorize("isFullyAuthenticated() && hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {

    String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Override
    @RestResource(exported=false)
    Expense findOne(Long id);

    @Override
    @RestResource(exported=false)
    void delete(Expense entity);

    /**
     * Implemented in controller.
     */
    @Override
    @RestResource(exported=false)
    Expense save(Expense expense);


    /**
     * Adding custom order (datetime desc).
     * We query for all users in case of admin, otherwise for expenses of current uer only.
     */
    @Override
    @Query("select x from Expense x where x.user.name like ?#{hasRole('ROLE_ADMIN') ? '%' : authentication.name} order by x.datetime desc")
    Page<Expense> findAll(Pageable pageable);



    @Query("select x from Expense x where " +
            "x.amount <= :maxAmount " +
            "and x.amount >= :minAmount " +
            "and x.description like '%' || :description || '%' " +
            "and x.datetime >= :startDate " +
            "and x.datetime <= :endDate " +
            "and x.user.name like ?#{hasRole('ROLE_ADMIN') ? '%' : authentication.name} order by x.datetime desc")
    Page<Expense> filter(Pageable pageable,
                         @Param("minAmount") BigDecimal minAmount,
                         @Param("maxAmount") BigDecimal maxAmount,
                         @Param("description") String description,
                         @DateTimeFormat(pattern = ISO_FORMAT) @Param("startDate") Date startDate,
                         @DateTimeFormat(pattern = ISO_FORMAT) @Param("endDate") Date endDate);


    /**
     * Return average and sum from expenses filtered by given filters.
     * @return ExpenseStats with average and sum
     */
    @RestResource(exported=false) // cannot export as ExpenseStats is not an entity
    @Query("select new com.ovo6.expenses.repo.ExpenseStats(sum(x.amount), avg(x.amount)) from Expense x where " +
            "x.amount <= :maxAmount " +
            "and x.amount >= :minAmount " +
            "and x.description like '%' || :description || '%' " +
            "and x.datetime >= :startDate " +
            "and x.datetime <= :endDate " +
            "and x.user.name like ?#{hasRole('ROLE_ADMIN') ? '%' : authentication.name}")
    ExpenseStats stats(
                         @Param("minAmount") BigDecimal minAmount,
                         @Param("maxAmount") BigDecimal maxAmount,
                         @Param("description") String description,
                         @Param("startDate") Date startDate,
                         @Param("endDate") Date endDate
    );


}