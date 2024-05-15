package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class CarrelloDAO {
    public Carrello doRetrieveById(int id)
    {
        try(Connection con= ConPool.getConnection())
        {
            PreparedStatement preparedStatement=con.prepareStatement("SELECT id_carrello,email_utente,id_prodotto,quantita,prezzo FROM carrello WHERE id_carrello=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                Carrello o=new Carrello(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getFloat(5));
                return o;
            }
            return null;

        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }
    }

    public void doSave(Carrello carrello) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO carrello (id_carrello,email_utente,id_prodotto,quantita,prezzo) VALUES(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, carrello.getIdCarrello());
            ps.setString(2, carrello.getEmailUtente());
            ps.setInt(3, carrello.getIdProdotto());
            ps.setInt(4, carrello.getQuantita());
            ps.setFloat(5, carrello.getPrezzo());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Carrello> doRetrieveAll(){

        ArrayList<Carrello> carrelli = new ArrayList<>();

        Statement st;

        ResultSet rs;

        Carrello o;

        try (Connection con = ConPool.getConnection()) {

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM carrello");

            while(rs.next()) {

                o = new Carrello();
                o.setIdCarrello(rs.getInt(1));
                o.setEmailUtente(rs.getString(2));
                o.setIdProdotto(rs.getInt(3));
                o.setQuantita(rs.getInt(4));
                o.setPrezzo(rs.getFloat(5));

                carrelli.add(o);
            }

            con.close();

            return carrelli;
        }

        catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public void doUpdateCustomer(DettaglioOrdine o){

        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            String query = "update dettaglio_ordine set id_dettaglio_ordine='" + o.getIdDettaglioOrdine() +
                    "', id_ordine='" + o.getIdOrdine() +
                    "', id_prodotto='" + o.getIdProdotto() +
                    "', quantita='"+ o.getQuantita() +
                    "', prezzo="+ o.getPrezzo()
                    + " where id_dettaglio_ordine=" + o.getIdDettaglioOrdine() + ";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
