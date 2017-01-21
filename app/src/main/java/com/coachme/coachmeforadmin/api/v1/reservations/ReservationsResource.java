package com.coachme.coachmeforadmin.api.v1.reservations;

import com.coachme.coachmeforadmin.CoachMeForAdminApp;
import com.coachme.coachmeforadmin.database.helpers.MachineDatabaseHelper;
import com.coachme.coachmeforadmin.database.helpers.ReservationDatabaseHelper;
import com.coachme.coachmeforadmin.database.helpers.UserDatabaseHelper;
import com.coachme.coachmeforadmin.model.Reservation;
import com.coachme.coachmeforadmin.utils.Helper;
import com.coachme.coachmeforadmin.utils.errors.MyValidationException;
import com.coachme.coachmeforadmin.utils.errors.NotFoundException;

import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class ReservationsResource extends ServerResource {
    static ReservationDatabaseHelper reservationDatabaseHelper = new ReservationDatabaseHelper();
    static UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
    static MachineDatabaseHelper machineDatabaseHelper = new MachineDatabaseHelper();

    @Get("json")
    public Representation getReservations() throws ResourceException {
        String jsonReservations = Helper.convertObjectToJson(reservationDatabaseHelper.getReservations());

        return new JsonRepresentation(jsonReservations);
    }

    @Post("json")
    public void addReservation(JsonRepresentation representation) throws ResourceException {
        try {
            JSONObject jsonobject = representation.getJsonObject();
            Reservation reservation = Helper.convertJsonToObject(jsonobject.toString(), Reservation.class);
            Helper.checkConstraintsValidations(reservation);

            if (machineDatabaseHelper.getMachine(reservation.getMachineId()) != null ||
                    userDatabaseHelper.getUser(reservation.getUserId()) != null) {
                long reservationId = reservationDatabaseHelper.addReservation(reservation);
                Reservation reservationInserted = reservationDatabaseHelper.getReservation((int) reservationId);
                String jsonReservationInserted = Helper.convertObjectToJson(reservationInserted);

                getResponse().setEntity(new JsonRepresentation(jsonReservationInserted));
                getResponse().setStatus(Status.SUCCESS_CREATED);
            }
        } catch (MyValidationException e) {
            String jsonErrors = Helper.convertObjectToJson(e.getErrors());

            getResponse().setEntity(new JsonRepresentation(jsonErrors));
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (NotFoundException e) {
            String jsonError = Helper.convertObjectToJson(e.getError());

            getResponse().setEntity(new JsonRepresentation(jsonError));
            getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        } catch (Exception e) {
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
        }
    }
}
