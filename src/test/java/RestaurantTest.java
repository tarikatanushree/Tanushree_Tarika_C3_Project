import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.ArrayList;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class RestaurantTest {
    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setUp() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        LocalTime newCurrentTime = LocalTime.parse("21:30:00");
        Restaurant spyRestaurant = spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(newCurrentTime);
        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        LocalTime newCurrentTime = LocalTime.parse("23:30:00");
        Restaurant spyRestaurant = spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(newCurrentTime);
        assertFalse(spyRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {


        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {


        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER COST>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void calculateTotalCost_should_return_0_if_no_item_is_add() throws itemNotFoundException {
        restaurant.removeFromMenu("Sweet corn soup");
        restaurant.removeFromMenu("Vegetable lasagne");
        assertTrue(restaurant.calculateTotalItemCost(restaurant.getMenu())==0);
    }
    @Test
    public void calculateTotalCost_should_return_price_of_the_item_if_only_1_item_is_added() throws itemNotFoundException {
        restaurant.removeFromMenu("Vegetable lasagne");
        assertTrue(restaurant.calculateTotalItemCost(restaurant.getMenu())==((restaurant.getMenu().get(0).getPrice())));
    }
    @Test
    public void calculateTotalCost_should_return_sum_of_price_of_each_item_added() throws itemNotFoundException {
    int actualPrice=0;
    List<Item> menu = restaurant.getMenu();
     for(Item item : menu) {
         actualPrice = actualPrice + item.getPrice();
     }
        assertEquals(restaurant.calculateTotalItemCost(restaurant.getMenu()), actualPrice);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER COST>>>>>>>>>>>>>>>>>>>>>>>
}