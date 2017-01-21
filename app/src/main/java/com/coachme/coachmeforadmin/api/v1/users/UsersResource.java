package com.coachme.coachmeforadmin.api.v1.users;

import com.coachme.coachmeforadmin.CoachMeForAdminApp;
import com.coachme.coachmeforadmin.database.helpers.UserDatabaseHelper;
import com.coachme.coachmeforadmin.model.User;
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


public class UsersResource extends ServerResource {
    static UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();

    @Get("json")
    public Representation getUsers() throws ResourceException {
        String jsonUsers = Helper.convertObjectToJson(userDatabaseHelper.getUsers());

        return new JsonRepresentation(jsonUsers);
    }

    @Post("json")
    public void addUser(JsonRepresentation representation) throws ResourceException {
        try {
            JSONObject jsonobject = representation.getJsonObject();
            User user = Helper.convertJsonToObject(jsonobject.toString(), User.class);
            Helper.checkConstraintsValidations(user);

            long userId = userDatabaseHelper.addUser(user);
            User userInserted = userDatabaseHelper.getUser((int) userId);
            String jsonUserInserted = Helper.convertObjectToJson(userInserted);

            getResponse().setEntity(new JsonRepresentation(jsonUserInserted));
            getResponse().setStatus(Status.SUCCESS_CREATED);
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
