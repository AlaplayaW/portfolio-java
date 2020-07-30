package com.perrine.portfolio.repository;

import com.perrine.portfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

	List<Portfolio> findAll();

}
