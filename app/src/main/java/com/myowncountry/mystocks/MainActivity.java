package com.myowncountry.mystocks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.myowncountry.mystocks.activity.CreateShopDialogActivity;
import com.myowncountry.mystocks.activity.ShopSettings;
import com.myowncountry.mystocks.activity.ShowAllUsersActivity;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopDetails;
import com.myowncountry.mystocks.dto.User;
import com.myowncountry.mystocks.firebase.db.FireBaseService;
import com.myowncountry.mystocks.google.signin.SignInService;
import com.myowncountry.mystocks.recycleview.adapter.ShopDetailsAdapter;
import com.myowncountry.mystocks.recycleview.model.ShopDetailsRV;
import com.myowncountry.mystocks.service.ShopDetailService;
import com.myowncountry.mystocks.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SignInService signInService;
    private GoogleSignInAccount account;
    private FireBaseService fireBaseService;
    private UserService userService;
    private ShopDetailService shopDetailService;
    private FloatingActionButton addNewShop;
    private CreateShopDialogActivity createShopDialogActivity;

    private RelativeLayout mainActivityLayout;
    private MenuItem showAllUsers, shopSettingMenu;
    private ShopDetailsAdapter shopDetailsAdapter;
    private RecyclerView shopDetailsRC;

    private List<ShopDetailsRV> shopDetailsRVList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init object
        initObjects();

        //init Google
        initGoogle();

        //init layout and objects
        initLayoutAndObjects();

        //load content
        loadContents();
    }

    private void loadContents() {
        shopDetailService.getAllShopDetails().addOnSuccessListener(v-> {
           for (DocumentSnapshot doc :v.getDocuments()) {
               shopDetailsRVList.add(new ShopDetailsRV(doc.toObject(ShopDetails.class), doc.getId()));
           }
           shopDetailsAdapter.notifyDataSetChanged();
        });
    }

    private void initLayoutAndObjects() {
        mainActivityLayout = findViewById(R.id.main_activity);
        addNewShop = findViewById(R.id.main_activity_add_new_shop);
        addNewShop.setOnClickListener(v -> createShopDialogActivity.show());
        shopDetailsAdapter = new ShopDetailsAdapter(shopDetailsRVList);
        shopDetailsRC = findViewById(R.id.main_activity_shop_details);



        //init recycle view
        shopDetailsRC.setAdapter(shopDetailsAdapter);
        shopDetailsRC.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        shopDetailsRC.setHasFixedSize(true);
    }

    private void addNewShop(ShopDetails shopDetails) {
        shopDetails.setOutstandingBottles(0);
        shopDetails.setOutstandingBottles(0);
        shopDetailService.createShopDetails(shopDetails).addOnSuccessListener(v -> {
            String id = v.getId();
            Snackbar.make(mainActivityLayout, "Created new shop", Snackbar.LENGTH_SHORT);
            shopDetailsRVList.add(new ShopDetailsRV(shopDetails, id));
            shopDetailsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        showAllUsers = menu.findItem(R.id.show_users);
        shopSettingMenu = menu.findItem(R.id.show_shop_setting);
        return true;
    }

    private void initFireBase() {
        fireBaseService.init(getApplicationContext());
    }

    private void initObjects() {
        signInService = SignInService.getInstant();
        fireBaseService = FireBaseService.getInstance();
        userService = UserService.getInstance();
        shopDetailService = ShopDetailService.getInstance();
        createShopDialogActivity = new CreateShopDialogActivity(MainActivity.this, shopDetails -> addNewShop(shopDetails));
    }

    private void initiateUserProcess(User user) {
        if (user.isAdmin()) {
            showAllUsers.setVisible(true);
            shopSettingMenu.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                signInService.logout(getApplicationContext());
                return true;
            case R.id.show_users:
                startActivity(new Intent(getApplicationContext(), ShowAllUsersActivity.class));
                return true;
            case R.id.show_shop_setting:
                startActivity(new Intent(getApplicationContext(), ShopSettings.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUser() {
        //init Firebase
        initFireBase();

        try {
            userService.getUser(fireBaseService.getCollection(GenericsConstants.USER_COLLECTION), account.getEmail())
                    .addOnSuccessListener((a) -> {
                        List<User> userObj = a.toObjects(User.class);
                        if (userObj!= null && !userObj.isEmpty()) {
                            if (userObj.get(0).isActive()) {
                                initiateUserProcess(userObj.get(0));
                                Snackbar.make(mainActivityLayout, "User login success", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(mainActivityLayout, "User is not active", Snackbar.LENGTH_SHORT).show();
                                this.finish();
                            }
                        } else {
                            Snackbar.make(mainActivityLayout, "User is not active", Snackbar.LENGTH_SHORT).show();
                            this.finish();
                        }
                    });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGoogle() {
        ActivityResultLauncher<Intent> activityContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                a ->{
                    account = signInService.getCredentials(a.getData());
                    initUser();
                });
        account = GoogleSignIn.getLastSignedInAccount(this.getApplicationContext());
        if (account == null) {
            Intent in = signInService.getCredentials(getApplicationContext());
            activityContent.launch(in);
        } else {
            initUser();
        }
    }
}