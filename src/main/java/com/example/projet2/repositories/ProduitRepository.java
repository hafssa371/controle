package com.example.projet2.repositories;

import com.example.projet2.entities.Fournisseur;
import com.example.projet2.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,Long> {
}
