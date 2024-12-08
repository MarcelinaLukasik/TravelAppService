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

    public static void main(String args[]) throws IOException, ClassNotFoundException {

        serverSocket = new ServerSocket(port);

        while(true) {
            System.out.println("Waiting for the client request");


            Socket socket = serverSocket.accept();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message);



            if (message.equalsIgnoreCase("fetchOffers")) {

                List<Offer> offers = connectToDatabaseAndQuery();

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(offers);
                oos.flush();

                ois.close();
                oos.close();
                socket.close();
            }
            else if (message.equalsIgnoreCase("bookOffer")) {

                int offerId = ois.readInt();
                String username = ois.readUTF();

                updateReservationInDatabase(offerId, username);


//                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                oos.writeObject("Offer updated successfully");
                //oos.flush();
                ois.close();
                //oos.close();
                socket.close();
            }

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
                        endDate,
                        rs.getString("IMAGEURL"),
                        rs.getString("INSURANCE")
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

    private static void updateReservationInDatabase(int offerId, String username)
    {

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);


            Statement stmt = conn.createStatement();


            int isPaid = 0;

            String query = "INSERT INTO reservations (offerid, username, ispaid) " +
                    "VALUES (" + offerId + ", '" + username + "', " + isPaid + ")";

            stmt.executeUpdate(query);

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
    }
}
