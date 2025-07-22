package com.company.falsesummit.routes;

import static spark.Spark.*;
import com.company.falsesummit.db.Database;
import com.company.falsesummit.model.Wholesaler;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;

public class WholesalerRoutes {
    public static void Wholesalers() {
        get("/wholesalers", (req, res) -> {
            ArrayList<Wholesaler> wholesalers = new ArrayList<>();

            try (
                Connection c = Database.get();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT wholesaler_id, wholesaler_name FROM wholesaler")) {

                while (rs.next()) {
                    wholesalers.add(new Wholesaler(
                            rs.getInt("wholesaler_id"),
                            rs.getString("wholesaler_name"),
                            null, null, null, null
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace(); // This shows errors in your terminal
                res.status(500);
                return "Database error: " + e.getMessage();
            }

            res.type("application/json");
            return new Gson().toJson(wholesalers);
        });
    }









    public static void WholesalerInfo() {
        get("/wholesaler-info", (req, res) -> {
            ArrayList<Wholesaler> wholesalers = new ArrayList<>();

            int wholesaler_id = Integer.parseInt(req.queryParams("wholesaler_id"));

            String sql = """
                        SELECT
                            *
                        FROM wholesaler
                        WHERE wholesaler_id = ?1;
                        """;

            sql = sql.replace("?1", "" + wholesaler_id);

            System.out.println(sql);

            try (
                    Connection c = Database.get();
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(sql)) {

                while (rs.next()) {
                    wholesalers.add(new Wholesaler(
                            rs.getInt("wholesaler_id"),
                            rs.getString("wholesaler_name"),
                            rs.getString("bundle"),
                            rs.getString("phone"),
                            rs.getString("phone_emergency"),
                            rs.getString("email")
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace(); // This shows errors in your terminal
                res.status(500);
                return "Database error: " + e.getMessage();
            }

            res.type("application/json");
            return new Gson().toJson(wholesalers);
        });
    }

}
