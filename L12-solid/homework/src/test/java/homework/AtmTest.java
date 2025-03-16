package homework;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.atm.Atm;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.atm.Currency.EUR;
import static ru.atm.Currency.RUR;
import static ru.atm.Nominal.FIFTY;
import static ru.atm.Nominal.ONE_HUNDRED;

public class AtmTest {
    private static Atm atm;

    @AfterAll
    public static void shutDown(){
        atm = null;
    }

    @Test
    @DisplayName("Проверить пустую валютную ячейку")
    public void checkMoneyInEmptyCellTest(){
        atm = new Atm(List.of(RUR));
        assertEquals(0,atm.getAccountBalance(RUR));
    }

    @Test
    @DisplayName("Внесение средств на счет в одной валюте")
    public void addMoneyForOneCurToAtmTest()  {
        atm = new Atm(RUR);
        atm.processAddition(RUR,FIFTY,4);
        atm.processAddition(RUR,ONE_HUNDRED,2);

        assertEquals(400,atm.getAccountBalance(RUR));
    }

    @Test
    @DisplayName("Внесение средств на счет в двух валютах")
    public void addMoneyForMultipleCurToAtmTest() {
        atm = new Atm(List.of(RUR,EUR));
        atm.processAddition(RUR,FIFTY,4);
        atm.processAddition(RUR,ONE_HUNDRED,2);
        atm.processAddition(EUR,ONE_HUNDRED,2);

        assertEquals(400,atm.getAccountBalance(RUR));
        assertEquals(200,atm.getAccountBalance(EUR));
    }

    @Test
    @DisplayName("Внесение средств в несуществующую валютную ячейку")
    public void addMoneyForUnavalibleCurToAtmTest(){
        atm = new Atm(List.of(RUR));
        assertThrows(Exception.class,() ->
                atm.processAddition(EUR,ONE_HUNDRED,2),
                "Ячейки для данной валюты не существует");
    }

    @Test
    @DisplayName("Валидация выдачи из валютной ячейку")
    public void checkWithrawIsValidToAtmTest() {
        atm = new Atm(List.of(EUR));
        atm.processAddition(EUR,ONE_HUNDRED,1);
        assertEquals(100,atm.getAccountBalance(EUR));
        assertThrows(Exception.class,() ->
                atm.processWithdraw(EUR,1),
                "Нет необходимых купюр для выдачи");

    }

    @Test
    @DisplayName("Внесение средств в два банкомата ")
    public void insertMoneyInTwoAtmsTest() {
        atm = new Atm(RUR);
        Atm atm2 = new Atm(RUR);
        atm.processAddition(RUR,ONE_HUNDRED,2);
        atm2.processAddition(RUR,ONE_HUNDRED,1);
        assertEquals(200,atm.getAccountBalance(RUR));
        assertEquals(100,atm2.getAccountBalance(RUR));
    }
}
