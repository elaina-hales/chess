package client;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url){
        this.serverUrl = url;
    }

    // takes in a port or a full url
    // call this class from client to make requests to the server
}
