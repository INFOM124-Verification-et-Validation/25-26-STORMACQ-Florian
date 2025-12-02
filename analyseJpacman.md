TP VV
-----

See : https://refactoring.guru/design-patterns

Design patterns ?

1. Classe `Player` = Change preventers = Divergent change -> Classe qui a trop de responsibilités, nécessite une refactorisation.
   - Technique utilisée pour diagnostiquer : Lecture et compréhension du code source.
   - Action corrective : Extraire les différentes responsibilités dans de nouvelles ou autres classes.
   - Pratique et développement à modifier : Code review, SPEC = Single Responsibility Principle, travailler sur des branches séparées pour chaque fonctionnalité (branch per feature, the smallest possible change), pull request.
2. Classe `Player` : `PlayerScoreManager` < `ScoreManager` < `Score` = Message Shains -> Classe qui passe des messages à travers plusieurs objets pour accomplir une tâche.
   - Technique utilisée pour diagnostiquer : Lecture, compréhension et traçage des appels de méthodes.
   - Action corrective : Suppression des classes intermédiaires et centralisation de la logique dans une seule classe.
   - Pratique et développement à modifier : Pair Programming, code review.