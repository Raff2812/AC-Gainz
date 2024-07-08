package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfezioneDAO {

    public Confezione doRetrieveById(int id){
        Confezione confezione = new Confezione();
        try (Connection connection = ConPool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from confezione where id_confezione = ?");
            preparedStatement.setInt(1, id );
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                confezione.setIdConfezione(resultSet.getInt("id_confezione"));
                confezione.setPeso(resultSet.getInt("peso"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return confezione;
    }
}
