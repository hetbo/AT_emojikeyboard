// This activity contains a button 
// Click on button to get permission for CAMERA
public class DefaultMethod extends AppCompatActivity {
    private static int CAMERA_REQUEST_CODE = 100;
    AppCompatButton requestButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //find button
        requestButton = findViewById(R.id.request);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(DefaultMethod.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    //Permission is not granted
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DefaultMethod.this,Manifest.permission.CAMERA)){
                        //request denied once. we should say why we need this
                        String detail = "برای گرفتن عکس نیاز به دسترسی به دوربین می باشد";
                        Snackbar.make(view,detail,Snackbar.LENGTH_INDEFINITE).setAction("اجازه دادن", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //ask for permission
                                ActivityCompat.requestPermissions(DefaultMethod.this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
                            }
                        }).show();
                    }else{
                        //First time asking permission
                        ActivityCompat.requestPermissions(DefaultMethod.this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
                    }
                }else{
                    //permission already granted
                    doSomething();
                }
            }
        });
    }
    private void doSomething(){
        //Here We Do Things that need Permission
        Toast.makeText(DefaultMethod.this,"دسترسی داده شده است. میتوان عمل مورد نظر را انجام داد!",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //We have our permission
                doSomething();
            }else{
                //Permission denied completely and never ask again is pressed
                //if permission is not necessary deactivate this feature otherwise redirect user to phone settings
                //in order to grant permission manually
                String detail = "برای گرفتن عکس نیاز به دسترسی به دوربین می باشد. برای اجازه دادن به تنظیمات تلفن همراه مراجعه کنید";
                Snackbar.make(requestButton,detail,Snackbar.LENGTH_INDEFINITE).setAction("اجازه دادن", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        i.setData(Uri.fromParts("package",getPackageName(),null));
                        startActivity(i);
                    }
                }).show();
            }
        }
    }
}
