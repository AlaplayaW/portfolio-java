package com.perrine.portfolio.repository;

import com.perrine.portfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

	public List<Portfolio> findAll();

//    @Modifying
//    @Transactional
//    @Query("UPDATE Portfolio p SET p.coverImage.id = :fileId WHERE p.id = :portfolioId")
//    public void setCoverImage(@Param("portfolioId") Integer portfolioId, @Param("fileId") Integer fileId);
}
