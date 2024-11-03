package com.example.projet2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "le nom est obligatoire")
    private String nom;
    private String description;
    @Positive(message = "le prix doit être positif")
    private double prix;
    @Positive(message = "le stock doit être positif")
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "fournisseur_id")
    private Fournisseur fournisseur;

    public Produit() {
    }

    public Produit(String nom, String description, double prix, int stock) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
    }

    public Produit(String nom, String description, double prix, int stock, Fournisseur fournisseur) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.fournisseur = fournisseur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "le nom est obligatoire") String getNom() {
        return nom;
    }

    public void setNom(@NotBlank(message = "le nom est obligatoire") String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Positive(message = "le prix doit être positif")
    public double getPrix() {
        return prix;
    }

    public void setPrix(@Positive(message = "le prix doit être positif") double prix) {
        this.prix = prix;
    }

    @Positive(message = "le stock doit être positif")
    public int getStock() {
        return stock;
    }

    public void setStock(@Positive(message = "le stock doit être positif") int stock) {
        this.stock = stock;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
}
