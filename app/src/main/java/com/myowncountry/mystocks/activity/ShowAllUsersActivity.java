package com.myowncountry.mystocks.activity;

import android.os.Bundle;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.User;
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

    }

    private void initObjects() {
        userService = UserService.getInstance();
        fireBaseService = FireBaseService.getInstance();
        showAllUserRC = findViewById(R.id.show_all_users_list_view);
        showAllUserRL = findViewById(R.id.show_all_user_activity);

        alertDialogBuilder = new AlertDialog.Builder(this);
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
                        }).setNegativeButton("No", (dialog, which) -> {dialog.cancel();})
                .create().show();
            }
//            Snackbar.make(showAllUserRC, viewHolder.getAdapterPosition(), BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    };

}