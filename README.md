Le but de ce travail est de spécifier et concevoir un moteur de fusion multimodale pour interagir
avec une palette de dessin ne disposant d’aucun bouton de fonction. Les interactions possibles
sont :
 la reconnaissance de geste 2D,
 la reconnaissance de parole,
 le pointage sur la palette de dessin.

# Git Workflow
Tout sur master.

# Fonctionnement

Il faut lancer les .bat "sra.bat", "reco.bat", "palette.bat" et le fusionneur.

# Commandes implémentés

# Grammaire
La grammaire de la reconnaissance de parole inclut les commandes suivantes:
 
## Position
les mots "ici", "là" et "à cette position" provoquent un message "here" 

## Coleurs
Les couleurs rouge vert noir gris blanc bleu et jaune sont reconnus et provoquent l'envoi des messages correspondants en anglais (red green black gray white blue et yellow)

## Designation de l'objet

Les commandes "cette ellipse" "ce rectangle" et "cet objet" sont reconnus et envoient les messages thisellipse, thisrectangle et thisobject
