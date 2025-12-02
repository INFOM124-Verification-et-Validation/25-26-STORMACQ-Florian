Code Smells Identification
--------------------------

Observing the `GildedRose.java` file, several code smells can be identified that indicate areas for improvement:
1. **Long Method**: The `updateQuality` method is excessively long and complex, making it difficult to read and maintain. It would benefit from being broken down into smaller, more focused methods. May be a method for each item type.
2. **Duplicated Code**: Several patterns are repeated:
    - Quality boundary checks (< 50, > 0)
    - Quality increment/decrement operations
    - Item name equality checks
3. **Switch Statements**: The code uses string comparisons to determine item type behavior instead of using polymorphism or a strategy pattern.
4. **Feature Envy**: The method constantly accesses and modifies the Item object's fields (quality, sellIn, name) rather than letting the Item handle its own behavior.
5. **Primitive Obsession**: The use of strings to represent item types leads to fragile code that is hard to extend. Using subclasses or an enum would be more robust.