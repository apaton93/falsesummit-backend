package com.company.falsesummit.routes;

import static spark.Spark.*;
import com.company.falsesummit.db.Database;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;

public class DateRoutes {
    public static void Dates() {
        get("/dates", (req, res) -> {
            ArrayList<Integer> years = new ArrayList<>();

            String sql = """
                SELECT DISTINCT year(effective_date) AS year FROM effective_dates;
                """;

            try (
                    Connection c = Database.get();
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(sql)) {

                while (rs.next()) {
                    years.add(rs.getInt("year"));
                }
            } catch (Exception e) {
                e.printStackTrace(); // This shows errors in your terminal
                res.status(500);
                return "Database error: " + e.getMessage();
            }

            res.type("application/json");
            return new Gson().toJson(years);
        });
    }

}
