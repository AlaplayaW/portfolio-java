package com.perrine.portfolio.controller;


import com.perrine.portfolio.model.Portfolio;
import com.perrine.portfolio.repository.PortfolioRepository;
import com.perrine.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiPortfolioController {

    @Autowired
    PortfolioRepository portfolioRepo;
    @Autowired
    TagRepository tagRepo;

    @GetMapping("/api/portfolios")
    public List<Portfolio> index(){

        return portfolioRepo.findAll();
    }

    @GetMapping("/api/portfolios/{id}")
    public Portfolio show(@PathVariable Long id){
        return portfolioRepo.findById(id).get();
    }

    @PostMapping("/api/portfolios")
    public Portfolio create(@RequestBody Portfolio portfolio){
        return portfolioRepo.save(portfolio);
    }

    @PutMapping("/api/portfolios/{id}")
    public Portfolio update(@PathVariable Long id, @RequestBody Portfolio portfolio){
        // getting portfolio
        Portfolio portfolioToUpdate = portfolioRepo.findById(id).get();
        portfolioToUpdate.setTitle(portfolio.getTitle());
        portfolioToUpdate.setDescription(portfolio.getDescription());
        return portfolioRepo.save(portfolioToUpdate);
    }

    @DeleteMapping("/api/portfolios/{id}")
    public boolean delete(@PathVariable Long id){
        portfolioRepo.deleteById(id);
        return true;
    }
}