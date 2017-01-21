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
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class MachinesResources extends ServerResource {
    static MachineDatabaseHelper machineDatabaseHelper = new MachineDatabaseHelper();

    @Get("json")
    public Representation getMachines() throws ResourceException {
        String jsonMachines = Helper.convertObjectToJson(machineDatabaseHelper.getMachines());

        return new JsonRepresentation(jsonMachines);
    }

    @Post("json")
    public void addMachine(JsonRepresentation representation) throws ResourceException {
        try {
            JSONObject jsonObject = representation.getJsonObject();
            Machine machine = Helper.convertJsonToObject(jsonObject.toString(), Machine.class);
            Helper.checkConstraintsValidations(machine);

            long machineId = machineDatabaseHelper.addMachine(machine);
            Machine machineInserted = machineDatabaseHelper.getMachine((int) machineId);
            String jsonMachineInserted = Helper.convertObjectToJson(machineInserted);

            getResponse().setEntity(new JsonRepresentation(jsonMachineInserted));
            getResponse().setStatus(Status.SUCCESS_CREATED);
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
