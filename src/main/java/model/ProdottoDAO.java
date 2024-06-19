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
                return new Prodotto(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4),resultSet.getInt(5),resultSet.getString(6),resultSet.getString(7),resultSet.getFloat(8),resultSet.getFloat(9),resultSet.getFloat(10),resultSet.getFloat(11),resultSet.getInt(12),resultSet.getString(13),resultSet.getInt(14));
            }
            return null;

        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }
    }

    public void doSave(Prodotto prodotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto (id_prodotto,nome,descrizione,prezzo,quantita,categoria,Gusto,Calorie,Grassi,Carboidrati,Proteine,Peso,Immagine,Sconto) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,prodotto.getIdProdotto());
            ps.setString(2, prodotto.getNome());
            ps.setString(3, prodotto.getDescrizione());
            ps.setFloat(4, prodotto.getPrezzo());
            ps.setInt(5, prodotto.getQuantita());
            ps.setString(6, prodotto.getCategoria());
            ps.setString(7, prodotto.getGusto());
            ps.setFloat(8, prodotto.getCalorie());
            ps.setFloat(9, prodotto.getGrassi());
            ps.setFloat(10, prodotto.getCarboidrati());
            ps.setFloat(11, prodotto.getProteine());
            ps.setInt(12, prodotto.getPeso());
            ps.setString(13, prodotto.getImmagine());
            ps.setInt(14, prodotto.getSconto());
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
                p.setCalorie(rs.getFloat(8));
                p.setGrassi(rs.getFloat(9));
                p.setCarboidrati(rs.getFloat(10));
                p.setProteine(rs.getFloat(11));
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
                p.setCalorie(rs.getFloat(8));
                p.setGrassi(rs.getFloat(9));
                p.setCarboidrati(rs.getFloat(10));
                p.setProteine(rs.getFloat(11));
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




    public List<Prodotto> doRetrieveAll(){

        ArrayList<Prodotto> prodotti = new ArrayList<>();

        Statement st;

        ResultSet rs;

        Prodotto p;

        try (Connection con = ConPool.getConnection()) {

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM prodotto");

            while(rs.next()) {

                p = new Prodotto();
                p.setIdProdotto(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getFloat(4));
                p.setQuantita(rs.getInt(5));
                p.setCategoria(rs.getString(6));
                p.setGusto(rs.getString(7));
                p.setCalorie(rs.getFloat(8));
                p.setGrassi(rs.getFloat(9));
                p.setCarboidrati(rs.getFloat(10));
                p.setProteine(rs.getFloat(11));
                p.setPeso(rs.getInt(12));
                p.setImmagine(rs.getString(13));
                p.setSconto(rs.getInt(14));
                p.setEvidenza(rs.getBoolean(15));

                prodotti.add(p);
            }

            con.close();

            return prodotti;
        }

        catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public void doUpdateCustomer(Prodotto u){

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
