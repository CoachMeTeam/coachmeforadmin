package com.coachme.coachmeforadmin.api.v1.machines;

import com.coachme.coachmeforadmin.CoachMeForAdminApp;
import com.coachme.coachmeforadmin.database.helpers.MachineDatabaseHelper;
import com.coachme.coachmeforadmin.model.Machine;
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

public class MachineResource extends ServerResource {
    static MachineDatabaseHelper machineDatabaseHelper = CoachMeForAdminApp.getMachineDatabaseHelper();
    private int machineId;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        this.machineId = Integer.parseInt((String) getRequestAttributes().get("id"));
    }

    @Get("json")
    public void getMachine() throws ResourceException {
        try {
            Machine machine = machineDatabaseHelper.getMachine(machineId);
            String jsonMachine = Helper.convertObjectToJson(machine);

            getResponse().setEntity(new JsonRepresentation(jsonMachine));
            getResponse().setStatus(Status.SUCCESS_OK);
        } catch (NotFoundException e) {
            String jsonError = Helper.convertObjectToJson(e.getError());

            getResponse().setEntity(new JsonRepresentation(jsonError));
            getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        }
    }

    @Put("json")
    public void updateMachine(JsonRepresentation representation) throws ResourceException {
        try {
            JSONObject jsonobject = representation.getJsonObject();
            Machine machine = Helper.convertJsonToObject(jsonobject.toString(), Machine.class);
            Helper.checkConstraintsValidations(machine);

            if (machineDatabaseHelper.getMachine(machineId) != null) {
                machineDatabaseHelper.updateMachine(machineId, machine);
                Machine machineUpdated = machineDatabaseHelper.getMachine((int) machineId);

                getResponse().setEntity(new JsonRepresentation(Helper.convertObjectToJson(machineUpdated)));
                getResponse().setStatus(Status.SUCCESS_ACCEPTED);
            } else {
                getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
            }
        } catch (MyValidationException e) {
            String jsonErrors = Helper.convertObjectToJson(e.getErrors());

            getResponse().setEntity(new JsonRepresentation(jsonErrors));
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (Exception e) {
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
        }
    }
}
