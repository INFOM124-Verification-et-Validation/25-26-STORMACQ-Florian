# Specification-based Testing

## Analyse GPT
## But
- Comprendre et tester le comportement de l'IA de `Inky` : comment elle calcule sa cible et renvoie la prochaine direction.

## Description succincte de l'implémentation
- `Inky.nextAiMove()` :
	1. Trouve `Blinky` (le fantôme rouge) et le `Player` les plus proches.
	2. Calcule la `playerDestination` = case située `SQUARES_AHEAD` (2) devant le joueur via `player.squaresAheadOf(2)`.
	3. Calcule `firstHalf` = `Navigation.shortestPath(blinky.getSquare(), playerDestination, null)` — ici le `traveller` est `null`, donc le chemin est déterminé "en ignorant le terrain" (approximation décrite dans le code).
	4. Appelle `followPath(firstHalf, playerDestination)` : parcourt les directions du chemin `firstHalf`, mais en partant de `playerDestination`, et suit ces directions pour obtenir la `destination` finale (c'est l'approximation de "prolonger la ligne Blinky→playerDestination").
	5. Calcule `path` = `Navigation.shortestPath(getSquare(), destination, this)` et renvoie la première direction si elle existe.

## Propriétés attendues / oracle
- Si `Blinky` ou `Player` manquent => `Optional.empty()`.
- Si `playerDestination` est hors-carte (ou devient `null`), l'appel peut échouer ; l'implémentation actuelle ne vérifie pas explicitement `null`.
- La destination calculée est l'emplacement obtenu en prolongeant (approximativement) le segment `Blinky→(2 devant Pac-Man)` d'autant de cases ; la direction renvoyée est la première étape du chemin vers cette destination.

## Partitions d'entrée importantes
- Présence/absence de `Blinky`.
- Présence/absence de `Player`.
- `playerDestination` valide (toujours sur la board) vs `playerDestination == null` (player trop près d'un bord).
- `firstHalf == null` (pas de chemin calculable entre Blinky et playerDestination) vs `firstHalf` non vide.
- `followPath` reste à l'intérieur de la board vs rencontre de `null` lors du parcours.

## Cas limites et bugs potentiels (observés dans le code)
- `Unit.squaresAheadOf(int)` : ne vérifie pas si `getSquareAt(...)` retourne `null`. Si le joueur est proche du bord et que l'on regarde 2 cases devant, `squaresAheadOf` peut retourner `null` (NPE possible ensuite).
- `followPath(List<Direction>, Square)` applique `destination = destination.getSquareAt(d)` sans vérifier `null` : si une des étapes mène hors-carte, une NPE survient.
- `Navigation.shortestPath(..., null)` est utilisé pour ignorer le terrain — c'est voulu mais produit des chemins qui peuvent traverser des murs; suivi depuis `playerDestination` peut donc produire des destinations inattendues.

## Tests recommandés
1. Scénario nominal (Blinky et Player présents, playerDestination dans la carte) : vérifier que `nextAiMove()` renvoie une direction présente et que la case suivante est accessible.
2. Player proche du bord (playerDestination hors-carte) : vérifier que `nextAiMove()` ne provoque pas d'exception et renvoie `Optional.empty()` ou est géré proprement.
3. `firstHalf == null` (aucun chemin entre Blinky et playerDestination selon l'algorithme) : vérifier que `nextAiMove()` renvoie `Optional.empty()`.
4. `followPath` sortant de la map : simuler un `firstHalf` qui contient une direction menant hors-carte et vérifier que la méthode ne plante pas (ou alors documenter la précondition et écrire un test qui s'attend à `Optional.empty()`).
5. Cohérence avec la spécification classique : dans une petite grille ouverte, calculer explicitement la cible attendue (double vecteur) et vérifier que la direction renvoyée correspond.

## Suggestions de corrections (si on veut durcir le code)
- `Unit.squaresAheadOf(int)` : retourner la dernière case valide (ou `null`) en s'assurant de tester `getSquareAt(...)` avant de l'assigner ; documenter la valeur de retour.
- `followPath(...)` : arrêter la boucle si `destination.getSquareAt(d)` est `null` et retourner la dernière case valide, ou retourner `null` et laisser l'appelant gérer le cas.
- `Inky.nextAiMove()` : vérifier `playerDestination != null` avant d'appeler `Navigation.shortestPath(...)` et gérer proprement le cas où `followPath` retourne `null`.

## Exemples succincts de tests JUnit (esquisse)
// 1. Test nominal
// Construire une map où Blinky est à (x1,y1), Pac-Man à (x2,y2) et s'assurer que player.squaresAheadOf(2) existe.
// Appeler nextAiMove() et vérifier : present && nextSquare.isAccessibleTo(inky)

// 2. Test bord
// Placer Pac-Man à 1 case du bord et vérifier que nextAiMove() ne lève pas d'exception (Optional.empty() acceptable).

## 1. Goal, inputs and outputs

- Goal: Determine Inky's next move based on its AI rules.
- Input domain: No input parameters.
- Output domain: An `Optional<Direction>` representing Inky's next move direction.

## 2. Explore the program (if needed)

## 3. Identify input and output partitions

### Input partitions

#### Individual inputs

Path partitions:
- P1: No path from Blinky to playerDestination
- P2: Valid path from Blinky to playerDestination
- P3: Path from Inky to destination is clear
- P4: Path from Inky to destination is blocked


#### Combinations of input values

### Output partitions

## 4. Identify boundaries

- No Blinky on the Map
- No Player on the Map

## 5. Select test cases

- T1: Blinky and Player present, valid paths exist (good weather). DONE
- T2: Inky to Blinky path blocked, valid path to destination (good weather). DONE 
- T3: Blinky missing (bad weather). DONE
- T4: Player missing (bad weather). DONE
- T5: No path from Blinky to playerDestination (bad weather). DONE