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
### Toast:
With this `UIToast`, you can create an attractive toast with icon and different background as well. Pass activity context `this` as a constructor parameter, else use `context as Activity` if it's a fragment. 

```
 UIToast.showToast(
            context as Activity, "Test Toast",
            Toast.LENGTH_LONG, R.drawable.your_icon, R.color.white, R.color.black
        ).show()
```

### Date Picker/Calendar:
With `UICalendarView`, we can create an interactive calendar with different background color, current day color and selected day color.

```
private fun showCalendar() {
        val dialog = Dialog(activity as Activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.your_calendar_layout)

        val currentCalendar = Calendar.getInstance(Locale.getDefault())
        val calendarView = dialog.findViewById<UICalendarView>(R.id.your_calendarview)

        calendarView.apply {
            //Show monday as first date of week
            initDayOfWeek = Calendar.MONDAY

            //Show/hide overflow days of a month
            isOverflowDateVisible = false

            calendarBackgroundColor =
                (ContextCompat.getColor(activity as Activity, R.color.white))

            calendarHeaderBackgroundColor =
                (ContextCompat.getColor(activity as Activity, android.R.color.holo_purple))
            calendarTitleTextColor = (ContextCompat.getColor(activity as Activity, R.color.black))
            weekLayoutBackgroundColor =
                (ContextCompat.getColor(activity as Activity, R.color.white))
            dayOfWeekTextColor = (ContextCompat.getColor(activity as Activity, R.color.black))
            dayOfMonthTextColor = (ContextCompat.getColor(activity as Activity, R.color.black))
            disabledDayBackgroundColor =
                (ContextCompat.getColor(activity as Activity, R.color.disabled_grey_light))
            disabledDayTextColor =
                (ContextCompat.getColor(activity as Activity, R.color.day_disabled_text_color))
            selectedDayBackground =
                (ContextCompat.getColor(activity as Activity, android.R.color.holo_green_dark))
            selectedDayTextColor = (ContextCompat.getColor(activity as Activity, R.color.white))
            currentDayOfMonth =
                (ContextCompat.getColor(activity as Activity, R.color.sky_blue))

            //call refreshCalendar to update calendar the view
            refreshCalendar(currentCalendar)
        }.setUICalendarListener(object : UICalendarListener {

            override fun onDateSelected(date: Date) {
                val df = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                Toast.makeText(activity as Activity, df.format(date), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onLongClick(date: Date) {
                val df = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                Toast.makeText(activity as Activity, df.format(date), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onMonthChanged(date: Date) {
                val df = SimpleDateFormat("MM-yyyy", Locale.ENGLISH)
                Toast.makeText(activity as Activity, df.format(date), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        dialog.show()
 ```

### Time Picker:
To create a customized time picker, use `UITimePicker`. End developer can send their own style/theme, time and title.

**Note:** For activity pass `supportFragmentManager` and for fragment pass `parentFragmentManager` as paremeter in show method.

```
private fun showTime() {
        val materialTimePicker = UITimePicker().materialTimeBuilder(
            R.style.Theme_DemoAppsForSharedComponent_TimePicker
        ).build()

        materialTimePicker.show(parentFragmentManager, "UIComponentFragment")
        
        materialTimePicker.addOnPositiveButtonClickListener {
            val pickedHour: Int = materialTimePicker.hour
            val pickedMinute: Int = materialTimePicker.minute

            UIToast.showToast(
                context as Activity, "Selected $pickedHour:$pickedMinute",
                Toast.LENGTH_LONG, R.drawable.custom_icon_tick, R.color.sky_blue, R.color.white
            ).show()
        }
    }
```




