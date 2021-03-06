package com.myowncountry.mystocks.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.firebase.db.FireBaseService;
import com.myowncountry.mystocks.recycleview.adapter.ShowAllUserAdapter;
import com.myowncountry.mystocks.recycleview.model.ShowAllUserModel;
import com.myowncountry.mystocks.service.UserService;
import com.myowncountry.mystocks.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class ShowAllUsersActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    private UserService userService;
    private FireBaseService fireBaseService;
    private RecyclerView showAllUserRC;
    private final List<ShowAllUserModel> showAllUserModelList = new ArrayList<>();
    private ShowAllUserAdapter showAllUserAdapter = new ShowAllUserAdapter(showAllUserModelList);
    private RelativeLayout showAllUserRL;
    private AlertDialog.Builder alertDialogBuilder;
    private FloatingActionButton createUserActionButton;
    private CreateUserDialogActivity createUserActionActivity;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_all_users);

        initObjects();

        initItems();

        userService.getAllUsers(fireBaseService.getCollection(GenericsConstants.USER_COLLECTION)).addOnSuccessListener(
                users -> {
                    users.getDocuments().forEach(user -> showAllUserModelList.add(new ShowAllUserModel(user.getId(), user.get("admin", Boolean.class))));
                    showAllUserAdapter.notifyDataSetChanged();
                }
        );
    }

    private void initItems() {


        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        showAllUserRC.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(showAllUserRC);


        showAllUserRC.setLayoutManager(new LinearLayoutManager(this));
        showAllUserRC.setAdapter(showAllUserAdapter);
        showAllUserRC.setHasFixedSize(true);

        createUserActionButton.setOnClickListener(v -> createUser());

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initObjects() {
        userService = UserService.getInstance();
        fireBaseService = FireBaseService.getInstance();
        showAllUserRC = findViewById(R.id.show_all_users_list_view);
        showAllUserRL = findViewById(R.id.show_all_user_activity);
        createUserActionButton = findViewById(R.id.show_all_user_add_user);
//        toolbar = findViewById(R.id.activity_show_all_user_toolbar);

        alertDialogBuilder = new AlertDialog.Builder(this);


        createUserActionActivity = new CreateUserDialogActivity(ShowAllUsersActivity.this);
        createUserActionActivity.setCallbackFunction(a -> onAddSuccess(a));
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            ShowAllUserModel data = showAllUserModelList.get(position);
            if (data.isAdmin()) {
                showAllUserAdapter.notifyDataSetChanged();
                Snackbar.make(showAllUserRL, "User cannot be deleted", BaseTransientBottomBar.LENGTH_SHORT).show();
            } else {
                alertDialogBuilder.setTitle("Delete?")
                        .setMessage("Do you want to delete the user?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            showAllUserModelList.remove(position);
                            showAllUserAdapter.notifyItemRemoved(position);
                            userService.deleteUser(data.getEmailId()).addOnSuccessListener(a -> {
                                Snackbar.make(showAllUserRL, "User deleted", BaseTransientBottomBar.LENGTH_SHORT).show();
                            });
                        }).setNegativeButton("No", (dialog, which) -> {
                            dialog.cancel();
                            showAllUserAdapter.notifyDataSetChanged();
                        })
                .create().show();
            }
        }
    };

    private void createUser() {
        createUserActionActivity.show();
    }

    private Void onAddSuccess(String emailId) {
        showAllUserModelList.add(new ShowAllUserModel(emailId, false));
        showAllUserAdapter.notifyDataSetChanged();
        Snackbar.make(showAllUserRL, "User added", BaseTransientBottomBar.LENGTH_SHORT).show();
        return null;
    }

}