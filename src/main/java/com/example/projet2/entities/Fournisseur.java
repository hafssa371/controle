package com.example.projet2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;

    @OneToMany(mappedBy = "fournisseur",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Produit> produits = new ArrayList<>();

    public Fournisseur() {
    }

    public Fournisseur(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }

    public Fournisseur(String nom, String adresse, List<Produit> produits) {
        this.nom = nom;
        this.adresse = adresse;
        this.produits = produits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
        for (Produit p : produits)
            p.setFournisseur(this);
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void ajouterProduit(Produit pd){
        produits.add(pd);
        pd.setFournisseur(this);
    }
    
}
