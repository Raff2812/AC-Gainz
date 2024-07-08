package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GustoDAO {

    public Gusto doRetrieveById(int id){
        Gusto gusto = new Gusto();
        try (Connection connection = ConPool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from gusto where id_gusto = ?");
            preparedStatement.setInt(1, id );
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                gusto.setIdGusto(resultSet.getInt("id_gusto"));
                gusto.setNome(resultSet.getString("nome"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }


        return gusto;
    }


    public Gusto doRetrieveByIdVariante(int id) {
        Gusto gusto = null;
        try (Connection connection = ConPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT gusto.id_gusto, gusto.nome FROM gusto JOIN variante ON gusto.id_gusto = variante.id_gusto WHERE variante.id_variante = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                gusto = new Gusto();
                gusto.setIdGusto(resultSet.getInt("id_gusto"));
                gusto.setNome(resultSet.getString("nome"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gusto;
    }

}
