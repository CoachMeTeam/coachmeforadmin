package com.coachme.coachmeforadmin.activities.admin.users;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.coachme.coachmeforadmin.R;
import com.coachme.coachmeforadmin.activities.admin.AdminActivity;
import com.coachme.coachmeforadmin.database.helpers.UserDatabaseHelper;
import com.coachme.coachmeforadmin.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.coachme.coachmeforadmin.CoachMeForAdminApp.getContext;


public class UsersListActivity extends AppCompatActivity {
    private final String ID_COLUMN = "ID";
    private final String NAME_COLUMN = "NOM";
    private final String FIRSTNAME_COLUMN = "PRENOM";
    private final String GOAL_COLUMN = "OBJECTIF";

    private Button goBackButton;
    private List<User> users = new ArrayList<>();
    UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        initFieldComponents();
        userDatabaseHelper = new UserDatabaseHelper();
        users = userDatabaseHelper.getUsers();

        addHeaders();
        addData(users);
    }

    public void onStart() {
        super.onStart();

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void initFieldComponents() {
        tableLayout = (TableLayout) findViewById(R.id.usersTable);
        goBackButton = (Button) findViewById(R.id.usersListGoBackButton);
    }

    private void addHeaders() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.usersTable);
        TableRow rowHeader = new TableRow(getContext());
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {ID_COLUMN, NAME_COLUMN, FIRSTNAME_COLUMN, GOAL_COLUMN};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.START);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);
    }

    private void addData(List<User> users) {
        for (User user : users) {
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            String[] colText = {Integer.toString(user.getId()), user.getName(), user.getFirstName(), user.getGoal()};
            for (String text : colText) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.START);
                tv.setTextSize(16);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(text);
                row.addView(tv);
            }
            tableLayout.addView(row);
        }
    }
}