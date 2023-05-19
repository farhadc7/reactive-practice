package sample;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router= Router.router(vertx);
    router.route().handler(context->{
      String address=context.request().connection().remoteAddress().toString();
      MultiMap queryParams= context.queryParams();
      String name= queryParams.contains("name")?queryParams.get("name"): "unknwon";
      context.json(
        new JsonObject().
          put("name",name).
          put("address",address).
          put("message","hello a"+name + " from "+ address)
      );
    });

    vertx.createHttpServer().requestHandler(router).listen(8888).onSuccess(server->
      System.out.println("http server started on port :"+server.actualPort())
    ).onFailure()
  }
}
