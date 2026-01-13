package delft;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static delft.Field.*;
import static delft.Property.*;
import static delft.SportsHallPlanner.planHalls;

public class SportsHallPlannerTest {

    @Test
    void validFieldTypeAndSufficientQuantityAndAllPropertiesTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT),
                Map.of(BADMINTON, 5, TENNIS, 3));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        assertThat(hall.canFulfillRequest(request)).isTrue();
    }

    @Test
    void emptyRequestsReturnsEmptyMapTest(){
        List<Request> requests = new ArrayList<>();
        List<SportsHall> halls = List.of(
                new SportsHall(Set.of(NEAR_CITY_CENTRE),
                        Map.of(BADMINTON, 5)),
                new SportsHall(Set.of(HAS_RESTAURANT),
                        Map.of(TENNIS, 3))
        );
        assertThat(planHalls(requests, halls)).isEqualTo(new HashMap<>());
    }

    @Test
    void multipleRequestsMultipleHallsPerfectMatchTest(){
        SportsHall hall1 = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        SportsHall hall2 = new SportsHall(Set.of(HAS_RESTAURANT),
                Map.of(TENNIS, 3));
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        Request request2 = new Request(Set.of(HAS_RESTAURANT), TENNIS, 2);
        List<Request> requests = List.of(request1, request2);
        List<SportsHall> halls = List.of(hall1, hall2);
        Map<SportsHall, Request> expected = Map.of(
                hall1, request1,
                hall2, request2
        );
        assertThat(planHalls(requests, halls)).isEqualTo(expected);
    }

    @Test
    void moreHallsThanRequestsTest(){
        SportsHall hall1 = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        SportsHall hall2 = new SportsHall(Set.of(HAS_RESTAURANT),
                Map.of(TENNIS, 3));
        SportsHall hall3 = new SportsHall(Set.of(CLOSE_PUBLIC_TRANSPORT),
                Map.of(VOLLEYBALL, 4));
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request1);
        List<SportsHall> halls = List.of(hall1, hall2, hall3);
        Map<SportsHall, Request> expected = Map.of(
                hall1, request1
        );
        assertThat(planHalls(requests, halls)).isEqualTo(expected);
    }

    @Test
    void firstHallChosenWhenMultipleSuitableTest(){
        SportsHall hall1 = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        SportsHall hall2 = new SportsHall(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT),
                Map.of(BADMINTON, 10));
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request1);
        List<SportsHall> halls = List.of(hall1, hall2);
        Map<SportsHall, Request> expected = Map.of(
                hall1, request1
        );
        assertThat(planHalls(requests, halls)).isEqualTo(expected);
    }

    @Test
    void allFieldTypesCanBeMatchedTest() {
        SportsHall hall1 = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        SportsHall hall2 = new SportsHall(Set.of(HAS_RESTAURANT),
                Map.of(TENNIS, 3));
        SportsHall hall3 = new SportsHall(Set.of(CLOSE_PUBLIC_TRANSPORT),
                Map.of(VOLLEYBALL, 4));
        SportsHall hall4 = new SportsHall(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT),
                Map.of(BASKETBALL, 2));
        Request badmintonRequest = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 3);
        Request tennisRequest = new Request(Set.of(HAS_RESTAURANT), TENNIS, 2);
        Request volleyballRequest = new Request(Set.of(CLOSE_PUBLIC_TRANSPORT), VOLLEYBALL, 4);
        Request basketballRequest = new Request(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT), BASKETBALL, 1);

        List<Request> requests = List.of(badmintonRequest, tennisRequest, volleyballRequest, basketballRequest);

        Map<SportsHall, Request> expected = Map.of(
            hall1, badmintonRequest,
            hall2, tennisRequest,
            hall3, volleyballRequest,
            hall4, basketballRequest
        );

        assertThat(planHalls(requests, List.of(hall1, hall2, hall3, hall4))).isEqualTo(expected);
    }

    @Test
    void multiplePropertiesSatisfiedTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT, CLOSE_PUBLIC_TRANSPORT),
                Map.of(BADMINTON, 5));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT), BADMINTON, 4);
        assertThat(hall.canFulfillRequest(request)).isTrue();
    }

    @Test
    void insufficientFieldsReturnsNullTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 3));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request);
        List<SportsHall> halls = List.of(hall);
        assertThat(planHalls(requests, halls)).isNull();
    }

    @Test
    void wrongFieldTypeReturnsNullTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(TENNIS, 5));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request);
        List<SportsHall> halls = List.of(hall);
        assertThat(planHalls(requests, halls)).isNull();
    }

    @Test
    void missingPropertiesReturnsNullTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT), BADMINTON, 4);
        List<Request> requests = List.of(request);
        List<SportsHall> halls = List.of(hall);
        assertThat(planHalls(requests, halls)).isNull();
    }

    @Test
    void noHallsAvailableReturnsNullTest(){
        Request request = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request);
        List<SportsHall> halls = new ArrayList<>();
        assertThat(planHalls(requests, halls)).isNull();
    }

    @Test
    void noSuitableHallForAnyRequestReturnsNullTest(){
        SportsHall hall = new SportsHall(Set.of(HAS_RESTAURANT),
                Map.of(TENNIS, 5));
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        Request request2 = new Request(Set.of(CLOSE_PUBLIC_TRANSPORT), VOLLEYBALL, 2);
        List<Request> requests = List.of(request1, request2);
        List<SportsHall> halls = List.of(hall);
        assertThat(planHalls(requests, halls)).isNull();
    }

    @Test
    void impossiblePlanningReturnsNullTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));   
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        Request request2 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request1, request2);
        List<SportsHall> halls = List.of(hall);
        assertThat(planHalls(requests, halls)).isNull();
    }

    @Test
    void exactNumberOfFieldsMatchesTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 4));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request);
        List<SportsHall> halls = List.of(hall);
        Map<SportsHall, Request> expected = Map.of(
                hall, request
        );
        assertThat(planHalls(requests, halls)).isEqualTo(expected);
    }

    @Test
    void firstRequestAssignedToFirstHallTest(){
        SportsHall hall1 = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        SportsHall hall2 = new SportsHall(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT),
                Map.of(BADMINTON, 5));
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        Request request2 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request1, request2);
        List<SportsHall> halls = List.of(hall1, hall2);
        Map<SportsHall, Request> expected = Map.of(
                hall1, request1,
                hall2, request2
        );
        assertThat(planHalls(requests, halls)).isEqualTo(expected);
    }

    @Test
    void duplicateHallsInListThrowsExceptionTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        List<Request> requests = List.of(request);
        List<SportsHall> halls = List.of(hall, hall);
        assertThatThrownBy(() -> planHalls(requests, halls))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void backtrackingRequiredSimpleTest(){
        SportsHall hall1 = new SportsHall(Set.of(NEAR_CITY_CENTRE, CLOSE_PUBLIC_TRANSPORT),
                Map.of(TENNIS, 5));
        SportsHall hall2 = new SportsHall(Set.of(HAS_RESTAURANT, CLOSE_PUBLIC_TRANSPORT),
                Map.of(TENNIS, 3));
        Request request1 = new Request(Set.of(CLOSE_PUBLIC_TRANSPORT), TENNIS, 3);
        Request request2 = new Request(Set.of(CLOSE_PUBLIC_TRANSPORT), TENNIS, 5);
        List<Request> requests = List.of(request1, request2);
        List<SportsHall> halls = List.of(hall1, hall2);
        Map<SportsHall, Request> expected = Map.of(
                hall2, request1,
                hall1, request2
        );
        assertThat(planHalls(requests, halls)).isEqualTo(expected);
    }

    @Test
    void duplicateHallsCheckCoveredTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE, HAS_RESTAURANT),
                Map.of(BADMINTON, 2));
        assertThatThrownBy(() -> planHalls(
                List.of(new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 1)),
                List.of(hall, hall))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void emptyRequestsEarlyReturnCoveredTest(){
        List<Request> requests = new ArrayList<>();
        List<SportsHall> halls = List.of(
                new SportsHall(Set.of(NEAR_CITY_CENTRE),
                        Map.of(BADMINTON, 5)),
                new SportsHall(Set.of(HAS_RESTAURANT),
                        Map.of(TENNIS, 3))
        );
        assertThat(planHalls(requests, halls)).isEqualTo(new HashMap<>());
    }

    @Test
    void canFulfillRequestFalseBranchTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 2));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 3);
        assertThat(hall.canFulfillRequest(request)).isFalse();
    }

    @Test
    void canFulfillRequestTrueBranchTest(){
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        Request request = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 3);
        assertThat(hall.canFulfillRequest(request)).isTrue();
    }

    @Test
    void recursionReturnsNullBranchTest() {
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 3));
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        Request request2 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 2);
        List<Request> requests = List.of(request1, request2);
        List<SportsHall> halls = List.of(hall);
        assertThat(planHalls(requests, halls)).isNull();
    }

    @Test
    void recursionReturnsValidBranchTest() {
        SportsHall hall1 = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 5));
        SportsHall hall2 = new SportsHall(Set.of(HAS_RESTAURANT),
                Map.of(TENNIS, 3));
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        Request request2 = new Request(Set.of(HAS_RESTAURANT), TENNIS, 2);
        List<Request> requests = List.of(request1, request2);
        List<SportsHall> halls = List.of(hall1, hall2);
        Map<SportsHall, Request> expected = Map.of(
                hall1, request1,
                hall2, request2
        );
        assertThat(planHalls(requests, halls)).isEqualTo(expected);
    }

    @Test
    void finalReturnNullCoveredTest() {
        SportsHall hall = new SportsHall(Set.of(NEAR_CITY_CENTRE),
                Map.of(BADMINTON, 3));
        Request request1 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 4);
        Request request2 = new Request(Set.of(NEAR_CITY_CENTRE), BADMINTON, 2);
        List<Request> requests = List.of(request1, request2);
        List<SportsHall> halls = List.of(hall);
        assertThat(planHalls(requests, halls)).isNull();
    }
}
