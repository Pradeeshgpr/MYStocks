package com.myowncountry.mystocks;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.myowncountry.mystocks.activity.CreateShopDialogActivity;
import com.myowncountry.mystocks.activity.ShopSettings;
import com.myowncountry.mystocks.activity.ShopTransactionDetails;
import com.myowncountry.mystocks.activity.ShowAllUsersActivity;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopDetails;
import com.myowncountry.mystocks.dto.User;
import com.myowncountry.mystocks.firebase.db.FireBaseService;
import com.myowncountry.mystocks.google.signin.SignInService;
import com.myowncountry.mystocks.recycleview.adapter.ShopDetailsAdapter;
import com.myowncountry.mystocks.recycleview.model.ShopDetailsRV;
import com.myowncountry.mystocks.recycleview.model.ShowAllUserModel;
import com.myowncountry.mystocks.service.ShopDetailService;
import com.myowncountry.mystocks.service.ShopTransactionService;
import com.myowncountry.mystocks.service.UserService;
import com.myowncountry.mystocks.util.ExecutorServiceUtils;
import com.myowncountry.mystocks.util.VerticalSpacingItemDecorator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private SignInService signInService;
    private GoogleSignInAccount account;
    private FireBaseService fireBaseService;
    private UserService userService;
    private ShopDetailService shopDetailService;
    private ShopTransactionService shopTransactionService;
    private FloatingActionButton addNewShop;
    private CreateShopDialogActivity createShopDialogActivity;

    private RelativeLayout mainActivityLayout;
    private MenuItem showAllUsers, shopSettingMenu, export;
    private ShopDetailsAdapter shopDetailsAdapter;
    private RecyclerView shopDetailsRC;
    private AlertDialog.Builder alertDialogBuilder;
    private User user = null;
    private EditText searchET;
    private ActivityResultLauncher<Intent> activityForResult;
    private ExecutorService executorService;

    private static final String EXPORT_CSV_TEMPLATE = "\"%s\",\"%s\",\"%s\",\"%s\"\n";

    public static final int REQUEST_WRITE_STORAGE = 112;


    private List<ShopDetailsRV> shopDetailsRVList = new ArrayList<>();
    private List<ShopDetailsRV> shopDetailsRVNonFilteredList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    ShopDetailsRV data = (ShopDetailsRV) result.getData().getSerializableExtra(GenericsConstants.SHOP_DETAILS);
                    if (data instanceof ShopDetailsRV) {
                        for (ShopDetailsRV shopDetailsRV : shopDetailsRVNonFilteredList) {
                            if (shopDetailsRV.getId().equals(data.getId())) {
                                shopDetailsRV.setOutstandingAmount(data.getOutstandingAmount());
                                shopDetailsRV.setOutstandingBottles(data.getOutstandingBottles());
                                shopDetailsAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                }
            }

        });

        //init object
        initObjects();

        //init Google
        initGoogle();

        //init layout and objects
        initLayoutAndObjects();

    }

    private void loadContents() {
        shopDetailService.getAllShopDetails().addOnSuccessListener(v-> {
           for (DocumentSnapshot doc :v.getDocuments()) {
               ShopDetailsRV data = new ShopDetailsRV(doc.toObject(ShopDetails.class), doc.getId());
               shopDetailsRVList.add(data);
               shopDetailsRVNonFilteredList.add(data);
           }
           shopDetailsAdapter.notifyDataSetChanged();
        });
    }

    private void initLayoutAndObjects() {
        mainActivityLayout = findViewById(R.id.main_activity);
        searchET = findViewById(R.id.main_activity_search_bar);
        addNewShop = findViewById(R.id.main_activity_add_new_shop);
        addNewShop.setOnClickListener(v -> createShopDialogActivity.show());
        shopDetailsAdapter = new ShopDetailsAdapter(shopDetailsRVList, v-> shopSelected(v));
        shopDetailsRC = findViewById(R.id.main_activity_shop_details);
        alertDialogBuilder = new AlertDialog.Builder(this);

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                shopDetailsRVList.clear();
                if (text == null || text.isEmpty()) {
                    shopDetailsRVList.addAll(shopDetailsRVNonFilteredList);

                } else {
                    shopDetailsRVList.addAll(shopDetailsRVNonFilteredList.stream().filter(doc -> doc.getName().contains(text)).collect(Collectors.toList()));
                }
                shopDetailsAdapter.notifyDataSetChanged();
            }
        });



        //init recycle view
        shopDetailsRC.setAdapter(shopDetailsAdapter);
        shopDetailsRC.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        shopDetailsRC.setHasFixedSize(true);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        shopDetailsRC.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(shopDetailsRC);
    }

    private void shopSelected(View v) {
        int itemPosition = shopDetailsRC.getChildLayoutPosition(v);
        Intent intent = new Intent(getApplicationContext(), ShopTransactionDetails.class);
        intent.putExtra(GenericsConstants.SHOP_DETAILS, shopDetailsRVList.get(itemPosition));
        activityForResult.launch(intent);
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
        export = menu.findItem(R.id.show_export);
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
        shopTransactionService = ShopTransactionService.getInstance();
        executorService = ExecutorServiceUtils.getInstance();
    }

    private void initiateUserProcess(User user) {
        if (user.isAdmin()) {
            showAllUsers.setVisible(true);
            shopSettingMenu.setVisible(true);
            export.setVisible(true);
            addNewShop.setVisibility(View.VISIBLE);
        }
        this.user = user;
        //load content
        loadContents();
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
                return true;
            case R.id.show_export:
                initExportProcess();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initExportProcess() {
        Context context = getApplicationContext();
        boolean hasPermission = (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
            Snackbar.make(mainActivityLayout, "Re initiate export", BaseTransientBottomBar.LENGTH_SHORT).show();
        } else {
            try {
                Snackbar.make(mainActivityLayout, "Export started", BaseTransientBottomBar.LENGTH_SHORT).show();
                File dir = new File(context.getExternalFilesDir(null) + "/export-my-stocks");
                if (!dir.exists() &&  !dir.mkdirs()) {
                    Log.e("Main Activity", "initExportProcess: Unable to export");
                }
                dir = new File(dir.getAbsolutePath() + "/"  + LocalDateTime.now().toString() + ".csv");
                dir.createNewFile();
                FileOutputStream out = new FileOutputStream(dir);
                out.write("NAME, ADDRESS, \"OUTSTANDING AMOUNT\", \"OUTSTANDING BOTTLES\"\n".getBytes(StandardCharsets.UTF_8));
                for (ShopDetailsRV shopDetailsRV : shopDetailsRVNonFilteredList) {
                    out.write(String.format(EXPORT_CSV_TEMPLATE, shopDetailsRV.getName(), shopDetailsRV.getAddress(), shopDetailsRV.getOutstandingAmount(), shopDetailsRV.getOutstandingBottles()).getBytes(StandardCharsets.UTF_8));
                }
                out.close();
                Snackbar.make(mainActivityLayout, "Export Completed", BaseTransientBottomBar.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Snackbar.make(mainActivityLayout, "Initiating export", BaseTransientBottomBar.LENGTH_SHORT).show();
            executorService.execute(() -> {
                File file = new File(android.os.Environment.getStorageDirectory().getAbsolutePath() + "/export-my-stocks" + LocalDateTime.now().toString());
            });
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            Snackbar.make(mainActivityLayout, "Enable storage permission to write data", BaseTransientBottomBar.LENGTH_SHORT).show();
        }*/
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

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (user == null || !user.isAdmin()) {
                Snackbar.make(mainActivityLayout, "You don't have access to delete", BaseTransientBottomBar.LENGTH_SHORT).show();
                shopDetailsAdapter.notifyItemChanged(position);
                return;
            }
            ShopDetailsRV data = shopDetailsRVList.get(position);
            alertDialogBuilder.setTitle("Delete?")
                    .setMessage("Do you want to delete the user?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        shopDetailService.deleteDocument(data.getId()).addOnSuccessListener(a -> {
                            Snackbar.make(mainActivityLayout, "Shop deleted", BaseTransientBottomBar.LENGTH_SHORT).show();
                            shopDetailsRVList.remove(position);
                            shopDetailsAdapter.notifyItemRemoved(position);
                            shopTransactionService.deleteCollection(data.getId()).addOnSuccessListener(aa -> {
                                Snackbar.make(mainActivityLayout, "Shop Transaction deleted", BaseTransientBottomBar.LENGTH_SHORT).show();
                            });
                        });
                    }).setNegativeButton("No", (dialog, which) -> {
                        shopDetailsAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }).create().show();
        }
    };
}