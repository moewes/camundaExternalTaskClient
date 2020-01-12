package net.moewes.cloud;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class ManagementRessource {

  @Inject
  MyService service;

  @GET
  public String test() {
    return "OK";
  }

  @GET
  @Path("/start")
  public String start() {
    service.start();
    return "Service started";
  }

  @GET
  @Path("/stop")
  public String stop() {
    service.stop();
    return "Service stoped";
  }

}
