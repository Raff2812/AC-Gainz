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
            String query = "INSERT INTO carrello (utente, prodotto, quantity, total_price) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, c.getEmailUtente());
            preparedStatement.setString(2, c.getIdProdotto());
            preparedStatement.setInt(3, c.getQuantita());
            preparedStatement.setFloat(4, c.getPrezzo());


            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void doRemoveCartByUser(String emailUtente){
        try (Connection connection = ConPool.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("Delete from carrello where utente = ?");
            preparedStatement.setString(1, emailUtente);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("Eliminate correttamente: " + rowsDeleted + "righe dal carrello di: " + emailUtente);
            else System.out.println("Nessuna riga eliminata");

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


}
