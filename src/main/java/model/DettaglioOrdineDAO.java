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
            PreparedStatement preparedStatement=con.prepareStatement("SELECT id_ordine,id_prodotto,quantità,prezzo FROM dettaglio_ordine WHERE id_ordine=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return new DettaglioOrdine(resultSet.getInt("id_ordine"),resultSet.getString("id_prodotto"),
                        resultSet.getInt("quantità"),resultSet.getFloat("prezzo"));
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
                    "INSERT INTO dettaglio_ordine (id_ordine,id_prodotto,quantità,prezzo) VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dettaglioOrdine.getIdOrdine());
            ps.setString(2, dettaglioOrdine.getIdProdotto());
            ps.setInt(3, dettaglioOrdine.getQuantita());
            ps.setFloat(4, dettaglioOrdine.getPrezzo());
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
                o.setIdOrdine(rs.getInt("id_ordine"));
                o.setIdProdotto(rs.getString("id_prodotto"));
                o.setQuantita(rs.getInt("quantità"));
                o.setPrezzo(rs.getFloat("prezzo"));

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
            String query = "update dettaglio_ordine set id_ordine='" + o.getIdOrdine() +
                    "', id_prodotto='" + o.getIdProdotto() +
                    "', quantità='"+ o.getQuantita() +
                    "', prezzo="+ o.getPrezzo()
                    + " where id_ordine=" + o.getIdOrdine() + ";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
