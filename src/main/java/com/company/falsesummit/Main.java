package com.company.falsesummit;
import static spark.Spark.*;

import com.company.falsesummit.routes.WholesalerRoutes;
import com.company.falsesummit.routes.TariffRoutes;
import com.company.falsesummit.routes.DateRoutes;

public class Main {
    public static void main(String[] args) {
        port(8080);
        //Andrew

        //Sort CORS issue
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.type("application/json");
        });

        get("/ping", (req, res) -> "False Summit backend is active and running");

        //Wholesaler Routes
        WholesalerRoutes.Wholesalers();
        WholesalerRoutes.WholesalerInfo();

        //Tariff Routes
        TariffRoutes.Tariffs();
        TariffRoutes.TariffRates();

        //Date Routes
        DateRoutes.Dates();

    }
}
