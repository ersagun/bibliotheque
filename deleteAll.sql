use bibliotheque;
SET foreign_key_checks = 0;
DELETE FROM auteur;
DELETE FROM oeuvre;
DELETE FROM livre;
DELETE FROM auteur_livre;
DELETE FROM exemplaire;
Delete from magazine;
Delete from usager;
Delete from reservation;
Delete from auteur;
SET foreign_key_checks = 1;

