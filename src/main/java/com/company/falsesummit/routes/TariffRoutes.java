package com.company.falsesummit.routes;

import static spark.Spark.*;
import com.company.falsesummit.db.Database;
import com.company.falsesummit.model.Tariff;
import com.company.falsesummit.model.TariffRate;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;






public class TariffRoutes {
    public static void Tariffs() {
        get("/tariffs", (req, res) -> {
            ArrayList<Tariff> tariffs = new ArrayList<>();
            int wholesaler_id = Integer.parseInt(req.queryParams("wholesaler_id"));
            String component = req.queryParams("component");

            String sql = """
                SELECT
                    tariff_id,
                    tariff_code
                FROM tariffs
                WHERE (wholesaler_id = ?) AND (service_component_type = ?)
                """;

            try (
                Connection c = Database.get();
                PreparedStatement s = c.prepareStatement(sql)) {

                s.setInt(1, wholesaler_id);
                s.setString(2, component);
                ResultSet rs = s.executeQuery();

                while (rs.next()) {
                tariffs.add(new Tariff(
                        rs.getInt("tariff_id"),
                        rs.getString("tariff_code")
                ));
            }
            } catch (Exception e) {
                e.printStackTrace(); // This shows errors in your terminal
                res.status(500);
                return "Database error: " + e.getMessage();
            }

            res.type("application/json");
            return new Gson().toJson(tariffs);
        });
    }







    public static void TariffRates() {
        get("/rates", (req, res) -> {
            ArrayList<TariffRate> rates = new ArrayList<>();

            int tariff_id = Integer.parseInt(req.queryParams("tariff_id"));
            int contract_id = Integer.parseInt(req.queryParams("contract_id"));
            int year = Integer.parseInt(req.queryParams("year"));

            String sql = """
                        SELECT
                            t.tariff_name,
                            ct.element_name,
                            r.property_1_value,
                            r.property_2_value,
                            r.property_3_value
                        FROM charge_rates r
                        JOIN tariffs t ON r.fk_tariff_id = t.tariff_id
                        JOIN contracts c ON r.contract_id = c.id
                        JOIN effective_dates d ON r.effective_date_id = d.date_id
                        JOIN charge_type ct ON r.fk_charge_type_id = ct.charge_type_id
                        WHERE r.fk_tariff_id = ?1 AND c.id = ?2 AND year(d.effective_date) = ?3
                        ORDER BY d.effective_date ASC;
                        """;

            sql = sql.replace("?1", "" + tariff_id);
            sql = sql.replace("?2", "" + contract_id);
            sql = sql.replace("?3", "" + year);

            System.out.println(sql);

            try (
                    Connection c = Database.get();
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(sql)) {

                while (rs.next()) {
                    rates.add(new TariffRate(
                            rs.getString("tariff_name"),
                            rs.getString("element_name"),
                            rs.getDouble("property_1_value"),
                            rs.getDouble("property_2_value"),
                            rs.getDouble("property_3_value")
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace(); // This shows errors in your terminal
                res.status(500);
                return "Database error: " + e.getMessage();
            }

            res.type("application/json");
            return new Gson().toJson(rates);
        });
    }

}
