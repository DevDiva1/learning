package stock;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {
    @Test
    public void givenStockData_whenAddedToMap_thenMapHasLatestStockPrice() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<String> stockData = Arrays.asList(
                "0231351065 433.72 2021-01-02",
                "0231351065 500.72 2021-01-01",

                "0378331006 100.43 2021-02-01",
                "0378331006 101.43 2021-01-01",
                "0378331006 102.43 2021-03-01",

                "9497461011 10.00 2021-07-01",
                "9497461011 11.00 2021-06-01",

                "30303M1027 33.00 2021-03-01",
                "30303M1027 31.00 2021-02-01");

        ConcurrentHashMap<String, CurrentPrice> map = new ConcurrentHashMap<>();

        for (String stockInfo : stockData) {
            String[] stock = stockInfo.split(" ");
            PricingService.addStockPrice(new Stock(stock[0], new CurrentPrice(Double.parseDouble(stock[1]),
                            LocalDate.parse(stock[2], formatter))), map);

        }
        assertEquals(433.72, map.get("0231351065").price, "Latest stock price");
        assertEquals(102.43, map.get("0378331006").price,"Latest stock price");
        assertEquals(10.00, map.get("9497461011").price,"Latest stock price");
        assertEquals(33.00, map.get("30303M1027").price,"Latest stock price");
    }
}