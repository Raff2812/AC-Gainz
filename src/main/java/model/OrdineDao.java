package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdineDao {
    public Ordine doRetrieveById(int id) {
        Ordine ordine = new Ordine();
        try(Connection con= ConPool.getConnection())
        {
            PreparedStatement preparedStatement=con.prepareStatement("SELECT * FROM ordine WHERE id_ordine=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                ordine.setIdOrdine(resultSet.getInt("id_ordine"));
                ordine.setEmailUtente(resultSet.getString("email_utente"));
                ordine.setDataOrdine(resultSet.getDate("data"));
                ordine.setStato(resultSet.getString("stato"));
                ordine.setTotale(resultSet.getFloat("totale"));
            }

        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }

        return ordine;
    }


    public List<Ordine> doRetrieveByEmail(String email){
        List<Ordine> ordini = new ArrayList<>();
        try (Connection connection = ConPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from ordine where email_utente = ?");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Ordine ordine = new Ordine();
                ordine.setIdOrdine(resultSet.getInt("id_ordine"));
                ordine.setEmailUtente(resultSet.getString("email_utente"));
                ordine.setDataOrdine(resultSet.getDate("data"));
                ordine.setStato(resultSet.getString("stato"));
                ordine.setTotale(resultSet.getFloat("totale"));
                ordini.add(ordine);
            }



        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return ordini;
    }
    public int getLastInsertedId(){
        int id = 0;
        try (Connection connection = ConPool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                id = resultSet.getInt(1);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    public void doSave(Ordine ordine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ordine (id_ordine,data,stato,totale,email_utente) VALUES(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ordine.getIdOrdine());

            Date utilDate = ordine.getDataOrdine();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            ps.setDate(2, sqlDate);

            ps.setString(3, ordine.getStato());
            ps.setFloat(4, ordine.getTotale());
            ps.setString(5, ordine.getEmailUtente());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ordine> doRetrieveAll(){

        ArrayList<Ordine> ordini = new ArrayList<>();

        Statement st;

        ResultSet rs;

        Ordine o;

        try (Connection con = ConPool.getConnection()) {

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM ordine");

            while(rs.next()) {

                o = new Ordine();
                o.setIdOrdine(rs.getInt(1));


                o.setDataOrdine(rs.getDate(2));

                o.setStato(rs.getString(3));
                o.setTotale(rs.getFloat(4));
                o.setEmailUtente(rs.getString(5));

                ordini.add(o);
            }

            con.close();

            return ordini;
        }

        catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public void doUpdateOrder(Ordine o){

        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            String query = "update ordine set id_ordine='" + o.getIdOrdine() +
                    "', data='" + o.getDataOrdine() +
                    "', stato='" + o.getStato() +
                    "', totale='"+ o.getTotale() +
                    "', email_utente="+ o.getEmailUtente()
                    + " where id_ordine=" + o.getIdOrdine() + ";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
