package com.coachme.coachmeforadmin.api.v1;


import com.coachme.coachmeforadmin.api.v1.machines.MachineResource;
import com.coachme.coachmeforadmin.api.v1.machines.MachinesResources;
import com.coachme.coachmeforadmin.api.v1.reservations.ReservationResource;
import com.coachme.coachmeforadmin.api.v1.reservations.ReservationsResource;
import com.coachme.coachmeforadmin.api.v1.users.UserResource;
import com.coachme.coachmeforadmin.api.v1.users.UsersResource;
import com.coachme.coachmeforadmin.utils.ConfigPropertiesReader;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class CoachMeServer {
    static String urlPrefix = ConfigPropertiesReader.getConfig().getProperty("api_url_prefix_v1");

    public static void start() throws Exception {
        Component serverComponent = new Component();
        serverComponent.getServers().add(Protocol.HTTP, 5000);

        final Router router = new Router(serverComponent.getContext().createChildContext());
        router.attach(urlPrefix + "/users", UsersResource.class);
        router.attach(urlPrefix + "/users/{id}", UserResource.class);
        router.attach(urlPrefix + "/machines", MachinesResources.class);
        router.attach(urlPrefix + "/machines/{id}", MachineResource.class);
        router.attach(urlPrefix + "/reservations", ReservationsResource.class);
        router.attach(urlPrefix + "/reservations/{id}", ReservationResource.class);

        serverComponent.getDefaultHost().attach(router);
        serverComponent.start();
    }
}
