package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DettaglioOrdineDAO {
    public List<DettaglioOrdine> doRetrieveById(int id) {
        List<DettaglioOrdine> dettaglioOrdini = new ArrayList<>();
        try(Connection con= ConPool.getConnection())
        {
            PreparedStatement preparedStatement=con.prepareStatement("SELECT dettaglio_ordine.*, p.nome, p.immagine, g.nomeGusto, c.peso FROM dettaglio_ordine join prodotto p on dettaglio_ordine.id_prodotto = p.id_prodotto join variante v on dettaglio_ordine.id_variante = v.id_variante join confezione c on v.id_confezione = c.id_confezione join gusto g on v.id_gusto = g.id_gusto " +
                    "WHERE id_ordine = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
           while (resultSet.next()){
               DettaglioOrdine dettaglioOrdine = new DettaglioOrdine();
             dettaglioOrdine.setIdOrdine(resultSet.getInt("id_ordine"));
             dettaglioOrdine.setIdProdotto(resultSet.getString("id_prodotto"));
             dettaglioOrdine.setIdVariante(resultSet.getInt("id_variante"));
             dettaglioOrdine.setQuantita(resultSet.getInt("quantità"));
             dettaglioOrdine.setPrezzo(resultSet.getFloat("prezzo"));
             dettaglioOrdine.setGusto(resultSet.getString("nomeGusto"));
             dettaglioOrdine.setPesoConfezione(resultSet.getInt("peso"));
             dettaglioOrdine.setNomeProdotto(resultSet.getString("nome"));
             dettaglioOrdine.setImmagineProdotto(resultSet.getString("immagine"));

             dettaglioOrdini.add(dettaglioOrdine);
           }
        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }

        return dettaglioOrdini;
    }

    public void doSave(DettaglioOrdine dettaglioOrdine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO dettaglio_ordine (id_ordine, id_prodotto, id_variante, quantità, prezzo) VALUES(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dettaglioOrdine.getIdOrdine());
            ps.setString(2, dettaglioOrdine.getIdProdotto());
            ps.setInt(3, dettaglioOrdine.getIdVariante());
            ps.setInt(4, dettaglioOrdine.getQuantita());
            ps.setFloat(5, dettaglioOrdine.getPrezzo());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   /* public List<DettaglioOrdine> doRetrieveAll(){

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
    }*/
}
