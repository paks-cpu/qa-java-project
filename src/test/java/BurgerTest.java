import praktikum.Ingredient;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.IngredientType;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BurgerTest {

    private Burger burger;
    private Bun bunMock;
    private Ingredient ingredient1Mock;
    private Ingredient ingredient2Mock;

@Before
    public void setUp(){
    bunMock = mock(Bun.class);
    when(bunMock.getPrice()).thenReturn(20.0f);
    when(bunMock.getName()).thenReturn("nameBurger");

    ingredient1Mock = mock(Ingredient.class);
    when(ingredient1Mock.getType()).thenReturn(IngredientType.SAUCE);
    when(ingredient1Mock.getName()).thenReturn("CheeseSauce");
    when(ingredient1Mock.getPrice()).thenReturn(15.0f);

    ingredient2Mock = mock(Ingredient.class);
    when(ingredient2Mock.getType()).thenReturn(IngredientType.FILLING);
    when(ingredient2Mock.getName()).thenReturn("MeatFilling");
    when(ingredient2Mock.getPrice()).thenReturn(16.0f);

    burger = new Burger();
}

    @Test
    public void setBunsTest(){
    burger.setBuns(bunMock);
    assertEquals("Неправильная булочка", bunMock, burger.bun);
    }

    @Test
    public void addIngredientTest(){
    burger.addIngredient(ingredient1Mock);
    assertEquals("Ингридиет не добавлен к бургеру", List.of(ingredient1Mock), burger.ingredients);
    }

    @Test
    public void removeIngredientTest(){
    burger.addIngredient(ingredient1Mock);
    assertEquals("Ингридиент не добавлен к бургеру", List.of(ingredient1Mock), burger.ingredients);

    burger.removeIngredient(0);
    assertTrue("Список ингридиентов должен быть пустым после удаления", burger.ingredients.isEmpty());
    assertFalse("Ингридиент не долен содержаться в списке после удаления", burger.ingredients.contains(ingredient1Mock));
    }

    @Test
    public void removeIngredient2Test(){
    burger.addIngredient(ingredient1Mock);
    burger.addIngredient(ingredient2Mock);

    List<Ingredient> expectedBoth = List.of(ingredient1Mock, ingredient2Mock);
    assertEquals("Неверный список ингридиентов", expectedBoth, burger.ingredients);

    burger.removeIngredient(1);
    List<Ingredient> afterFirstRemove = List.of(ingredient1Mock);
    assertEquals("После удаления второго ингридиента должен остаться только первый", afterFirstRemove, burger.ingredients);

    assertTrue("Первый ингридиент должен остаться в списке", burger.ingredients.contains(ingredient1Mock));
    assertFalse("Второй ингридиент должен быть удалён", burger.ingredients.contains(ingredient2Mock));

    burger.removeIngredient(0);
    assertTrue("Список должен быть пуст", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientsTest(){
    burger.addIngredient(ingredient1Mock);
    burger.addIngredient(ingredient2Mock);

    burger.moveIngredient(0, 1);

    List<Ingredient> expectedOrder = List.of(ingredient2Mock, ingredient1Mock);
    assertEquals("Неверный порядок ингридиентов", expectedOrder, burger.ingredients);
    }

    @Test
    public void getPriceTest(){
    float expectedPrice = 20.0f * 2 + 15.0f + 16.0f;

    burger.setBuns(bunMock);
    burger.addIngredient(ingredient1Mock);
    burger.addIngredient(ingredient2Mock);

    assertEquals("Неверная цена бургеров", expectedPrice, burger.getPrice(), 0.01f);
    }

    @Test
    public void getReceiptTest(){
    burger.setBuns(bunMock);
    burger.addIngredient(ingredient1Mock);
    burger.addIngredient(ingredient2Mock);

    String receipt = burger.getReceipt();

    assertTrue("Чек должен содержать название булочки сверху", receipt.contains("==== nameBurger ===="));
    assertTrue("Чек должен содержать тип и название первого ингридиента", receipt.contains("sauce CheeseSauce"));
    assertTrue("Чек должен содержать тип название второго ингридиента", receipt.contains("filling MeatFilling"));
    assertTrue("Чек должен содержать цену", receipt.contains("Price:"));
    }
}
