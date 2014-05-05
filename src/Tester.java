import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by m on 31/03/14.
 */
public class Tester {

    final static int ok = 0;
    final static int error = 1;
    final static int authentication_failed = 2;
    final static int cannot_send_file = 3;
    final static int cannot_receive_file = 4;
    final static int cannot_connect = 5;
    final static int client_blocked = 6;
    final static int directory_listing = 7;
    final static int file_content = 8;
    final static int byte_array_content = 9;
    final static int string_content = 10;


    public static void main(String[] args) {
        for (int i = 0; i < 20; i++)
        {
            Process p = null;
            try {
                if (i == 15)
                    p = Runtime.getRuntime().exec("java StartServerBlocker");
                else
                    p = Runtime.getRuntime().exec("java StartServer");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("Error");
            }
            switch (i)
            {
                case 0:
                    try {
                        if (StartClient.TestConnect() == ok)
                            System.out.println("PASSED: TestConnect()");
                        else
                            System.out.println("Failed: TestConnect()");
                    } catch (NetException e) {
                        System.out.println(e.msg);
                        System.out.println("Exception: TestConnect()");
                    }
                    break;
                case 1:
                    try {
                        if (StartClient.TestSendPassword() == ok)
                            System.out.println("PASSED: TestSendPassword()");
                        else
                            System.out.println("Failed: TestSendPassword()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestSendPassword()");
                    }
                    break;
                case 2:
                    try {
                        if (StartClient.TestListDirectory() == directory_listing)
                            System.out.println("PASSED: TestListDirectory()");
                        else
                            System.out.println("Failed: TestListDirectory()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestListDirectory()");
                    }
                    break;
                case 3:
                    try {
                        if (StartClient.TestSendFile() == ok)
                            System.out.println("PASSED: TestSendFile()");
                        else
                            System.out.println("Failed: TestSendFile()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestSendFile()");
                    }
                    break;
                case 4:
                    try {
                        if (StartClient.TestReceiveFile() == file_content)
                            System.out.println("PASSED: TestReceiveFile()");
                        else
                            System.out.println("Failed: TestReceiveFile()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestReceiveFile()");
                    }
                    break;
                case 5:
                    try {
                        if (StartClient.TestEchoByteArray() == byte_array_content)
                            System.out.println("PASSED: TestEchoByteArray()");
                        else
                            System.out.println("Failed: TestEchoByteArray()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestEchoByteArray()");
                    }
                    break;
                case 6:
                    try {
                        if (StartClient.TestEchoString() == string_content)
                            System.out.println("PASSED: TestEchoString()");
                        else
                            System.out.println("Failed: TestEchoString()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestEchoString()");
                    }
                    break;

                case 7:
                    try {
                        if (StartClient.TestListDirectoryAuthenticationFalure() == authentication_failed)
                            System.out.println("PASSED: TestListDirectoryAuthenticationFalure()");
                        else
                            System.out.println("Failed: TestListDirectoryAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestListDirectoryAuthenticationFalure()");
                    }
                    break;
                case 8:
                    try {
                        if (StartClient.TestSendFileAuthenticationFalure() == authentication_failed)
                            System.out.println("PASSED: TestSendFileAuthenticationFalure()");
                        else
                            System.out.println("Failed: TestSendFileAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestSendFileAuthenticationFalure()");
                    }
                    break;
                case 9:
                    try {
                        if (StartClient.TestReceiveFileAuthenticationFalure() == authentication_failed)
                            System.out.println("PASSED: TestReceiveFileAuthenticationFalure()");
                        else
                            System.out.println("Failed: TestReceiveFileAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestReceiveFileAuthenticationFalure()");
                    }
                    break;
                case 10:
                    try {
                        if (StartClient.TestEchoByteArrayAuthenticationFalure() == authentication_failed)
                            System.out.println("PASSED: TestEchoByteArrayAuthenticationFalure()");
                        else
                            System.out.println("Failed: TestEchoByteArrayAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestEchoByteArrayAuthenticationFalure()");
                    }
                    break;
                case 11:
                    try {
                        if (StartClient.TestEchoStringAuthenticationFalure() == authentication_failed)
                            System.out.println("PASSED: TestEchoStringAuthenticationFalure()");
                        else
                            System.out.println("Failed: TestEchoStringAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestEchoStringAuthenticationFalure()");
                    }
                    break;
                case 12:
                    try {
                        if (StartClient.TestSendPasswordAuthenticationFalure() == authentication_failed)
                            System.out.println("PASSED: TestSendPasswordAuthenticationFalure()");
                        else
                            System.out.println("Failed: TestSendPasswordAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestSendPasswordAuthenticationFalure()");
                    }
                    break;
                case 13:
                    try {
                        if (StartClient.TestReceiveFileCannotReceiveFile() == cannot_receive_file)
                            System.out.println("PASSED: TestReceiveFileCannotReceiveFile()");
                        else
                            System.out.println("Failed: TestReceiveFileCannotReceiveFile()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestReceiveFileCannotReceiveFile()");
                    }
                    break;
                case 14:
                    try {
                        if (StartClient.TestSendFileCannotSendFile() == cannot_send_file)
                            System.out.println("PASSED: TestSentFileCannotSentFile()");
                        else
                            System.out.println("Failed: TestSentFileCannotSentFile()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestSentFileCannotSentFile()");
                    }
                    break;
                case 15:
                    try {
                        if (StartClient.TestConnectBlocked() == client_blocked)
                            System.out.println("PASSED: TestConnectBlocked()");
                        else
                            System.out.println("Failed: TestConnectBlocked()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestConnectBlocked()");
                    }
                    break;
                case 16:
                    try {
                        if (StartClient.TestServerExit() == ok)
                        {
                            Thread.sleep(500);
                            try{
                                p.exitValue();
                            } catch (IllegalThreadStateException e) {
                                System.out.println("Failed: TestServerExit() thread error");
                                break;
                            }
                            System.out.println("PASSED: TestServerExit()");
                        } else {
                            System.out.println("Failed: TestServerExit()");
                        }
                    } catch (NetException e) {
                        System.out.println("Exception: TestServerExit()");
                    } catch (InterruptedException e) {
                        System.out.println("TEST FAILED TO COMPLETE DUE TO OUTSIDE INFLUENCES, PLEASE RE RUN TEST SUITE.");
                    }
                    break;
                case 17:
                    try {
                        if (StartClient.TestServerExitAuthenticationFailure() == authentication_failed)
                        {
                            Thread.sleep(500);
                            try{
                                p.exitValue();
                            } catch (IllegalThreadStateException e) {
                                System.out.println("PASSED: TestServerExitAuthenticationFailure()");
                                break;
                            }
                            System.out.println("Failed: TestServerExitAuthenticationFailure()");
                        }
                        else
                            System.out.println("Failed: TestServerExitAuthenticationFailure()");
                    } catch (NetException e) {
                        System.out.println("Exception: TestServerExitAuthenticationFailure()");
                    } catch (InterruptedException e) {
                        System.out.println("TEST FAILED TO COMPLETE DUE TO OUTSIDE INFLUENCES, PLEASE RE RUN TEST SUITE.");
                    }
                    break;

                case 18:
                    System.out.println("TEST ALL ON ONE SERVER:");
                    try {
                        if (StartClient.TestConnect() == ok)
                            System.out.println("    PASSED: TestConnect()");
                        else
                            System.out.println("    Failed: TestConnect()");
                    } catch (NetException e) {
                        System.out.println(e.msg);
                        System.out.println("    Exception: TestConnect()");
                    }
                    try {
                        if (StartClient.TestSendPassword() == ok)
                            System.out.println("    PASSED: TestSendPassword()");
                        else
                            System.out.println("    Failed: TestSendPassword()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestSendPassword()");
                    }
                    try {
                        if (StartClient.TestListDirectory() == directory_listing)
                            System.out.println("    PASSED: TestListDirectory()");
                        else
                            System.out.println("    Failed: TestListDirectory()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestListDirectory()");
                    }
                    try {
                        if (StartClient.TestSendFile() == ok)
                            System.out.println("    PASSED: TestSendFile()");
                        else
                            System.out.println("    Failed: TestSendFile()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestSendFile()");
                    }
                    try {
                        if (StartClient.TestReceiveFile() == file_content)
                            System.out.println("    PASSED: TestReceiveFile()");
                        else
                            System.out.println("    Failed: TestReceiveFile()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestReceiveFile()");
                    }
                    try {
                        if (StartClient.TestEchoByteArray() == byte_array_content)
                            System.out.println("    PASSED: TestEchoByteArray()");
                        else
                            System.out.println("    Failed: TestEchoByteArray()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestEchoByteArray()");
                    }
                    try {
                        if (StartClient.TestEchoString() == string_content)
                            System.out.println("    PASSED: TestEchoString()");
                        else
                            System.out.println("    Failed: TestEchoString()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestEchoString()");
                    }
                    try {
                        if (StartClient.TestListDirectoryAuthenticationFalure() == authentication_failed)
                            System.out.println("    PASSED: TestListDirectoryAuthenticationFalure()");
                        else
                            System.out.println("    Failed: TestListDirectoryAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestListDirectoryAuthenticationFalure()");
                    }
                    try {
                        if (StartClient.TestSendFileAuthenticationFalure() == authentication_failed)
                            System.out.println("    PASSED: TestSendFileAuthenticationFalure()");
                        else
                            System.out.println("    Failed: TestSendFileAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestSendFileAuthenticationFalure()");
                    }
                    try {
                        if (StartClient.TestReceiveFileAuthenticationFalure() == authentication_failed)
                            System.out.println("    PASSED: TestReceiveFileAuthenticationFalure()");
                        else
                            System.out.println("    Failed: TestReceiveFileAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestReceiveFileAuthenticationFalure()");
                    }
                    try {
                        if (StartClient.TestEchoByteArrayAuthenticationFalure() == authentication_failed)
                            System.out.println("    PASSED: TestEchoByteArrayAuthenticationFalure()");
                        else
                            System.out.println("    Failed: TestEchoByteArrayAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestEchoByteArrayAuthenticationFalure()");
                    }
                    try {
                        if (StartClient.TestEchoStringAuthenticationFalure() == authentication_failed)
                            System.out.println("    PASSED: TestEchoStringAuthenticationFalure()");
                        else
                            System.out.println("    Failed: TestEchoStringAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestEchoStringAuthenticationFalure()");
                    }
                    try {
                        if (StartClient.TestSendPasswordAuthenticationFalure() == authentication_failed)
                            System.out.println("    PASSED: TestSendPasswordAuthenticationFalure()");
                        else
                            System.out.println("    Failed: TestSendPasswordAuthenticationFalure()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestSendPasswordAuthenticationFalure()");
                    }
                    try {
                        if (StartClient.TestReceiveFileCannotReceiveFile() == cannot_receive_file)
                            System.out.println("    PASSED: TestReceiveFileCannotReceiveFile()");
                        else
                            System.out.println("    Failed: TestReceiveFileCannotReceiveFile()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestReceiveFileCannotReceiveFile()");
                    }
                    try {
                        if (StartClient.TestSendFileCannotSendFile() == cannot_send_file)
                            System.out.println("    PASSED: TestSentFileCannotSentFile()");
                        else
                            System.out.println("    Failed: TestSentFileCannotSentFile()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestSentFileCannotSentFile()");
                    }
                    break;

                case 19:
                    System.out.println("TEST ALL ON ONE CLIENT:");
                    try {
                        if (StartClient.TestAll() == ok)
                            System.out.println("    PASSED: TestAll()");
                        else
                            System.out.println("    Failed: TestAll()");
                    } catch (NetException e) {
                        System.out.println("    Exception: TestAll()");
                    }
            }
            p.destroy();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * Created by m on 23/03/14.
 */
class StartServer {
    public static void main(String[] args) {
        try {
            ArrayList<String> blocklist = new ArrayList<String>();
            Task2.makeServer().start(56765,"password",blocklist);
        } catch (NetException e) {
            System.out.println("exception");
        }
        System.out.println("DONE");
    }
}

class StartServerBlocker {
    public static void main(String[] args) {
        try {
            ArrayList<String> blocklist = new ArrayList<String>();
            blocklist.add("127.0.0.1");
            Task2.makeServer().start(56765,"password",blocklist);
        } catch (NetException e) {
            System.out.println("exception");
        }
        System.out.println("DONE");
    }
}

/**
 * Created by m on 23/03/14.
 */


        /*try {
            client = Task2.makeClient();
            client.start("localhost", 56765);
            client.connect();
            client.sendPassword("password");
            Response r = client.echoString("hello");
            if (r instanceof StringContent)
                System.out.println(((StringContent)r).s_);
            else if (r instanceof AuthenticationFailed)
                System.out.println("AUTHENTICATION FAILED");
            else if (r instanceof Problem)
                System.out.println("GENERAL ERROR = " + ((Problem)r).msg_);
            client.clientExit();

            client = Task2.makeClient();
            client.start("localhost", 56765);
            client.connect();


            r = client.echoString("hello");
            if (r instanceof StringContent)
                System.out.println(((StringContent)r).s_);
            else if (r instanceof AuthenticationFailed)
                System.out.println("AUTHENTICATION FAILED");
            else if (r instanceof Problem)
                System.out.println("GENERAL ERROR = " + ((Problem)r).msg_);



            client.sendPassword("password");
            r = client.echoString("hello");
            if (r instanceof StringContent)
                System.out.println(((StringContent)r).s_);
            else if (r instanceof AuthenticationFailed)
                System.out.println("AUTHENTICATION FAILED");
            else if (r instanceof Problem)
                System.out.println("GENERAL ERROR = " + ((Problem)r).msg_);


            client.serverExit();
        } catch (NetException e) {
            System.out.println("exception");
        }
        System.out.println("DONE");*/
class StartClient {

    final static int ok = 0;
    final static int error = 1;
    final static int authentication_failed = 2;
    final static int cannot_send_file = 3;
    final static int cannot_receive_file = 4;
    final static int cannot_connect = 5;
    final static int client_blocked = 6;
    final static int directory_listing = 7;
    final static int file_content = 8;
    final static int byte_array_content = 9;
    final static int string_content = 10;

    public static int TestConnect() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        int code = convertResponse(client.connect());
        client.clientExit();
        return code;
    }

    public static int TestSendPassword() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        int code = convertResponse(client.sendPassword("password"));
        client.clientExit();
        return code;
    }

    public static int TestListDirectory() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        client.sendPassword("password");
        int code = convertResponse(client.listDirectory());
        client.clientExit();
        return code;
    }

    public static int TestSendFile() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        client.sendPassword("password");
        int code = convertResponse(client.sendFile("TESTFILE", "SOME\nTEST\nDATA"));
        client.clientExit();
        return code;
    }

    public static int TestReceiveFile() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        client.sendPassword("password");
        Response r = client.receiveFile("TESTFILE");
        int code;
        if (r instanceof FileContent)
        {
            if (((FileContent)r).fileData_.compareTo("SOME\nTEST\nDATA") != 0)
                return error;
        }
        code = convertResponse(r);
        client.clientExit();
        return code;
    }

    public static int TestEchoByteArray() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        client.sendPassword("password");
        byte[] bin = new byte[5];
        for (int i = 0 ; i < 5; i++)
        {
            bin[i] = ((byte)(100+i));
        }
        Response r = client.echoByteArray(bin);
        int code;
        if (r instanceof ByteArrayContent)
        {
            for (int i = 0 ; i < 5; i++)
                if (((ByteArrayContent)r).ba_[i] != bin[i])
                    return error;
        }
        code = convertResponse(r);
        client.clientExit();
        return code;
    }

    public static int TestEchoString() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        client.sendPassword("password");
        Response r = client.echoString("HELLO sErVeR!");
        int code;
        if (r instanceof StringContent)
        {
            if (((StringContent)r).s_.compareTo("HELLO sErVeR!") != 0)
                return error;
        }
        code = convertResponse(r);
        client.clientExit();
        return code;
    }



    public static int TestListDirectoryAuthenticationFalure() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        int code = convertResponse(client.listDirectory());
        client.clientExit();
        return code;
    }

    public static int TestSendFileAuthenticationFalure() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        int code = convertResponse(client.sendFile("TESTFILE", "SOME\nTEST\nDATA"));
        client.clientExit();
        return code;
    }

    public static int TestReceiveFileAuthenticationFalure() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        Response r = client.receiveFile("TESTFILE");
        int code;
        if (r instanceof FileContent)
        {
            if (((FileContent)r).fileData_.compareTo("SOME\nTEST\nDATA") != 0)
                return error;
        }
        code = convertResponse(r);
        client.clientExit();
        return code;
    }

    public static int TestEchoByteArrayAuthenticationFalure() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        byte[] bin = new byte[5];
        for (int i = 0 ; i < 5; i++)
        {
            bin[i] = ((byte)(100+i));
        }
        Response r = client.echoByteArray(bin);
        int code;
        if (r instanceof ByteArrayContent)
        {
            for (int i = 0 ; i < 5; i++)
                if (((ByteArrayContent)r).ba_[i] != bin[i])
                    return error;
        }
        code = convertResponse(r);
        client.clientExit();
        return code;
    }

    public static int TestEchoStringAuthenticationFalure() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        Response r = client.echoString("HELLO sErVeR!");
        int code;
        if (r instanceof StringContent)
        {
            if (((StringContent)r).s_.compareTo("HELLO sErVeR!") != 0)
                return error;
        }
        code = convertResponse(r);
        client.clientExit();
        return code;
    }

    public static int TestSendPasswordAuthenticationFalure() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        int code = convertResponse(client.sendPassword("NOTpassword"));
        client.clientExit();
        return code;
    }

    public static int TestReceiveFileCannotReceiveFile() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        client.sendPassword("password");
        Response r = client.receiveFile("TESTFILENOTTHERE");
        int code;
        if (r instanceof FileContent)
        {
            if (((FileContent)r).fileData_.compareTo("SOME\nTEST\nDATA") != 0)
                return error;
        }
        code = convertResponse(r);
        client.clientExit();
        return code;
    }

    public static int TestSendFileCannotSendFile() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        client.sendPassword("password");
        Response r = client.sendFile("TESTFILE", "CANTSENDTHIS");
        int code;
        code = convertResponse(r);
        client.clientExit();

        new File("TESTFILE").delete();
        return code;
    }

    public static int TestConnectBlocked() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        int code = convertResponse(client.connect());
        return code;
    }

    public static int TestServerExit() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        client.sendPassword("password");
        int code = convertResponse(client.serverExit());
        return code;
    }

    public static int TestServerExitAuthenticationFailure() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        client.connect();
        int code = convertResponse(client.serverExit());
        return code;
    }


    public static int TestAll() throws NetException {
        Client client;
        client = Task2.makeClient();
        client.start("localhost", 56765);
        if (convertResponse(client.connect()) == ok)
            System.out.println("    PASSED: connect()");
        else
            System.out.println("    Failed: connect()");


        if (convertResponse(client.sendPassword("password")) == ok)
            System.out.println("    PASSED: sendPassword()");
        else
            System.out.println("    Failed: sendPassword()");


        client.sendPassword("password");
        if (convertResponse(client.listDirectory()) == directory_listing)
            System.out.println("    PASSED: listDirectory()");
        else
            System.out.println("    Failed: listDirectory()");


        if (convertResponse(client.sendFile("TESTFILE", "SOME\nTEST\nDATA")) == ok)
            System.out.println("    PASSED: sendFile()");
        else
            System.out.println("    Failed: sendFile()");






        Response r = client.receiveFile("TESTFILE");
        new File("TESTFILE").delete();
        if (r instanceof FileContent)
        {
            if (((FileContent)r).fileData_.compareTo("SOME\nTEST\nDATA") != 0)
                System.out.println("    Failed: receiveFile()");
        }
        if (convertResponse(r) == file_content)
            System.out.println("    PASSED: receiveFile()");
        else
            System.out.println("    Failed: receiveFile()");




        byte[] bin = new byte[5];
        for (int i = 0 ; i < 5; i++)
        {
            bin[i] = ((byte)(100+i));
        }
        r = client.echoByteArray(bin);
        if (r instanceof ByteArrayContent)
        {
            for (int i = 0 ; i < 5; i++)
                if (((ByteArrayContent)r).ba_[i] != bin[i])
                    System.out.println("    Failed: echoByteArray()");
        }
        if (convertResponse(r) == byte_array_content)
            System.out.println("    PASSED: echoByteArray()");
        else
            System.out.println("    Failed: echoByteArray()");






       r = client.echoString("HELLO sErVeR!");
        if (r instanceof StringContent)
        {
            if (((StringContent)r).s_.compareTo("HELLO sErVeR!") != 0)
                System.out.println("    Failed: echoString()");
        }
        if (convertResponse(r) == string_content)
            System.out.println("    PASSED: echoString()");
        else
            System.out.println("    Failed: echoString()");






        r = client.receiveFile("TESTFILENOTTHERE");
        if (r instanceof FileContent)
        {
            if (((FileContent)r).fileData_.compareTo("SOME\nTEST\nDATA") != 0)
                System.out.println("    Failed: receiveFile()");
        }
        if (convertResponse(r) == cannot_receive_file)
            System.out.println("    PASSED: receiveFile()");
        else
            System.out.println("    Failed: receiveFile()");


        new File("TESTFILE").delete();

        return ok;
    }

    private static int convertResponse(Response r)
    {
        if (r instanceof OK)
            return ok;
        else if (r instanceof ClientBlocked)
            return client_blocked;
        else if (r instanceof CannotConnect)
            return cannot_connect;
        else if (r instanceof AuthenticationFailed)
            return authentication_failed;
        else if (r instanceof DirectoryListing)
            return directory_listing;
        else if (r instanceof FileContent)
            return file_content;
        if (r instanceof ByteArrayContent)
            return byte_array_content;
        if (r instanceof StringContent)
            return string_content;
        if (r instanceof CannotRecieveFile)
            return cannot_receive_file;
        if (r instanceof CannotSendFile)
            return cannot_send_file;

        return error;
    }
}
