package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProdottoDAO {

    public Prodotto doRetrieveById(String id)
    {
        try(Connection con= ConPool.getConnection())
        {
            PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM prodotto WHERE id_prodotto=?");
            preparedStatement.setString(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return new Prodotto(resultSet.getString("id_prodotto"),resultSet.getString("nome")
                        ,resultSet.getString("descrizione"),resultSet.getFloat("prezzo"),
                        resultSet.getInt("quantità"),resultSet.getString("categoria"),
                        resultSet.getString("gusto"),resultSet.getInt("calorie"),
                        resultSet.getInt("grassi"),resultSet.getInt("proteine"),
                        resultSet.getInt("carboidrati"),resultSet.getInt("peso"),
                        resultSet.getString("immagine"),resultSet.getInt("sconto"),
                        resultSet.getBoolean("evidenza"));
            }
            return null;

        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }
    }



    public List<Prodotto> doRetrieveByName(String nameProduct) {
        try (Connection connection = ConPool.getConnection()) {
            List<Prodotto> productsFilteredByName = new ArrayList<>();

            // Query to retrieve products with distinct names
            String query = "SELECT * FROM prodotto WHERE nome LIKE ? AND id_prodotto IN " +
                    "(SELECT MIN(id_prodotto) FROM prodotto WHERE nome LIKE ? GROUP BY nome)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            String filter = "%" + nameProduct + "%";
            preparedStatement.setString(1, filter);
            preparedStatement.setString(2, filter);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setIdProdotto(rs.getString("id_prodotto"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setQuantita(rs.getInt("quantità"));
                p.setCategoria(rs.getString("categoria"));
                p.setGusto(rs.getString("gusto"));
                p.setCalorie(rs.getInt("calorie"));
                p.setGrassi(rs.getInt("grassi"));
                p.setCarboidrati(rs.getInt("carboidrati"));
                p.setProteine(rs.getInt("proteine"));
                p.setPeso(rs.getInt("peso"));
                p.setImmagine(rs.getString("immagine"));
                p.setSconto(rs.getInt("sconto"));
                p.setEvidenza(rs.getBoolean("evidenza"));

                productsFilteredByName.add(p);
            }

            return productsFilteredByName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void doSave(Prodotto prodotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto (id_prodotto,nome,descrizione,prezzo,quantità,categoria,Gusto,Calorie,Grassi,Carboidrati,Proteine,Peso,Immagine,Sconto, evidenza) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,prodotto.getIdProdotto());
            ps.setString(2, prodotto.getNome());
            ps.setString(3, prodotto.getDescrizione());
            ps.setFloat(4, prodotto.getPrezzo());
            ps.setInt(5, prodotto.getQuantita());
            ps.setString(6, prodotto.getCategoria());
            ps.setString(7, prodotto.getGusto());
            ps.setInt(8, prodotto.getCalorie());
            ps.setInt(9, prodotto.getGrassi());
            ps.setInt(10, prodotto.getCarboidrati());
            ps.setInt(11, prodotto.getProteine());
            ps.setInt(12, prodotto.getPeso());
            ps.setString(13, prodotto.getImmagine());
            ps.setInt(14, prodotto.getSconto());
            ps.setBoolean(15, prodotto.isEvidenza());


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
        ResultSet rs;

        Prodotto p;

        try (Connection connection = ConPool.getConnection()){

            preparedStatement = connection.prepareStatement("SELECT * FROM prodotto where " + attribute +" = ?");
            preparedStatement.setString(1, value);
            rs = preparedStatement.executeQuery();

            while (rs.next()){
                p = new Prodotto();
                p.setIdProdotto(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getFloat(4));
                p.setQuantita(rs.getInt(5));
                p.setCategoria(rs.getString(6));
                p.setGusto(rs.getString(7));
                p.setCalorie(rs.getInt(8));
                p.setGrassi(rs.getInt(9));
                p.setCarboidrati(rs.getInt(10));
                p.setProteine(rs.getInt(11));
                p.setPeso(rs.getInt(12));
                p.setImmagine(rs.getString(13));
                p.setSconto(rs.getInt(14));
                p.setEvidenza(rs.getBoolean(15));

                prodotti.add(p);

            }
                    connection.close();
                    return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> doRetrieveByCriteriaRange(String attribute, int from, int to){
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        PreparedStatement preparedStatement;
        ResultSet rs;

        Prodotto p;

        try (Connection connection = ConPool.getConnection()){

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM prodotto where " +
                            attribute + " >= ? " + " and " + attribute + " <= ?");
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, to);

            rs = preparedStatement.executeQuery();

            while (rs.next()){
                p = new Prodotto();
                p.setIdProdotto(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getFloat(4));
                p.setQuantita(rs.getInt(5));
                p.setCategoria(rs.getString(6));
                p.setGusto(rs.getString(7));
                p.setCalorie(rs.getInt(8));
                p.setGrassi(rs.getInt(9));
                p.setCarboidrati(rs.getInt(10));
                p.setProteine(rs.getInt(11));
                p.setPeso(rs.getInt(12));
                p.setImmagine(rs.getString(13));
                p.setSconto(rs.getInt(14));
                p.setEvidenza(rs.getBoolean(15));

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
        ResultSet rs;
        Prodotto p;

        try (Connection con = ConPool.getConnection()) {

            st = con.createStatement();

            // Query to retrieve products with distinct names
            String query = "SELECT * FROM prodotto WHERE id_prodotto IN " +
                    "(SELECT MIN(id_prodotto) FROM prodotto GROUP BY nome)";

            rs = st.executeQuery(query);

            while(rs.next()) {
                p = new Prodotto();
                p.setIdProdotto(rs.getString("id_prodotto"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setPrezzo(rs.getFloat("prezzo"));
                p.setQuantita(rs.getInt("quantità"));
                p.setCategoria(rs.getString("categoria"));
                p.setGusto(rs.getString("gusto"));
                p.setCalorie(rs.getInt("calorie"));
                p.setGrassi(rs.getInt("grassi"));
                p.setCarboidrati(rs.getInt("carboidrati"));
                p.setProteine(rs.getInt("proteine"));
                p.setPeso(rs.getInt("peso"));
                p.setImmagine(rs.getString("immagine"));
                p.setSconto(rs.getInt("sconto"));
                p.setEvidenza(rs.getBoolean("evidenza"));

                prodotti.add(p);
            }

            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Prodotto> doRetrieveSimilarProducts(String name){
        List<Prodotto> similarProducts = new ArrayList<>();
        try (Connection connection = ConPool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from prodotto where nome = ?");

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                similarProducts.add(new Prodotto(resultSet.getString("id_prodotto"), resultSet.getString("nome"),
                                        resultSet.getString("descrizione"), resultSet.getFloat("prezzo"),
                                        resultSet.getInt("quantità"), resultSet.getString("categoria"),
                                        resultSet.getString("gusto"), resultSet.getInt("calorie"),
                                        resultSet.getInt("grassi"), resultSet.getInt("proteine"), resultSet.getInt("carboidrati"),
                                        resultSet.getInt("peso"), resultSet.getString("immagine"), resultSet.getInt("sconto"), resultSet.getBoolean("evidenza")));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return similarProducts;
    }


    public void doUpdateProduct (Prodotto u){

        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            String query = "update prodotto set id_prodotto='" + u.getIdProdotto() +
                    "', nome='" + u.getNome() +
                    "', descrizione='" + u.getDescrizione() +
                    "', prezzo='"+ u.getPrezzo() +
                    "', quantità ='"+ u.getQuantita() +
                    "', categoria='"+ u.getCategoria() +
                    "', Gusto='"+ u.getGusto() +
                    "', Calorie='"+ u.getCalorie() +
                    "', Grassi='"+ u.getGrassi() +
                    "', Carboidrati='"+ u.getCarboidrati() +
                    "', Proteine='"+ u.getProteine() +
                    "', Peso='"+ u.getPeso() +
                    "', Immagine='"+ u.getImmagine() +
                    "', Sconto="+ u.getSconto()
                    + " where id_prodotto=" + u.getIdProdotto() + ";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
