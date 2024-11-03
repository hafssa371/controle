package com.example.projet2.controllers;

import com.example.projet2.entities.Fournisseur;
import com.example.projet2.entities.Produit;
import com.example.projet2.repositories.FournisseurRepository;
import com.example.projet2.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("fournisseurs")
public class FournisseurController {
    @Autowired
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public String getAllFournisseurs(Model model) {
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        return "fournisseurs";
    }

    @GetMapping("/ajouter")
    public String afficherFormulaireAjouter(Model model) {
        model.addAttribute("fournisseur", new Fournisseur());
        List<Produit> produitsList = produitRepository.findAll();
        model.addAttribute("produitList", produitsList);
        return "ajouterFournisseur";
    }

    @PostMapping("/ajouter")
    public String ajouterFournisseur(@ModelAttribute Fournisseur fournisseur) {
        fournisseurRepository.save(fournisseur);
        return "redirect:/fournisseurs";
    }
// fournisseur sipésifié
    @GetMapping("/detail/{id}")
    public String afficherDetail(@PathVariable Long id, Model model) {
        Optional<Fournisseur> optionalFournisseur = fournisseurRepository.findById(id);

        if (optionalFournisseur.isPresent()) {
            Fournisseur fournisseur = optionalFournisseur.get();
            model.addAttribute("fournisseur", fournisseur);
            model.addAttribute("produits", fournisseur.getProduits());
        } else {
            // Gérer le cas où le fournisseur n'existe pas
            model.addAttribute("message", "Fournisseur non trouvé");
            return "error"; // Redirigez vers une page d'erreur ou une autre page appropriée
        }

        return "detailFournisseur";
    }

    @GetMapping("/edit/{id}")
    public String afficherFormulaireModifier(@PathVariable Long id, Model model) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur introuvable"));
        model.addAttribute("fournisseur", fournisseur);
        return "modifierFournisseur";
    }

    @PostMapping("/edit/{id}")
    public String modifierFournisseur(@PathVariable Long id, @ModelAttribute Fournisseur fournisseur) {
        Fournisseur existingFournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur introuvable"));
        // Mise à jour des informations du fournisseur
        existingFournisseur.setNom(fournisseur.getNom());
        existingFournisseur.setAdresse(fournisseur.getAdresse());
        existingFournisseur.setProduits(fournisseur.getProduits());
        fournisseurRepository.save(existingFournisseur);
        return "redirect:/fournisseurs";
    }

    @PostMapping("/delete/{id}")
    public String deleteFournisseur(@PathVariable Long id) {
        if (fournisseurRepository.existsById(id)) {
            fournisseurRepository.deleteById(id);
        } else {
            throw new RuntimeException("Fournisseur introuvable");
        }
        return "redirect:/fournisseurs";
    }
}

