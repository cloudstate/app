package app;

import static io.undertow.Handlers.routing;
import static io.undertow.Undertow.builder;
import static io.undertow.util.Headers.CONTENT_TYPE;
import static java.util.Arrays.asList;

import com.google.gson.Gson;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import app.domain.Product;

import io.undertow.server.HttpServerExchange;

public final class Main {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private static final String APP_JSON = "application/json";

    private static final Gson GSON = new Gson();

    private static final List<Product> products = asList(new Product("1", "Whizz"), new Product("2", "Bang"));
    private static final String json = GSON.toJson(products);

    private static final LongAdder adder = new LongAdder();

    public static void main(final String[] args) {
        System.out.println("Starting Undertow");
        System.out.println(HOST + ":" + PORT);
        System.out.println("    GET /alive");
        System.out.println("    GET /products");

        builder().addHttpListener(PORT, HOST)
                .setHandler(routing()
                        .add("GET", "/products", Main::productsHandler)
                        .add("GET", "/alive", Main::aliveHandler))
                .build()
                .start();
    }

    private static void productsHandler(final HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(CONTENT_TYPE, APP_JSON);
        exchange.getResponseSender().send(json);

        adder.increment();
    }

    private static void aliveHandler(final HttpServerExchange exchange) throws Exception {
        exchange.getResponseSender().send(Long.toString(adder.sum()));
        adder.reset();
    }

}