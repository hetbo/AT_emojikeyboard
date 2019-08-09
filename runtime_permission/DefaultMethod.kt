class Test2Activity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        permissionButton.setOnClickListener { 
            if (ActivityCompat.checkSelfPermission(this@Test2Activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //Permission is not granted
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@Test2Activity, Manifest.permission.CAMERA)) {
                    //request denied once. we should say why we need this
                    val detail = "برای گرفتن عکس نیاز به دسترسی به دوربین می باشد"
                    Snackbar.make(it, detail, Snackbar.LENGTH_INDEFINITE).setAction("اجازه دادن") {
                        //ask for permission
                        ActivityCompat.requestPermissions(this@Test2Activity, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE) 
                    }.show()
                } else {
                    //First time asking permission
                    ActivityCompat.requestPermissions(this@Test2Activity, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
                }
            } else {
                //permission already granted
                doSomething()
            }
        }
    }

    private fun doSomething() {
        //Here We Do Things that need Permission
        Toast.makeText(this@Test2Activity, "دسترسی داده شده است. میتوان عمل مورد نظر را انجام داد!", Toast.LENGTH_LONG)
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have our permission
                doSomething()
            } else {
                //Permission denied completely and never ask again is pressed
                //if permission is not necessary deactivate this feature otherwise redirect user to phone settings
                //in order to grant permission manually
                val detail = "برای گرفتن عکس نیاز به دسترسی به دوربین می باشد. برای اجازه دادن به تنظیمات تلفن همراه مراجعه کنید"
                Snackbar.make(permissionButton, detail, Snackbar.LENGTH_INDEFINITE).setAction("اجازه دادن") {
                    val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    i.data = Uri.fromParts("package", packageName, null)
                    startActivity(i)
                }.show()
            }
        }
    }
}
