package com.capgemini.todoweb.data;

import com.capgemini.todoweb.models.TodoModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<TodoModel, Long> {

    Iterable<TodoModel> findByTitleContainsIgnoreCaseOrderByDoneAscDoneAtAscUpdatedAtDesc(String title);
    @Transactional
    void deleteByTitleContainsIgnoreCase(String title);

    @Transactional
    void deleteByDoneTrue();

    @Transactional
    void deleteByDoneTrueAndTitleContainsIgnoreCase(String title);
}