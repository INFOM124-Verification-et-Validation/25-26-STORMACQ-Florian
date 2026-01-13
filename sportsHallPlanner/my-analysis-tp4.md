# Specification-based Testing

Here is the code of the `canFulfillRequest` method, which is used by the `planHalls` method to check whether a sports hall can fulfill a given request:

```java
/**
 * Checks whether this sports hall is suitable for the given requests.
 * It checks whether the required field is available, if there are enough fields,
 * and whether it has the required properties.
 * @param request the request to fulfill
 * @return true iff this sports hall can fulfill the request
*/
public boolean canFulfillRequest(Request request) {
return fields.containsKey(request.getRequiredFieldType())
        && fields.get(request.getRequiredFieldType()) >= request.getMinNumberOfFields()
        && properties.containsAll(request.getProperties());
}
```

## 1. Goal, inputs and outputs
- Goal: Check whether a sports hall can fulfill a given request.
- Input domain: Request objects with various field types, number of fields, and properties.
- Output domain: Boolean values (true or false).

## 2. Explore the program (if needed)

The `canFulfillRequest` method checks three main conditions:
1. The sports hall must have the required field type.
2. The sports hall must have at least the minimum number of fields requested.
3. The sports hall must have all the required properties specified in the request.

## 3. Identify input and output partitions

### Input partitions

#### Individual inputs

1. Field Type:
    - Badminton
    - Volleyball
    - Basketball
    - Tennis
    - Other types, why not ?
2. Number of Fields:
    - Less than required
    - Equal to required
    - More than required
3. Properties:
    - Near city centre
    - Has restaurant
    - close to public transport
    - Other properties, why not ?

#### Combinations of input values

1. Field Type + Number of Fields
    - Valid field type + Sufficient number of fields
    - Valid field type + Insufficient number of fields
    - Invalid field type + Any number of fields
2. Field Type + Properties
    - Valid field type + All required properties present
    - Valid field type + Some required properties missing
    - Invalid field type + Any properties
3. Number of Fields + Properties
    - Sufficient number of fields + All required properties present
    - Sufficient number of fields + Some required properties missing
    - Insufficient number of fields + Any properties

### Output partitions

## 4. Identify boundaries

1. Field Type:
    - Boundary between valid and invalid field types.
2. Number of Fields:
    - Boundary at the exact number of fields required.
3. Properties:
    - Boundary between having all required properties and missing at least one.

## 5. Select test cases

perplexity : 

A. Tests de base (Specification-based)

A1. Cas nominaux (Good Weather)

✅ validFieldTypeAndSufficientQuantityAndAllPropertiesTest - 1 request, 1 hall parfait → Match

emptyRequestsReturnsEmptyMapTest - 0 requests, N halls → Map vide {}

multipleRequestsMultipleHallsPerfectMatchTest - 3 requests, 3 halls (perfect match) → Toutes assignées

moreHallsThanRequestsTest - 2 requests, 5 halls disponibles → 2 halls utilisées, 3 inutilisées

firstHallChosenWhenMultipleSuitableTest - 1 request, 2 halls compatibles → Première hall choisie (ordre important)

allFieldTypesCanBeMatchedTest - Tester BADMINTON, TENNIS, VOLLEYBALL, BASKETBALL → Tous fonctionnent

multiplePropertiesSatisfiedTest - Request avec 3 properties, hall avec 4 → Match (superset OK)

A2. Cas d'échec (Bad Weather)

insufficientFieldsReturnsNullTest - Request 3 terrains, hall 2 terrains → null

wrongFieldTypeReturnsNullTest - Request TENNIS, hall a BADMINTON → null

missingPropertiesReturnsNullTest - Request NEAR_CITY, hall sans → null

noHallsAvailableReturnsNullTest - 1 request, 0 halls → null

noSuitableHallForAnyRequestReturnsNullTest - 3 requests, 3 halls incompatibles → null

impossiblePlanningReturnsNullTest - 2 requests identiques, 1 hall compatible → null (hall utilisée 1 fois max)

A3. Cas limites (Boundaries)

exactNumberOfFieldsMatchesTest - Request 2 terrains, hall 2 terrains (exact) → Match

oneFieldLessThanRequiredReturnsNullTest - Request 3 terrains, hall 2 terrains → null

oneFieldMoreThanRequiredMatchesTest - Request 2 terrains, hall 3 terrains → Match

emptyPropertiesRequestMatchesAnyHallTest - Request sans propriétés requises → Match si fields OK

emptyPropertiesHallMatchesOnlyEmptyRequestTest - Hall sans propriétés, request avec properties → null

allPropertiesRequiredAndPresentTest - Request 3 properties, hall 3 exactes → Match

partialPropertiesMatchReturnsNullTest - Request 2 properties, hall 1 seule → null

singleRequestSingleHallTest - Cas le plus simple (1-1) → Match

B. Tests de logique métier

B1. Ordre de priorité (First match wins)

firstRequestAssignedToFirstHallTest - R1 peut H1 ou H2, R2 peut H2 → R1→H1, R2→H2 (ordre respecté)

secondRequestGetsSecondHallTest - R1→H1, R2 ne peut que H2 → R2→H2

requestSkipsIncompatibleHallsTest - R1 incompatible avec H1, H2, H3 mais compatible H4 → R1→H4

orderOfHallsMattersTest - Même requests/halls, ordre halls inversé → Résultat différent

B2. Contraintes d'unicité

eachHallUsedAtMostOnceTest - 2 requests identiques, 1 hall compatible → null

duplicateHallsInListThrowsExceptionTest - Liste halls avec duplicata → IllegalArgumentException

duplicateRequestsCanBeAssignedToDistinctHallsTest - 2 requests identiques, 2 halls compatibles → Les 2 assignées

hallNotReusedAfterAssignmentTest - R1→H1, R2 compatible H1 mais déjà prise → R2 cherche ailleurs

B3. Backtracking (algorithme récursif)

backtrackingRequiredSimpleTest - R1 peut H1 ou H2, R2 ne peut que H1 → R1→H2, R2→H1 (backtrack)

backtrackingFailsWhenNoSolutionTest - R1→H1, R2 ne peut que H1 aussi → null

backtrackingWithThreeRequestsTest - R1 peut H1/H2/H3, R2 peut H1/H2, R3 ne peut que H1 → Solution optimale

deepBacktrackingTest - 5 requests, nécessite plusieurs retours en arrière → Solution trouvée

C. Tests structurels (Whitebox)

C1. Couverture de branches du code

duplicateHallsCheckCoveredTest - Ligne 29-31 → Exception levée
​

emptyRequestsEarlyReturnCoveredTest - Ligne 33-35 → Return new HashMap<>()
​

canFulfillRequestFalseBranchTest - Ligne 40 → if (hall.canFulfillRequest(current)) = false
​

canFulfillRequestTrueBranchTest - Ligne 40 → if (hall.canFulfillRequest(current)) = true
​

recursionReturnsNullBranchTest - Ligne 45-47 → if (solution != null) = false
​

recursionReturnsValidBranchTest - Ligne 45-47 → if (solution != null) = true
​

finalReturnNullCoveredTest - Ligne 51 → Boucle complète sans solution
​

C2. Boucles et récursion

loopIteratesOverAllHallsTest - Boucle for (SportsHall hall : halls) ligne 38 → Toutes les halls visitées

loopExitsEarlyWhenSolutionFoundTest - Boucle s'arrête dès qu'une solution est trouvée

recursionDepthOneTest - 1 request → Profondeur 1

recursionDepthFiveTest - 5 requests → Profondeur 5 (test récursion profonde)

recursionWithEmptyNextRequestsTest - Cas terminal de la récursion

C3. Manipulation de collections

nextRequestsListCorrectlyModifiedTest - nextRequests.remove(0) retire bien le premier élément

nextHallsListCorrectlyModifiedTest - nextHalls.remove(hall) retire bien la hall assignée

originalListsNotModifiedTest - Listes originales requests et halls non mutées

resultMapCorrectlyConstructedTest - solution.put(hall, current) ajoute bien l'entrée

D. Tests de propriétés et champs

D1. Types de champs (Field enum)

badmintonFieldTypeTest - Request/Hall avec BADMINTON

tennisFieldTypeTest - Request/Hall avec TENNIS

volleyballFieldTypeTest - Request/Hall avec VOLLEYBALL

basketballFieldTypeTest - Request/Hall avec BASKETBALL

mixedFieldTypesInHallTest - Hall avec plusieurs types de terrains

D2. Propriétés (Property enum)

nearCityCentrePropertyTest - Property NEAR_CITY_CENTRE

hasRestaurantPropertyTest - Property HAS_RESTAURANT

closePublicTransportPropertyTest - Property CLOSE_PUBLIC_TRANSPORT

multiplePropertiesInRequestTest - Request avec les 3 properties

multiplePropertiesInHallTest - Hall avec les 3 properties

E. Tests de performance et edge cases

E1. Edge cases

zeroFieldsRequestedTest - Request 0 terrains → Comportement ?

negativeFieldsRequestedTest - Request -1 terrains → Comportement ? (devrait être invalide)

veryLargeNumberOfFieldsTest - Request 1000 terrains → Fonctionne normalement

emptyFieldsMapInHallTest - Hall sans aucun terrain → Ne peut satisfaire aucune request

nullFieldTypeInRequestTest - Request avec fieldType = null → Exception ?

nullPropertiesInRequestTest - Request avec properties = null → Exception ?

E2. Performance

largeNumberOfRequestsTest - 100 requests, 100 halls → Temps raisonnable ?

largeNumberOfHallsTest - 1 request, 1000 halls → Temps raisonnable ?

worstCaseBacktrackingTest - Configuration nécessitant exploration maximale

F. Tests d'intégration avec méthodes auxiliaires

canFulfillRequestIntegrationTest - Vérifier que canFulfillRequest() est bien appelée

requestGettersUsedCorrectlyTest - getRequiredFieldType(), getMinNumberOfFields(), getProperties() appelées

hallFieldsAccessedCorrectlyTest - Map fields correctement consultée

hallPropertiesAccessedCorrectlyTest - Set properties correctement consulté