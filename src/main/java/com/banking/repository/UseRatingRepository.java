package com.banking.repository;

import org.springframework.data.repository.CrudRepository;

import com.banking.entity.UseRating;

public interface UseRatingRepository extends CrudRepository<UseRating, Long>  {
	UseRating findByYear(String year);
}
