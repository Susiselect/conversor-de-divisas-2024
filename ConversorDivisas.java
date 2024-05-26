import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConversorDivisas {

    private static final String API_KEY = "6ee67419050a2a3675b8979c"; // Mi API Key
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    convertirUSDToEUR(scanner);
                    break;
                case 2:
                    convertirEURToUSD(scanner);
                    break;
                case 3:
                    convertirJPYToGBP(scanner);
                    break;
                case 4:
                    convertirGBPToJPY(scanner);
                    break;
                case 5:
                    convertirCLPToMXN(scanner);
                    break;
                case 6:
                    convertirMXNToCLP(scanner);
                    break;
                case 7:
                    System.out.println("Gracias por utilizar nuestro Conversor de Divisas, ¡fin del proceso!");
                    System.exit(0);
                default:
                    System.out.println("Opción no disponible. Inténtalo otra vez");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n**************************************************\n");
        System.out.println("Bienvenido(a) al Conversor de Divisas");
        System.out.println("\n**************************************************\n");
        System.out.println("1. Convertir USD a EUR");
        System.out.println("2. Convertir EUR a USD");
        System.out.println("3. Convertir JPY a GBP");
        System.out.println("4. Convertir GBP a JPY");
        System.out.println("5. Convertir CLP a MXN");
        System.out.println("6. Convertir MXN a CLP");
        System.out.println("7. Salir");
        System.out.println("\n**************************************************\n");
        System.out.print("Elige la divisa que quieres convertir, del 1 al 7: ");
    }

    private static void convertirUSDToEUR(Scanner scanner) throws IOException {
        System.out.print("Ingresa el monto a convertir (USD): ");
        double montoUSD = scanner.nextDouble();

        String url = BASE_URL + "USD";
        JsonObject jsonobj = makeRequest(url);

        double tipoCambioEUR = jsonobj.getAsJsonObject("conversion_rates").get("EUR").getAsDouble();
        double montoEUR = montoUSD * tipoCambioEUR;

        System.out.println("El monto es " + montoEUR + " EUR");
    }

    private static void convertirEURToUSD(Scanner scanner) throws IOException {
        System.out.print("Ingresa el monto a convertir (EUR): ");
        double montoEUR = scanner.nextDouble();

        String url = BASE_URL + "EUR";
        JsonObject jsonobj = makeRequest(url);

        double tipoCambioUSD = jsonobj.getAsJsonObject("conversion_rates").get("USD").getAsDouble();
        double montoUSD = montoEUR * tipoCambioUSD;

        System.out.println("El monto es " + montoUSD + " USD");
    }

    // Implementar métodos similares para convertirJPYToGBP, convertirGBPToJPY, convertirCLPToMXN y convertirMXNToCLP
    private static void convertirJPYToGBP(Scanner scanner) throws IOException {
        System.out.print("Ingresa el monto a convertir (JPY): ");
        double montoJPY = scanner.nextDouble();

        String url = BASE_URL + "JPY";
        JsonObject jsonobj = makeRequest(url);

        double tipoCambioGBP = jsonobj.getAsJsonObject("conversion_rates").get("GBP").getAsDouble();
        double montoGBP = montoJPY * tipoCambioGBP;

        System.out.println("El monto es " + montoGBP + " GBP");
    }

    private static void convertirGBPToJPY(Scanner scanner) throws IOException {
        System.out.print("Ingresa el monto a convertir (GBP): ");
        double montoGBP = scanner.nextDouble();

        String url = BASE_URL + "GBP";
        JsonObject jsonobj = makeRequest(url);

        double tipoCambioJPY = jsonobj.getAsJsonObject("conversion_rates").get("JPY").getAsDouble();
        double montoJPY = montoGBP * tipoCambioJPY;

        System.out.println("El monto es " + montoJPY + " JPY");
    }

    private static void convertirCLPToMXN(Scanner scanner) throws IOException {
        System.out.print("Ingresa el monto a convertir (CLP): ");
        double montoCLP = scanner.nextDouble();

        String url = BASE_URL + "CLP";
        JsonObject jsonobj = makeRequest(url);

        double tipoCambioMXN = jsonobj.getAsJsonObject("conversion_rates").get("MXN").getAsDouble();
        double montoMXN = montoCLP * tipoCambioMXN;

        System.out.println("El monto es " + montoMXN + " MXN");
    }

    private static void convertirMXNToCLP(Scanner scanner) throws IOException {
        System.out.print("Ingresa el monto a convertir (MXN): ");
        double montoMXN = scanner.nextDouble();

        String url = BASE_URL + "MXN";
        JsonObject jsonobj = makeRequest(url);

        double tipoCambioCLP = jsonobj.getAsJsonObject("conversion_rates").get("CLP").getAsDouble();
        double montoCLP = montoMXN * tipoCambioCLP;

        System.out.println("El monto es " + montoCLP + " CLP");
    }

    private static JsonObject makeRequest(String url_str) throws IOException {
        // Setting URL
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        // Accessing object
        String req_result = jsonobj.get("result").getAsString();
        if (!req_result.equals("success")) {
            throw new IOException("Error: API request unsuccessful");
        }

        return jsonobj;
    }
}
