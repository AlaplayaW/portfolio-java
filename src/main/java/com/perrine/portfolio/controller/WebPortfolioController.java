package com.perrine.portfolio.controller;


import com.perrine.portfolio.model.Portfolio;
import com.perrine.portfolio.model.Tag;
import com.perrine.portfolio.repository.PortfolioRepository;
import com.perrine.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Controller
public class WebPortfolioController {
    @Autowired
    PortfolioRepository portfolioRepo;
    @Autowired
    TagRepository tagRepo;

    @GetMapping("/portfolios")
    public String getAllPortfolios(Model model) {
        model.addAttribute("portfolios", portfolioRepo.findAll());
        return "portfolios";
    }

    @GetMapping("/portfolio")
    public String getPortfolio(Model model,
                               @RequestParam(required = false) Long id) {
        Portfolio portfolio = new Portfolio();

        if (id != null) {
            Optional<Portfolio> optionalPortfolio = portfolioRepo.findById(id);
            if (optionalPortfolio.isPresent()) {
                portfolio = optionalPortfolio.get();
            }
        }

        model.addAttribute("portfolios", portfolioRepo.findAll());
//        model.addAttribute("allTags", tagRepo.findAll());
        model.addAttribute("portfolio", portfolio);

        return "portfolio";
    }

    @PostMapping("/portfolio")
    public String postPortfolio(@RequestParam(required = false) Long idPortfolio,
                                @RequestParam(required = false) Long idTag) {

        Optional<Portfolio> optionalPortfolio = portfolioRepo.findById(idPortfolio);
        if (optionalPortfolio.isPresent()) {
            Portfolio portfolio = optionalPortfolio.get();

            Optional<Tag> optionalTag = tagRepo.findById(idTag);
            if (optionalTag.isPresent()) {
                Tag tag = optionalTag.get();

                // call the method getTags in Portfolio
                List<Tag> tags;
                Method method = getMethod(portfolio, "getTags",
                        new Class[]{});
                if (method != null) {
                    try {
                        tags = (List<Tag>) method.invoke(portfolio);
                        tags.add(tag);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                portfolioRepo.save(portfolio);
            }
        }
        return "redirect:/portfolio?idPortfolio=" + idPortfolio;
    }

    @PutMapping("/portfolios/{id}")
    public Portfolio update(@PathVariable Long id, @RequestBody Portfolio portfolio) {
        // getting portfolio
        Portfolio portfolioToUpdate = portfolioRepo.findById(id).get();
        portfolioToUpdate.setTitle(portfolio.getTitle());
        portfolioToUpdate.setDescription(portfolio.getDescription());
        return portfolioRepo.save(portfolioToUpdate);
    }

    @DeleteMapping("portfolios/{id}")
    public boolean delete(@PathVariable Long id) {
        portfolioRepo.deleteById(id);
        return true;
    }

    public Method getMethod(Object obj, String methodName, Class[] args) {
        Method method;
        try {
            method = obj.getClass().getDeclaredMethod(methodName, args);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}