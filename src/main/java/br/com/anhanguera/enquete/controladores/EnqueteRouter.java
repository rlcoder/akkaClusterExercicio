package br.com.anhanguera.enquete.controladores;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import br.com.anhanguera.enquete.dominio.Enquete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.complete;
import static akka.http.javadsl.server.Directives.delete;
import static akka.http.javadsl.unmarshalling.StringUnmarshallers.INTEGER;

public class EnqueteRouter {

    private static List<Enquete> enquetes = new ArrayList<>();

    public static Route routes() {
        return
                pathPrefix("enquete", () ->
                        route(
                                get(() -> {
                                    return complete(StatusCodes.OK, enquetes, Jackson.<List<Enquete>>marshaller());
                                }),
                                put(() ->
                                        entity(Jackson.unmarshaller(Enquete.class), enquete ->
                                                complete(StatusCodes.OK, enquete, Jackson.<Enquete>marshaller())
                                        )
                                ),
                                path("alternate", () ->
                                        put(() ->
                                                entity(Jackson.unmarshaller(Enquete.class), enquete -> {
                                                            enquetes.add(enquete);
                                                            return complete(StatusCodes.OK, enquete, Jackson.<Enquete>marshaller());
                                                        }
                                                )
                                        )
                                ),
                                path(INTEGER, petId -> route(
                                        delete(() -> {
                                            Iterator<Enquete> it = enquetes.iterator();
                                            while (it.hasNext()) {
                                                Enquete eq = it.next();
                                                if (eq.getId().equals(petId)) {
                                                    enquetes.remove(eq);
                                                }
                                            }
                                            return complete(StatusCodes.NO_CONTENT);
                                        })
                                ))
                        )
                );

    }
}


