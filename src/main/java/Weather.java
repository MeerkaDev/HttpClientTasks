import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Weather {
    public static void main(String[] args) throws IOException, InterruptedException {
        URI uri = URI.create("https://api.openweathermap.org/data/2.5/weather?q=Astana&units=metric&lang=ru&appid=79d1ca96933b0328e1c7e3e7a26cb347");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject main = jsonObject.get("main").getAsJsonObject();

        double temp = main.get("temp").getAsDouble();
        System.out.println("Температура воздуха: " +  temp + " ˚C");

        // распечатать скорость ветра
        double windSpeed = jsonObject.get("wind").getAsJsonObject().get("speed").getAsDouble();
        System.out.println("Сокрость ветра: " +  windSpeed + " м/с");
    }
}
