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
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class ReservationResource extends ServerResource {
    static ReservationDatabaseHelper reservationDatabaseHelper = CoachMeForAdminApp.getReservationDatabaseHelper();
    static UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
    static MachineDatabaseHelper machineDatabaseHelper = CoachMeForAdminApp.getMachineDatabaseHelper();
    private int reservationId;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        this.reservationId = Integer.parseInt((String) getRequestAttributes().get("id"));
    }

    @Get("json")
    public void getReservation() throws ResourceException {
        try {
            Reservation reservation = reservationDatabaseHelper.getReservation(reservationId);
            String jsonReservation = Helper.convertObjectToJson(reservation);

            getResponse().setEntity(new JsonRepresentation(jsonReservation));
            getResponse().setStatus(Status.SUCCESS_OK);
        } catch (NotFoundException e) {
            String jsonError = Helper.convertObjectToJson(e.getError());

            getResponse().setEntity(new JsonRepresentation(jsonError));
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    @Put("json")
    public void updateReservation(JsonRepresentation representation) throws ResourceException {
        try {
            JSONObject jsonobject = representation.getJsonObject();
            Reservation reservation = Helper.convertJsonToObject(jsonobject.toString(), Reservation.class);
            Helper.checkConstraintsValidations(reservation);

            if (machineDatabaseHelper.getMachine(reservation.getMachineId()) != null ||
                    userDatabaseHelper.getUser(reservation.getUserId()) != null) {
                reservationDatabaseHelper.updateReservation(reservationId, reservation);
                Reservation reservationUpdated = reservationDatabaseHelper.getReservation(reservationId);

                getResponse().setEntity(new JsonRepresentation(Helper.convertObjectToJson(reservationUpdated)));
                getResponse().setStatus(Status.SUCCESS_ACCEPTED);
            }
        } catch (NotFoundException e) {
            String jsonError = Helper.convertObjectToJson(e.getError());

            getResponse().setEntity(new JsonRepresentation(jsonError));
            getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        } catch (MyValidationException e) {
            String jsonErrors = Helper.convertObjectToJson(e.getErrors());

            getResponse().setEntity(new JsonRepresentation(jsonErrors));
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (Exception e) {
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
        }
    }
}
