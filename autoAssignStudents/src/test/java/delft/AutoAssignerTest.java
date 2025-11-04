package delft;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.within;
import java.time.temporal.ChronoUnit;

import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.beans.Transient;
import java.time.*;

public class AutoAssignerTest {

    private ZonedDateTime date(int year, int month, int day, int hour, int minute) {
        return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, ZoneId.systemDefault());
    }

    @Test
    @DisplayName("Vérifie que hasAvailableDate fonctionne correctement")
    void testHasAvailableDate(){
        // Arrange
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 1, 10, 10, 0), 0); // Date avec 0 spots
        spotsPerDate.put(date(2024, 1, 20, 10, 0), 2); // Date avec des spots
        Workshop workshop = new Workshop(1, "C++ Workshop", spotsPerDate);
        
        // Act
        boolean result = workshop.hasAvailableDate();
        
        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Vérifie que hasAvailableDate retourne false quand aucune date n'a de spots")
    void testHasNoAvailableDate(){
        // Arrange
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 1, 10, 10, 0), 0); // Date avec 0 spots
        spotsPerDate.put(date(2024, 1, 20, 10, 0), 0); // Date avec 0 spots
        Workshop workshop = new Workshop(1, "C++ Workshop", spotsPerDate);
        AutoAssigner assigner = new AutoAssigner();
        
        // Act
        boolean result = workshop.hasAvailableDate();

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Vérifie que takeASpot diminue le nombre de spots disponibles")
    void testTakeASpot(){
        // Arrange
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        ZonedDateTime date1 = date(2024, 1, 10, 10, 0);
        spotsPerDate.put(date1, 2); // Date avec 2 spots
        Workshop workshop = new Workshop(1, "C++ Workshop", spotsPerDate);
        
        // Act
        workshop.takeASpot(date1);
        
        // Assert - Vérifie qu'il reste 1 spot
        assertThat(workshop.getSpotsPerDate().get(date1)).isEqualTo(1);
        
        // Act - Prendre le dernier spot
        workshop.takeASpot(date1);
        
        // Assert - Vérifie qu'il reste 0 spots
        assertThat(workshop.getSpotsPerDate().get(date1)).isEqualTo(0);
    }

    // Test pour le point 2: Map avec au moins une entrée, même avec 0 spots
    @Test
    @DisplayName("Workshop avec une seule date ayant 0 spots - doit logger une erreur")
    void testWorkshopWithSingleDateHavingZeroSpots() {
        // Arrange
        Student student = new Student(1, "Alice", "alice@example.com");
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 1, 15, 10, 0), 0); // Une date avec 0 spots
        Workshop workshop = new Workshop(1, "Java Workshop", spotsPerDate);
        
        AutoAssigner assigner = new AutoAssigner();
        
        // Act
        AssignmentsLogger result = assigner.assign(List.of(student), List.of(workshop));
        
        // Assert
        assertThat(result.getAssignments()).isEmpty();
        assertThat(result.getErrors()).containsExactly("Java Workshop,Alice");
    }
    
    @Test
    @DisplayName("Workshop avec une seule date ayant 1 spot - doit assigner l'étudiant")
    void testWorkshopWithSingleDateHavingOneSpot() {
        // Arrange
        Student student = new Student(1, "Bob", "bob@example.com");
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 1, 15, 10, 0), 1); // Une date avec 1 spot
        Workshop workshop = new Workshop(1, "Python Workshop", spotsPerDate);
        
        AutoAssigner assigner = new AutoAssigner();
        
        // Act
        AssignmentsLogger result = assigner.assign(List.of(student), List.of(workshop));
        
        // Assert
        assertThat(result.getErrors()).isEmpty();
        assertThat(result.getAssignments()).containsExactly("Python Workshop,Bob,15/01/2024 10:00");
    }
    
    @Test
    @DisplayName("Workshop avec une date à 0 spots et une autre avec des spots - doit utiliser la date disponible")
    void testWorkshopWithOneZeroSpotDateAndOneAvailableDate() {
        // Arrange
        Student student = new Student(1, "Charlie", "charlie@example.com");
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 1, 10, 10, 0), 0); // Première date: 0 spots
        spotsPerDate.put(date(2024, 1, 20, 10, 0), 2); // Deuxième date: 2 spots
        Workshop workshop = new Workshop(1, "C++ Workshop", spotsPerDate);
        
        AutoAssigner assigner = new AutoAssigner();
        
        // Act
        AssignmentsLogger result = assigner.assign(List.of(student), List.of(workshop));
        
        // Assert
        assertThat(result.getErrors()).isEmpty();
        assertThat(result.getAssignments()).containsExactly("C++ Workshop,Charlie,20/01/2024 10:00");
    }
    
    @Test
    @DisplayName("Plusieurs étudiants et workshop avec exactement le bon nombre de spots")
    void testMultipleStudentsWithExactNumberOfSpots() {
        // Arrange
        Student student1 = new Student(1, "David", "david@example.com");
        Student student2 = new Student(2, "Emma", "emma@example.com");
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 2, 1, 14, 0), 2); // Exactement 2 spots pour 2 étudiants
        Workshop workshop = new Workshop(1, "Web Workshop", spotsPerDate);
        
        AutoAssigner assigner = new AutoAssigner();
        
        // Act
        AssignmentsLogger result = assigner.assign(List.of(student1, student2), List.of(workshop));
        
        // Assert
        assertThat(result.getErrors()).isEmpty();
        assertThat(result.getAssignments()).containsExactlyInAnyOrder(
            "Web Workshop,David,01/02/2024 14:00",
            "Web Workshop,Emma,01/02/2024 14:00"
        );
    }
    
    @Test
    @DisplayName("Plusieurs étudiants mais pas assez de spots - certains doivent être en erreur")
    void testMoreStudentsThanAvailableSpots() {
        // Arrange
        Student student1 = new Student(1, "Frank", "frank@example.com");
        Student student2 = new Student(2, "Grace", "grace@example.com");
        Student student3 = new Student(3, "Henry", "henry@example.com");
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 3, 10, 9, 0), 2); // Seulement 2 spots pour 3 étudiants
        Workshop workshop = new Workshop(1, "Database Workshop", spotsPerDate);
        
        AutoAssigner assigner = new AutoAssigner();
        
        // Act
        AssignmentsLogger result = assigner.assign(List.of(student1, student2, student3), List.of(workshop));
        
        // Assert
        assertThat(result.getAssignments()).hasSize(2);
        assertThat(result.getErrors()).hasSize(1);
        assertThat(result.getErrors().get(0)).isEqualTo("Database Workshop,Henry");
    }

    @Test
    @DisplayName("Test des fonctions de création et d'accesseurs des workshops")
    void testWorkshopCreationAndAccessors() {
        // Arrange
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        ZonedDateTime date1 = date(2024, 4, 5, 11, 0);
        spotsPerDate.put(date1, 3);
        Workshop workshop = new Workshop(1, "AI Workshop", spotsPerDate);

        // Act
        int id = workshop.getId();
        String name = workshop.getName();
        Map<ZonedDateTime, Integer> retrievedSpotsPerDate = workshop.getSpotsPerDate();

        // Assert
        assertThat(id).isEqualTo(1);
        assertThat(name).isEqualTo("AI Workshop");
        assertThat(retrievedSpotsPerDate).isEqualTo(spotsPerDate);
    }

    @Test
    @DisplayName("workshop.equals et hashCode fonctionnent correctement")
    void testWorkshopEqualsAndHashCode() {
        // Arrange
        Map<ZonedDateTime, Integer> spotsPerDate1 = new HashMap<>();
        spotsPerDate1.put(date(2024, 5, 1, 10, 0), 5);
        Map<ZonedDateTime, Integer> spotsPerDate2 = new HashMap<>();
        spotsPerDate2.put(date(2024, 5, 1, 10, 0), 5);
        Map<ZonedDateTime, Integer> spotsPerDate3 = new HashMap<>();
        spotsPerDate3.put(date(2024, 6, 1, 10, 0), 3);
        Workshop workshop1 = new Workshop(1, "ML Workshop", spotsPerDate1);
        Workshop workshop2 = new Workshop(1, "ML Workshop", spotsPerDate2);
        Workshop workshop3 = new Workshop(2, "DL Workshop", spotsPerDate3); 

        // Act & Assert
        assertThat(workshop1).isEqualTo(workshop2);
        assertThat(workshop1).isNotEqualTo(workshop3);
        assertThat(workshop1.hashCode()).isEqualTo(workshop2.hashCode());
        assertThat(workshop1.hashCode()).isNotEqualTo(workshop3.hashCode());
    }

    @Test
    @DisplayName("workshop.equals if (this == o)")
    void testWorkshopEqualsSelfCheck() {
        // Arrange
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 5, 1, 10, 0), 5);
        Workshop workshop = new Workshop(1, "ML Workshop", spotsPerDate);

        // Act & Assert
        assertThat(workshop).isEqualTo(workshop);
    
    }

    @Test
    @DisplayName("workshop.equals if (o == null)")
    void testWorkshopEqualsNullCheck() {
        // Arrange
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 5, 1, 10, 0), 5);
        Workshop workshop = new Workshop(1, "ML Workshop", spotsPerDate);

        // Act & Assert
        assertThat(workshop).isNotEqualTo(null);
    }
    @Test
    @DisplayName("workshop.equals if (getClass() != o.getClass())")
    void testWorkshopEqualsTypeCheck() {
        // Arrange
        Map<ZonedDateTime, Integer> spotsPerDate = new HashMap<>();
        spotsPerDate.put(date(2024, 5, 1, 10, 0), 5);
        Workshop workshop = new Workshop(1, "ML Workshop", spotsPerDate);
        String notAWorkshop = "Not a workshop";
        // Act & Assert
        assertThat(workshop).isNotEqualTo(notAWorkshop);
    }

    // @Test
    // @DisplayName("Test des fonctions de création et d'accesseurs des étudiants")
    // void testStudentCreationAndAccessors() {
    //     // Arrange
    //     Student student = new Student(1, "Isabella", "isabella@example.com");

    //     // Act
    //     int id = student.getId();
    //     String name = student.getName();
    //     String email = student.getEmail();

    //     // Assert
    //     assertThat(id).isEqualTo(1);
    //     assertThat(name).isEqualTo("Isabella");
    //     assertThat(email).isEqualTo("isabella@example.com");
    // }

    // @Test
    // @DisplayName("student.equals et hashCode fonctionnent correctement")
    // void testStudentEqualsAndHashCode() {
    //     // Arrange
    //     Student student1 = new Student(1, "John", "john@example.com");
    //     Student student2 = new Student(1, "John", "john@example.com");
    //     Student student3 = new Student(2, "Jane", "jane@example.com");

    //     // Act & Assert
    //     assertThat(student1).isEqualTo(student2);
    //     assertThat(student1).isNotEqualTo(student3);
    //     assertThat(student1.hashCode()).isEqualTo(student2.hashCode());
    //     assertThat(student1.hashCode()).isNotEqualTo(student3.hashCode());
    // }

    // @Test
    // @DisplayName("student.equals if (this == o)")
    // void testStudentEqualsSelfCheck() {
    //     // Arrange
    //     Student student = new Student(1, "John", "john@example.com");

    //     // Act & Assert
    //     assertThat(student).isEqualTo(student);
    // }

    // @Test
    // @DisplayName("student.equals if (o == null)")
    // void testStudentEqualsNullCheck() {
    //     // Arrange
    //     Student student = new Student(1, "John", "john@example.com");

    //     // Act & Assert
    //     assertThat(student).isNotEqualTo(null);
    // }

    // @Test
    // @DisplayName("student.equals if (getClass() != o.getClass())")
    // void testStudentEqualsTypeCheck() {
    //     // Arrange
    //     Student student = new Student(1, "John", "john@example.com");
    //     String notAStudent = "Not a student";
        
    //     // Act & Assert
    //     assertThat(student).isNotEqualTo(notAStudent);
    // }
}