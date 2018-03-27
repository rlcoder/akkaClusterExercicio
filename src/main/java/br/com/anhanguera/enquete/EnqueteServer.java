package br.com.anhanguera.enquete;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import br.com.anhanguera.enquete.controladores.EnqueteRouter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;

import static akka.http.javadsl.server.Directives.*;

public class EnqueteServer {

    public static Route routes() {
        return route(
                path("", () ->
                        getFromResource("web/index.html")
                ),
                EnqueteRouter.routes()
        );
    }

    public static void main(String[] args) throws IOException {

    	//carrega o arquivo de configuração padrão
        Config config = ConfigFactory.load();
        //inicializando sistema de atores para 
        //criar o cluster
        ActorSystem system = ActorSystem.create("enquete-server", config);

        //configurando o servidor REST(Http)
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final ConnectHttp host = ConnectHttp.toHost("0.0.0.0", 8080);

        Http.get(system).bindAndHandle(routes()
                .flow(system, materializer), host, materializer);

        System.out.println("Pressione ENTER para finalizar...");
        System.in.read();
        system.terminate();
    }

}
