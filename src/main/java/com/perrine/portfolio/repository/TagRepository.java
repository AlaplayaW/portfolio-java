package com.perrine.portfolio.repository;

import com.perrine.portfolio.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

//    List<Tag> findAllInPortfolio();
//	@Query("Select t.name from Tag t")
//    List<String> findAllTagNames();
//
	List<Tag> findAllByNameAllIgnoreCaseIn(String[] tagsName);
//
//	Tag findByName(@Param("name") String tagName);
}
