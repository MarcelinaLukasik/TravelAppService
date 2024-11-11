import com.example.travel_app_client.Offer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SocketServer {

    private static ServerSocket serverSocket;
    private static int port = 9876;

    private static String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/FREE";
    private static String dbUser = "C##myuser";
    private static String dbPassword = "mypassword";

    public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException {

        serverSocket = new ServerSocket(port);

        while(true) {
            System.out.println("Waiting for the client request");


            Socket socket = serverSocket.accept();

            // Create ObjectInputStream to read messages
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message);

            //Connect to the database and perform a SELECT * query
            List<Offer> offers = connectToDatabaseAndQuery();

            // Create ObjectOutputStream object to send the response to the client
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            // Send the list of offers back to the client
            oos.writeObject(offers);
            oos.flush();

            // Close resources
            ois.close();
            oos.close();
            socket.close();

            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");

        serverSocket.close();
    }

    private static List<Offer> connectToDatabaseAndQuery() {
        List<Offer> offers = new ArrayList<>();

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM OFFERS";
            ResultSet rs = stmt.executeQuery(query);

            if (!rs.next()) {
                System.out.println("No data returned from the database.");
            } else {
                System.out.println("Query executed successfully, fetching data...");
            }

            Date sqlStartDate = rs.getDate("STARTDATE");
            Date sqlEndDate = rs.getDate("ENDDATE");


            java.util.Date startDate = new java.util.Date(sqlStartDate.getTime());
            java.util.Date endDate = new java.util.Date(sqlEndDate.getTime());

            while (rs.next()) {
                Offer offer = new Offer(
                        rs.getInt("id"),
                        rs.getString("COUNTRY"),
                        rs.getString("CITY"),
                        rs.getDouble("PRICE"),
                        rs.getString("HOTEL_NAME"),
                        startDate,
                        endDate
                );
                offers.add(offer);
            }


            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return offers;
    }
}
