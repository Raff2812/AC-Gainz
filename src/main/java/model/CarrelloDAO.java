package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class CarrelloDAO {


    public void doSave(Carrello c){
        try (Connection con = ConPool.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement("INSERT into carrello (utente, prodotto, quantity, total_price) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, c.getEmailUtente());
            preparedStatement.setString(2, c.getIdProdotto());
            preparedStatement.setInt(3, c.getQuantita());
            preparedStatement.setFloat(4, c.getPrezzo());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public List<Carrello> doRetrieveCartItemsByUser(String emailUtente) {
        List<Carrello> carrelli = new ArrayList<>();

        try (Connection connection = ConPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM carrello WHERE utente = ?");
            preparedStatement.setString(1, emailUtente);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Carrello carrello = new Carrello();
                carrello.setEmailUtente(resultSet.getString("utente"));
                carrello.setIdProdotto(resultSet.getString("prodotto"));
                carrello.setQuantita(resultSet.getInt("quantity"));
                carrello.setPrezzo(resultSet.getFloat("total_price"));

                carrelli.add(carrello);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carrelli;
    }


    public void removeProductFromCart(int idCarrello, String emailUtente, Prodotto prodotto){
        try (Connection connection = ConPool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from carrello where utente = ? and prodotto = ?");
            preparedStatement.setString(1, emailUtente);
            preparedStatement.setString(2, prodotto.getIdProdotto());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }




    public void addProductToCart(String emailUtente, Prodotto prodotto) {
        try (Connection con = ConPool.getConnection()) {

            String query = "INSERT INTO carrello (utente, prodotto, quantity, total_price) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "quantity = quantity + ?, total_price = total_price + ?";

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, emailUtente);
                preparedStatement.setString(2, prodotto.getIdProdotto());
                preparedStatement.setInt(3, 1);  // Aggiungi 1 unità del prodotto
                preparedStatement.setFloat(4, prodotto.getPrezzo());  // Prezzo totale iniziale per il nuovo prodotto
               preparedStatement.setInt(5, 1);  // Quantità da aggiungere in caso di duplicato
                preparedStatement.setFloat(6, prodotto.getPrezzo());  // Prezzo totale da aggiungere in caso di duplicato

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Prodotto aggiunto al carrello con successo per l'utente " + emailUtente);
                } else {
                    System.out.println("Il prodotto non è stato aggiunto al carrello per l'utente " + emailUtente);
                }
            }
        } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            System.err.println("Errore durante l'aggiunta del prodotto al carrello per l'utente " + emailUtente);
            e.printStackTrace();
            // Puoi lanciare una eccezione specifica o gestire diversamente l'errore a seconda delle necessità
        }
    }

    public void removeItem(String emailProdotto, String idProdotto){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from carrello where utente = ? and prodotto = ?");
            preparedStatement.setString(1, emailProdotto);
            preparedStatement.setString(2, idProdotto);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Rimosso dal carrello il prodotto: " + idProdotto);
            }else {
                System.out.println("Non è stato rimosso il prodotto: " + idProdotto + " dal carrello");
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


}
