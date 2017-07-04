package com.officenotes.gcm;


public class App {
	
	public static void main( String[] args )
    {
        System.out.println( "Sending POST to GCM" );
        //Plato
        //AIzaSyBX1zO3F072OmW2mdUU9rC2Sp4yACu3QWk
        //MRA
        //AIzaSyC3rnxJoCGEecgEbf5IT1d-5cmQGjvR6RA
        //Office Notes
        //AIzaSyCENcKRJWqHkFQgxa_oIlNUlEMIv5hezU0
        String apiKey = "AIzaSyCENcKRJWqHkFQgxa_oIlNUlEMIv5hezU0";
        Content content = createContent();

        Post2GCM.post(content);
    }

    public static Content createContent(){

        Content c = new Content();
        //c.addRegId("APA91bFg4AqsvD03jtKtn8UvNoxktCUtiMT1HSROeCZhCAijM3pfxhqvR-ES2FpwLULgS0zyyYjHeQXf6AkGl-t2x_wjg9S8wOKzWHZXQ3Wq2bgMJq7AFMQ");
        //sbalm001
        //samsung tablet
        //APA91bFEd5weWXdLWO5zdM-9tY33qY_KRePMSd1CZbbPQmcpk57JnM1cYgBSbS2LxfZYCespssy_zK11lV3TkFTgf6xf92IGT5ZShQs-L229B2TPvBZlRRM1jNaPwiP_OrVzuPdBbXlI
        //phone
        //APA91bHzl-Nrb18Xk-PLMPb8E554HknAf-9hJFJJSnaHf7xI1oN6ITM3sWUyHeJ667qg2LBJM7mPWn6L99o01xrjvse64JQp8K-6e_30DwBnwrdwx-lxkfolFvfmAMFrQFPM33AvQGFP
        c.addRegId("APA91bFEd5weWXdLWO5zdM-9tY33qY_KRePMSd1CZbbPQmcpk57JnM1cYgBSbS2LxfZYCespssy_zK11lV3TkFTgf6xf92IGT5ZShQs-L229B2TPvBZlRRM1jNaPwiP_OrVzuPdBbXlI");
        c.createData("Welcome, Plato User!!", "Test Message");

        return c;
    }

}
