package com.example.projet2.controllers;

import com.example.projet2.entities.Fournisseur;
import com.example.projet2.entities.Produit;
import com.example.projet2.repositories.FournisseurRepository;
import com.example.projet2.repositories.ProduitRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/produits")
public class ProduitController {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private FournisseurRepository fournisseurRepository;

    // Afficher tous les produits
    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "produits"; // Nom de la vue pour afficher tous les produits
    }

    // Afficher le formulaire pour ajouter un nouveau produit
    @GetMapping("/ajouter")
    public String afficherFormulaireAjouter(Model model) {
        model.addAttribute("produit", new Produit());
        return "ajouterproduit"; // Nom de la vue pour ajouter un produit
    }

    // Traiter le formulaire d'ajout d'un nouveau produit
    @PostMapping("/ajouter")
    public String ajouterProduit(@Valid @ModelAttribute("produit") Produit produit,
                                 BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("fournisseurs", fournisseurRepository.findAll());
            return "ajouterproduit";
        }
        Fournisseur fournisseur = fournisseurRepository.findById(produit.getFournisseur().getId())
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
        produit.setFournisseur(fournisseur);
        produitRepository.save(produit);
        return "redirect:/produits"; // Redirige vers la liste des produits après l'ajout
    }

    // Afficher les détails d'un produit
    @GetMapping("/detail/{id}")
    public String afficherDetail(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        model.addAttribute("produit", produit);
        return "detailProduits"; // Nom de la vue pour afficher les détails du produit
    }

    // Afficher le formulaire pour modifier un produit existant
    @GetMapping("/edit/{id}")
    public String afficherFormulaireModifier(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        model.addAttribute("produit", produit);
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        return "modifierProduit"; // Nom de la vue pour modifier un produit
    }

    // Traiter le formulaire de modification d'un produit existant
    @PostMapping("/edit/{id}")
    public String modifierProduit(@PathVariable Long id, @ModelAttribute Produit produit) {
        Produit existingProduit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        // Mise à jour des informations du produit
        existingProduit.setNom(produit.getNom());
        existingProduit.setDescription(produit.getDescription());
        existingProduit.setPrix(produit.getPrix());
        existingProduit.setStock(produit.getStock());
        produitRepository.save(existingProduit);
        return "redirect:/produits"; // Redirige vers la liste des produits après la modification
    }

    // Supprimer un produit
    @PostMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Long id) {
        produitRepository.deleteById(id);
        return "redirect:/produits"; // Redirige vers la liste des produits après la suppression
    }
}

