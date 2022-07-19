# SharedComponentsDemoApp
- A Sinigle App to demo the shared components(Re usbale components) that have been developed as App services.

- Added shared components demo modlues, Such as:
- 1. Device Info
- 2. Also setting the code framework for Camera Capture
- 3. Elastic Search 
- 4. Compass
- 5. Network Manager
- 6. Barcode Scanner
- 7. Encryption/Decryption
- 8. UI Utitlity Components
- So that other sample app code can be added in similar manner as "Device Info" is being added.

### Developers can add there respective sample app code base here as an item in the navigation drawer.
## vii.  Encryption/Decryption
### AES Encryption Algorithm:
Should pass password and input text as params. Both params are String

```
private lateinit var encryptionViewModal: EncryptionViewModal
encryptionViewModal = ViewModelProvider(this).get(EncryptionViewModal::class.java)
encryptionViewModal.aesEncryptAlgorithm("123456", input)
```

### AES Decryption Algorithm:
Should pass password and input text as params. Both params are String. Here in below code input is encrypted text. The passwordshould be same for both encryption and decryption .

```
private lateinit var encryptionViewModal: EncryptionViewModal
encryptionViewModal = ViewModelProvider(this).get(EncryptionViewModal::class.java)
encryptionViewModal.aesDecryptionAlgorithm("123456", input)
```

### Generate RSA Secrete Key:

Below code is to generate RSA  Secrete key.

```
val kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        rsaKey = kpg.genKeyPair();
```

### RSA Encryption Algorithm:
Should pass input and  key as parameter. Input should be string and  key should be PublicKey. It returns encrypted value in string.
```
private lateinit var encryptionViewModal: EncryptionViewModal
encryptionViewModal = ViewModelProvider(this).get(EncryptionViewModal::class.java)
encryptionViewModal.rsaEncryptAlgorithm(input, rsaKey.public)
```

### RSA Decryption Algorithm:
Should pass input and  key as parameter. Input should be string (Encrypted value) and  key should be private key. It returns Decrypted  value in string.
```
private lateinit var encryptionViewModal: EncryptionViewModal
encryptionViewModal = ViewModelProvider(this).get(EncryptionViewModal::class.java)
encryptionViewModal.rsaDecryptAlgorithm(input, rsaKey.private)
```

## viii. UI Utitlity Components

### Snackbar:
Initializing `UISnackBar` by passing activity context `this`. If it's a fragment use `context as Activity` as a constructor parameter.

```
val snackBar = UISnackBar(this).apply {
            backgroundColorRes(R.color.purple_500)
            cornerRadius(7F)
            padding(10)
            textTypeface(Typeface.DEFAULT_BOLD)
            duration(Snackbar.LENGTH_SHORT)
            textColorRes(R.color.white)
            message("Test UISnackBar")
        }
```
To show the Snackbar just call the below line
```
snackBar.show()
```

### Progressbar:
To show custom progressbar call the below method. Pass activity context `this`, if it's a fragment use `context as Activity` as a constructor parameter.

```
UIProgressBar.showProgressBar(
            this,
            ContextCompat.getColor(context as Activity, R.color.purple_200))
 ```
 To hide progress dialog
 
 ```
 UIProgressBar.hideProgressBar()
 ```
 **NOTE:** To avoid memory leak, it is highly recommendable to call `hideProgressBar()` inside `onDestroy()` if it's activity and `onDestroyView()` is it's fragment.
 
### Alert Dialog:
To create a custom alert dialog use `UIAlertDialog` class and call the builder method followed by required title, body, icon and button text. If it's a fragment use `context as Activity` as a constructor parameter. `onPositive` & `onNegative` are anonymous callback methods.

```
 UIAlertDialog.build(this)
            .title("Alert Dialog Title")
            .body("Alert Dialog Body")
            .position(UIAlertDialog.POSITIONS.CENTER)
            .icon(R.drawable.custom_icon_tick)
            .onPositive("OK") {
                Log.d("TAG", "Positive Clicked")
            }.onNegative(
                "Cancel",
                buttonBackgroundColor = R.drawable.custom_rounded_white,
                textColor = ContextCompat.getColor(context as Activity, android.R.color.black)
            )
```

### RecyclerView:
This custom recyclerview will accept any kind of layout and data that we want to show in the list. With this there is not need to implement the adapter or the viewholder class. All we need is enable databinding in app gradle file, pass item layout and pass list of data.

```
val data: List<RecyclerItem> = listOfData.map {
            it.toRecyclerItem()
        }
setRecyclerViewItems(recyclerView, data)
```

```
fun YourDataClass.toRecyclerItem() = RecyclerItem(
        data = this,
        layoutId = R.layout.yout_item_layout,
        variableId = BR.sampleData)
```




