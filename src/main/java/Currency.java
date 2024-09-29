import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Currency {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите сумму в тг: ");
        double sumInKzt = Double.parseDouble(scanner.nextLine());

        System.out.print("Введите валюту (пример USD, EUR): ");
        String currencyName = scanner.nextLine();

        String finalUri = "https://api.apilayer.com/exchangerates_data/latest?base=" +
                        currencyName +
                        "&symbols=KZT&apikey=iISN69jOgAmSSuWq5GG68tko23CuqMLk";

        URI uri = URI.create(finalUri);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int stasusCode = response.statusCode();

        if (stasusCode != 200) {
            System.out.println("Валюта не найдена!");
        } else {
            JsonElement jsonElement = JsonParser.parseString(response.body());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            double kztRate = jsonObject.get("rates").getAsJsonObject().get("KZT").getAsDouble();

            double sumInCurrency = sumInKzt / kztRate;

            System.out.println(sumInKzt + " в " + currencyName + " составляет: " + sumInCurrency);
        }
    }
}
