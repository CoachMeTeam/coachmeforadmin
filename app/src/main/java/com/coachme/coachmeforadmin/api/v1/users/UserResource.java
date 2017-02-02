package com.coachme.coachmeforadmin.api.v1.users;

import com.coachme.coachmeforadmin.database.helpers.UserDatabaseHelper;
import com.coachme.coachmeforadmin.model.User;
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

public class UserResource extends ServerResource {
    static UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
    private int userId;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        this.userId = Integer.parseInt((String) getRequestAttributes().get("id"));
    }

    @Get("json")
    public void getUser() throws ResourceException {
        try {
            User user = userDatabaseHelper.getUser(userId);
            String jsonUser = Helper.convertObjectToJson(user);

            getResponse().setEntity(new JsonRepresentation(jsonUser));
            getResponse().setStatus(Status.SUCCESS_OK);
        } catch (NotFoundException e) {
            String jsonError = Helper.convertObjectToJson(e.getError());

            getResponse().setEntity(new JsonRepresentation(jsonError));
            getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        }
    }

    @Put("json")
    public void updateUser(JsonRepresentation representation) throws ResourceException {
        try {
            JSONObject jsonobject = representation.getJsonObject();
            User user = Helper.convertJsonToObject(jsonobject.toString(), User.class);
            Helper.checkConstraintsValidations(user);

            userDatabaseHelper.updateUser(userId, user);
            User userUpdated = userDatabaseHelper.getUser(userId);

            getResponse().setEntity(new JsonRepresentation(Helper.convertObjectToJson(userUpdated)));
            getResponse().setStatus(Status.SUCCESS_ACCEPTED);
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
