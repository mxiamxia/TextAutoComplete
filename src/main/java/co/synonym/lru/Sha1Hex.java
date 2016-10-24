package co.synonym.lru;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Hex {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		System.out.println(makeSHA1Hash("I have no dial tone in my house!"));
	}

    public static String makeSHA1Hash(String input)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
        {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;
        }
}
