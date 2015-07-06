package hello1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/users/{username}")
public class UserResource {

  @GET
  // @Produces("text/xml")
  @Produces("text/html")
  public String getUser(@PathParam("username") String userName) {
    // return "hai " + userName;
    return "<html lang=\"en\"><body><h1>Hello, " + userName + "</h1></body></html>";
  }
}
