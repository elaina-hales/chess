package handlers;

import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import requestsresults.*;

public class RegisterHandler {
    public static String handleRequest(Request req, Response res){
        LoginRequest request = (LoginRequest)Gson.fromJson(req, LoginRequest.class);

        LoginService service = new LoginService();
        LoginResult result = service.login(request);

        return Gson.toJson(result);
    }
}
