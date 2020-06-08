package Helpers;

import StateClasses.Dbinfo;

import javax.xml.transform.Result;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class PasswordGenerator {


    public static byte[] generateSalt() throws NoSuchProviderException, NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static String generateSaltedPassword(String plainPassword) throws NoSuchProviderException, NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        return generateSaltedPassword(plainPassword, salt);

    }

    public static String generateSaltedPassword(String plainPassword, byte[] salt) throws NoSuchProviderException, NoSuchAlgorithmException {
        {

            String generatedPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(salt);
                byte[] bytes = md.digest(plainPassword.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();

            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }
            return generatedPassword + "." + Base64.getEncoder().encodeToString(salt);
        }

    }

    public static boolean checkPassword(String email, String plainPassword) throws Exception {
        Connection conn = Dbinfo.startConnection();
        String query = "Select password from Staff where email=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet result = stmt.executeQuery();
        String hashedPassword;
        if (result.next()) {
            hashedPassword = result.getString("password");
        } else throw new Exception("User not found");
        String[] arrPassword = hashedPassword.split(".");
        String actualPassword = arrPassword[0];
        byte[] salt = Base64.getDecoder().decode(arrPassword[1].getBytes());
        String generatedPassword[] = generateSaltedPassword(plainPassword, salt).split(".");
        conn.close();
        return generatedPassword[0].equals(hashedPassword);
    }

}




