package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProdottoDAO {

    public Prodotto doRetrieveById(String id)
    {
        try(Connection con= ConPool.getConnection())
        {
            PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM prodotto WHERE prodotto.id_prodotto = ?");
            preparedStatement.setString(1,id);

            ResultSet resultSet=preparedStatement.executeQuery();
            List<Variante> varianti = new ArrayList<>();
            if(resultSet.next())
            {

                Prodotto p = new Prodotto();

                p.setIdProdotto(resultSet.getString("id_prodotto"));
                p.setNome(resultSet.getString("nome"));
                p.setDescrizione(resultSet.getString("descrizione"));
                p.setCategoria(resultSet.getString("categoria"));
                p.setImmagine(resultSet.getString("immagine"));
                p.setCalorie(resultSet.getInt("calorie"));
                p.setCarboidrati(resultSet.getInt("carboidrati"));
                p.setProteine(resultSet.getInt("proteine"));
                p.setGrassi(resultSet.getInt("grassi"));

                VarianteDAO varianteDAO = new VarianteDAO();
                varianti = varianteDAO.doRetrieveVariantiByIdProdotto(p.getIdProdotto());

                p.setVarianti(varianti);

                return p;
            }

            return null;

        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }
    }



    public List<Prodotto> filterProducts(String category, String sortingFilter, String weightFilter, String tasteFilter, String nameFilter) throws SQLException {
        List<Prodotto> filteredProducts = new ArrayList<>();
        VarianteDAO varianteDAO = new VarianteDAO();
        boolean filterOnEvidence;

        try (Connection connection = ConPool.getConnection()) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT p.* FROM prodotto p ");

            // Gestione delle condizioni di filtraggio
            List<String> conditions = new ArrayList<>();
            List<Object> params = new ArrayList<>();

            if (category != null && !category.equals("tutto") && !category.isBlank()) {
                conditions.add("p.categoria = ?");
                params.add(category);
            }

            if (nameFilter != null && !nameFilter.isBlank()) {
                conditions.add("p.nome LIKE ?");
                params.add("%" + nameFilter + "%");
            }

            if (!conditions.isEmpty()) {
                sql.append("WHERE ");
                sql.append(String.join(" AND ", conditions));
            }

            System.out.println(sql.toString());

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            // Imposta i parametri per il PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Prodotto p = extractProductFromResultSet(resultSet);
                filteredProducts.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (sortingFilter != null){
            filterOnEvidence = sortingFilter.equals("evidence");
        } else {
            filterOnEvidence = false;
        }

        // Fetch and set the cheapest variant for the products
        filteredProducts.removeIf(prodotto -> {
            Variante cheapestVariante = null;
            try {
                cheapestVariante = varianteDAO.doRetrieveCheapestFilteredVarianteByIdProdotto(
                        prodotto.getIdProdotto(), weightFilter, tasteFilter, filterOnEvidence);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (cheapestVariante != null) {
                List<Variante> varianti = new ArrayList<>();
                varianti.add(cheapestVariante);
                prodotto.setVarianti(varianti);
                return false; // Keep the product
            }
            return true; // Remove the product
        });

        // Handle sorting
        if (sortingFilter != null && !sortingFilter.isBlank()) {
            filteredProducts.sort(new Comparator<Prodotto>() {
                public int compare(Prodotto p1, Prodotto p2) {
                    return switch (sortingFilter) {
                        case "PriceDesc" -> Float.compare(getLowestPrice(p2), getLowestPrice(p1));
                        case "PriceAsc" -> Float.compare(getLowestPrice(p1), getLowestPrice(p2));
                        case "CaloriesDesc" -> Integer.compare(p2.getCalorie(), p1.getCalorie());
                        case "CaloriesAsc" -> Integer.compare(p1.getCalorie(), p2.getCalorie());
                        default -> 0;
                    };
                }
            });
        }

        return filteredProducts;
    }

    private float getLowestPrice(Prodotto prodotto) {
        List<Variante> varianti = prodotto.getVarianti();
        if (varianti == null || varianti.isEmpty()) {
            return Float.MAX_VALUE;
        }
        Variante variante = varianti.get(0);
        return variante.getPrezzo() * (1 - variante.getSconto() / 100.0f);
    }

 /*   private boolean hasEvidentVariant(Prodotto prodotto) {
        List<Variante> varianti = prodotto.getVarianti();
        if (varianti == null || varianti.isEmpty()) {
            return false;
        }
        return varianti.get(0).isEvidenza();
    }
*/
    private Prodotto extractProductFromResultSet(ResultSet resultSet) throws SQLException {
        Prodotto p = new Prodotto();
        p.setIdProdotto(resultSet.getString("id_prodotto"));
        p.setNome(resultSet.getString("nome"));
        p.setCategoria(resultSet.getString("categoria"));
        p.setCalorie(resultSet.getInt("calorie"));
        p.setImmagine(resultSet.getString("immagine"));
        return p;
    }




/*
    public List<Prodotto> filterProducts(String category, String sortingFilter, String weightFilter, String tasteFilter, String nameFilter) throws SQLException {
        List<Prodotto> filteredProducts = new ArrayList<>();

        try (Connection connection = ConPool.getConnection()) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT p.*, v.*, g.nomeGusto, c.peso FROM prodotto p ");
            sql.append("JOIN variante v ON p.id_prodotto = v.id_prodotto_variante ");
            sql.append("LEFT JOIN gusto g ON g.id_gusto = v.id_gusto ");
            sql.append("LEFT JOIN confezione c ON v.id_confezione = c.id_confezione ");

            // Gestione delle condizioni di filtraggio
            List<String> conditions = new ArrayList<>();
            List<Object> params = new ArrayList<>();

            if (weightFilter != null && !weightFilter.isBlank()) {
                String valueWeight = weightFilter.split(" ")[0];
                conditions.add("c.peso = ?");
                params.add(Integer.parseInt(valueWeight));
            }

            if (tasteFilter != null && !tasteFilter.isBlank()) {
                String valueTaste = tasteFilter.split(" \\(")[0];
                conditions.add("g.nomeGusto = ?");
                params.add(valueTaste);
            }

            if (nameFilter != null && !nameFilter.isBlank()) {
                conditions.add("p.nome LIKE ?");
                params.add("%" + nameFilter + "%");
            }

            if (category != null && !category.equals("tutto") && !category.isBlank()) {
                conditions.add("p.categoria = ?");
                params.add(category);
            }

            if (!conditions.isEmpty()) {
                sql.append("WHERE ");
                sql.append(String.join(" AND ", conditions));
            }

            // Gestione dell'ordinamento
            if (sortingFilter != null && !sortingFilter.isBlank()) {
                switch (sortingFilter) {
                    case "PriceDesc":
                        sql.append(" ORDER BY (v.prezzo * (1 - v.sconto / 100)) DESC");
                        break;
                    case "PriceAsc":
                        sql.append(" ORDER BY (v.prezzo * (1 - v.sconto / 100)) ASC");
                        break;
                    case "CaloriesDesc":
                        sql.append(" ORDER BY p.calorie DESC");
                        break;
                    case "CaloriesAsc":
                        sql.append(" ORDER BY p.calorie ASC");
                        break;
                    case "evidence":
                        if (conditions.isEmpty()) {
                            sql.append("WHERE v.evidenza = 1");
                        } else {
                            sql.append(" AND v.evidenza = 1");
                        }
                        break;
                }
            }

            System.out.println(sql.toString());

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            // Imposta i parametri per il PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Prodotto p = extractProductFromResultSet(resultSet);


                int valueWeight = -1;
                VarianteDAO varianteDAO = new VarianteDAO();
                List<Variante> filteredVarianti = new ArrayList<>();

                if (weightFilter != null && !weightFilter.isBlank()) {
                    System.out.println("strunz");
                    valueWeight = Integer.parseInt(weightFilter.split(" ")[0]);
                }
                // Filtra la variante in base ai criteri
                Variante v = extractVarianteFromResultSet(resultSet);

                if (filterVariante(v, tasteFilter, valueWeight)) {
                    filteredVarianti.add(v);
                }

                p.setVarianti(filteredVarianti);
                filteredProducts.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filteredProducts;
    }

    private Variante extractVarianteFromResultSet(ResultSet resultSet) throws SQLException {
        Variante v = new Variante();
        v.setIdVariante(resultSet.getInt("id_variante"));
        v.setIdProdotto(resultSet.getString("id_prodotto_variante"));
        v.setPrezzo(resultSet.getFloat("prezzo"));
        v.setSconto(resultSet.getInt("sconto"));
        v.setIdGusto(resultSet.getInt("id_gusto"));
        v.setIdConfezione(resultSet.getInt("id_confezione"));


        if (hasColumn(resultSet, "nomeGusto")) {
            v.setGusto(resultSet.getString("nomeGusto"));
        }

        if (hasColumn(resultSet, "peso")) {
            v.setPesoConfezione(resultSet.getInt("peso"));
        }

        v.setEvidenza(resultSet.getBoolean("evidenza"));

        return v;
    }

    private boolean hasColumn(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean filterVariante(Variante variante, String tasteFilter, int peso) {
        // Utilizza il metodo fornito per filtrare le varianti
        if (peso >= 0) {
            if (variante.getPesoConfezione() != peso) {
                return false;
            }
        }

        if (tasteFilter != null && !tasteFilter.isBlank()) {
            String valueFilter = tasteFilter.split(" \\(")[0];
            if (!variante.getGusto().equals(valueFilter)) {
                return false;
            }
        }
        return true;
    }



    // Metodo per estrarre un oggetto Prodotto da un ResultSet
  private Prodotto extractProductFromResultSet(ResultSet resultSet) throws SQLException {
        Prodotto p = new Prodotto();
        p.setIdProdotto(resultSet.getString("id_prodotto"));
        p.setNome(resultSet.getString("nome"));
        p.setCategoria(resultSet.getString("categoria"));
        p.setCalorie(resultSet.getInt("calorie"));
        p.setImmagine(resultSet.getString("immagine"));

        return p;
    }





    public List<Prodotto> doRetrieveByName(String nameProduct) {
        try (Connection connection = ConPool.getConnection()) {
            List<Prodotto> productsFilteredByName = new ArrayList<>();

            String query = "SELECT * FROM prodotto WHERE nome LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            String filter = "%" + nameProduct + "%";
            preparedStatement.setString(1, filter);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Prodotto p = new Prodotto();
                p.setIdProdotto(resultSet.getString("id_prodotto"));
                p.setNome(resultSet.getString("nome"));
                p.setDescrizione(resultSet.getString("descrizione"));
                p.setCategoria(resultSet.getString("categoria"));
                p.setImmagine(resultSet.getString("immagine"));
                p.setCalorie(resultSet.getInt("calorie"));
                p.setCarboidrati(resultSet.getInt("carboidrati"));
                p.setProteine(resultSet.getInt("proteine"));
                p.setGrassi(resultSet.getInt("grassi"));

                VarianteDAO varianteDAO = new VarianteDAO();
                List<Variante> varianti = new ArrayList<>();
                Variante variante = varianteDAO.doRetrieveCheapestVariant(p.getIdProdotto());
                varianti.add(variante);

                p.setVarianti(varianti);

                productsFilteredByName.add(p);
            }

            return productsFilteredByName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


*/




    public void doSave(Prodotto prodotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto (id_prodotto, nome, descrizione, categoria, immagine, calorie, carboidrati, proteine, grassi) VALUES(?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,prodotto.getIdProdotto());
            ps.setString(2, prodotto.getNome());
            ps.setString(3, prodotto.getDescrizione());
            ps.setString(4, prodotto.getCategoria());
            ps.setString(5, prodotto.getImmagine());
            ps.setInt(6, prodotto.getCalorie());
            ps.setInt(7, prodotto.getCarboidrati());
            ps.setInt(8, prodotto.getProteine());
            ps.setInt(9, prodotto.getGrassi());


            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Prodotto> doRetrieveByCriteria(String attribute, String value){
        if(Objects.equals(value, "Tutto"))
            doRetrieveAll();


        ArrayList<Prodotto> prodotti = new ArrayList<>();

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Prodotto p;

        try (Connection connection = ConPool.getConnection()){

            preparedStatement = connection.prepareStatement("SELECT * FROM prodotto where " + attribute +" = ?");
            preparedStatement.setString(1, value);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                p = new Prodotto();
                p.setIdProdotto(resultSet.getString("id_prodotto"));
                p.setNome(resultSet.getString("nome"));
                p.setDescrizione(resultSet.getString("descrizione"));
                p.setCategoria(resultSet.getString("categoria"));
                p.setImmagine(resultSet.getString("immagine"));
                p.setCalorie(resultSet.getInt("calorie"));
                p.setCarboidrati(resultSet.getInt("carboidrati"));
                p.setProteine(resultSet.getInt("proteine"));
                p.setGrassi(resultSet.getInt("grassi"));


                /*
                VarianteDAO varianteDAO = new VarianteDAO();
                List<Variante> varianti = varianteDAO.doRetrieveVariantiByIdProdotto(p.getIdProdotto());

                p.setVarianti(varianti);
                 */
                VarianteDAO varianteDAO = new VarianteDAO();
                Variante cheapest = varianteDAO.doRetrieveCheapestVariant(p.getIdProdotto());
                List<Variante> varianti = new ArrayList<>();
                varianti.add(cheapest);

                p.setVarianti(varianti);

                prodotti.add(p);

            }
            connection.close();
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public List<Prodotto> doRetrieveAll() {

        ArrayList<Prodotto> prodotti = new ArrayList<>();
        Statement st;
        ResultSet resultSet;
        Prodotto p;

        try (Connection con = ConPool.getConnection()) {

            st = con.createStatement();


            String query = "SELECT * FROM prodotto";

            resultSet = st.executeQuery(query);

            while(resultSet.next()) {
                p = new Prodotto();
                p.setIdProdotto(resultSet.getString("id_prodotto"));
                p.setNome(resultSet.getString("nome"));
                p.setDescrizione(resultSet.getString("descrizione"));
                p.setCategoria(resultSet.getString("categoria"));
                p.setImmagine(resultSet.getString("immagine"));
                p.setCalorie(resultSet.getInt("calorie"));
                p.setCarboidrati(resultSet.getInt("carboidrati"));
                p.setProteine(resultSet.getInt("proteine"));
                p.setGrassi(resultSet.getInt("grassi"));


                VarianteDAO varianteDAO = new VarianteDAO();
                List<Variante> varianti = new ArrayList<>();
                Variante variante = varianteDAO.doRetrieveCheapestVariant(p.getIdProdotto());
                varianti.add(variante);

                p.setVarianti(varianti);

                prodotti.add(p);
            }

            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public static void main (String[] args){
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> filteredOnTaste = new ArrayList<>();

        /*filteredOnTaste = prodottoDAO.filterOnTaste("creatina", "cioccolato");

        for(Prodotto p: filteredOnTaste)
            System.out.println(p.getIdProdotto() +  " " + p.getNome());*/

        Prodotto p = new Prodotto();
        p = prodottoDAO.doRetrieveById("B000");

        List<Variante> varianti = p.getVarianti();

        for (Variante variante: varianti)
            System.out.println(variante.getPrezzo() + " " + variante.getIdVariante());
    }
}
