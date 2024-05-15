package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class DettaglioOrdineDAO {
    public DettaglioOrdine doRetrieveById(int id)
    {
        try(Connection con= ConPool.getConnection())
        {
            PreparedStatement preparedStatement=con.prepareStatement("SELECT id_dettaglio_ordine,id_ordine,id_prodotto,quantita,prezzo FROM dettaglio_ordine WHERE id_dettaglio_ordine=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                DettaglioOrdine o=new DettaglioOrdine(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getFloat(5));
                return o;
            }
            return null;

        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }
    }

    public void doSave(DettaglioOrdine dettaglioOrdine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO dettaglio_ordine (id_dettaglio_ordine,id_ordine,id_prodotto,quantita,prezzo) VALUES(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dettaglioOrdine.getIdDettaglioOrdine());
            ps.setInt(2, dettaglioOrdine.getIdOrdine());
            ps.setInt(3, dettaglioOrdine.getIdProdotto());
            ps.setInt(4, dettaglioOrdine.getQuantita());
            ps.setFloat(5, dettaglioOrdine.getPrezzo());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DettaglioOrdine> doRetrieveAll(){

        ArrayList<DettaglioOrdine> dettagliordini = new ArrayList<>();

        Statement st;

        ResultSet rs;

        DettaglioOrdine o;

        try (Connection con = ConPool.getConnection()) {

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM dettaglio_ordine");

            while(rs.next()) {

                o = new DettaglioOrdine();
                o.setIdDettaglioOrdine(rs.getInt(1));
                o.setIdOrdine(rs.getInt(2));
                o.setIdProdotto(rs.getInt(3));
                o.setQuantita(rs.getInt(4));
                o.setPrezzo(rs.getFloat(5));

                dettagliordini.add(o);
            }

            con.close();

            return dettagliordini;
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
