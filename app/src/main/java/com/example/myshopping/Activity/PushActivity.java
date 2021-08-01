package com.example.myshopping.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshopping.Model.Products;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PushActivity extends AppCompatActivity {
    TextView imageView_category,saveProduct;
    EditText nameproduct,describeproduct,priceProduct,numberproduct;
    ImageView imagepick;
    private Uri filePath;
    String category,name_product,describe,id,photo,name_seller;
    double price;
    int number;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Products");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        AnhXa();
        function();
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    filePath = selectedImage;
                    imagepick.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    filePath = selectedImage;
                    imagepick.setImageURI(selectedImage);
                }
                break;
        }
    }
    private void function() {
        category = getIntent().getStringExtra("category");
        imageView_category.setText(category);
        imagepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(PushActivity.this);
            }
        });
        imageView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PushActivity.this, SelectCategoryActivity.class);

                startActivity(intent);
            }
        });
        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushProducts();
           }
        });
    }
    private void pushProducts(){
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        name_seller = sharedPref.getString("name", "");
        name_product = nameproduct.getText().toString();
        describe = describeproduct.getText().toString();
        if(!priceProduct.getText().toString().equals(""))
        price = Double.parseDouble(priceProduct.getText().toString());
        if(!nameproduct.getText().toString().equals(""))
        number = Integer.parseInt(numberproduct.getText().toString());

        id = SupportCode.getUID();
        if(name_product.equals("")|| describe.equals("")||price==0||number==0||filePath==null){
            Toast.makeText(PushActivity.this,"Vui lòng điền hết thông tin",Toast.LENGTH_SHORT).show();
        }
        else{
            String time = String.valueOf(System.currentTimeMillis());
            // them anh len firebase
            StorageReference ref = storageReference.child("Products").child(time);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    //here uri.Tast is not success to end the while loop so put not equal to sing !
                    while (!uriTask.isSuccessful());
                    photo = uriTask.getResult().toString();
                    if (uriTask.isSuccessful()){
                        ref.getClass();
                        Products product = new Products(time,id, name_product,name_seller,describe,price,number,photo);
                        myRef.child(time).setValue(product);
                        Toast.makeText(PushActivity.this,"Đã đăng bán thành công sản phẩm", Toast.LENGTH_SHORT).show();
                        setText();
                    }

                }
            });
        }
    }
    private void setText(){
        nameproduct.setText("");
        describeproduct.setText("");
        priceProduct.setText("");
        nameproduct.setText("");
        numberproduct.setText("");
        imagepick.setImageResource(R.drawable.ic_add_pic);
    }
    private void AnhXa() {
        imageView_category =findViewById(R.id.imageView_category);
        imagepick = findViewById(R.id.imagepick);
        nameproduct = findViewById(R.id.nameproduct);
        describeproduct = findViewById(R.id.describeproduct);
        priceProduct = findViewById(R.id.priceProduct);
        saveProduct = findViewById(R.id.saveProduct);
        numberproduct=findViewById(R.id.numberproduct);
    }

    @Override
    protected void onResume() {
        SupportCode.updateOnlineStatus("online");
        super.onResume();
    }

    @Override
    protected void onPause() {
        SupportCode.updateOnlineStatus(String.valueOf(System.currentTimeMillis()));
        super.onPause();
    }
    
}