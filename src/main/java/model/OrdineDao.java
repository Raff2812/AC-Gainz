package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class OrdineDao {
    public Ordine doRetrieveById(int id)
    {
        try(Connection con= ConPool.getConnection())
        {
            PreparedStatement preparedStatement=con.prepareStatement("SELECT id_ordine,data,stato,totale,email_utente FROM ordine WHERE id_ordine=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                Ordine o=new Ordine(resultSet.getInt(1),resultSet.getDate(2),resultSet.getString(3),resultSet.getFloat(4),resultSet.getString(5));
                return o;
            }
            return null;

        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }
    }

    public void doSave(Ordine ordine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ordine (id_ordine,data,stato,totale,email_utente) VALUES(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ordine.getIdOrdine());
            ps.setDate(2, ordine.getDataOrdine());
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
