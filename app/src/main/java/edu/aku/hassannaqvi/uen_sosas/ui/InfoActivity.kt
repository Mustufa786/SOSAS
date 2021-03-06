package edu.aku.hassannaqvi.uen_sosas.ui

//import android.support.v4.content.ContextCompat.startActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import edu.aku.hassannaqvi.uen_sosas.R
import edu.aku.hassannaqvi.uen_sosas.adapter.ChildListAdapter
import edu.aku.hassannaqvi.uen_sosas.contracts.AreasContract
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract
import edu.aku.hassannaqvi.uen_sosas.contracts.FormsContract
import edu.aku.hassannaqvi.uen_sosas.contracts.VillagesContract
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper
import edu.aku.hassannaqvi.uen_sosas.core.MainApp
import edu.aku.hassannaqvi.uen_sosas.core.MainApp.*
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivityInfoBinding
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class InfoActivity : AppCompatActivity() {


    lateinit var bi: ActivityInfoBinding
    lateinit var db: DatabaseHelper
    var villageCode: String? = null
    var areaCode: String? = null
    var hhNo: String? = null
    var adapter: ChildListAdapter? = null
    lateinit var villageList: Collection<VillagesContract>
    lateinit var villageNames: ArrayList<String>
    lateinit var villageMap: HashMap<String, String>
    lateinit var motherList: List<FamilyMembersContract>

    var flag = false
//    lateinit var womenList: List<Int>

    companion object {
        lateinit var womenList: MutableList<Pair<Int, Boolean>>

        fun checkingWomenExist(serial: Int): Boolean {
            val item = womenList.find { it.first == serial }
            return item?.second ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bi = DataBindingUtil.setContentView(this, R.layout.activity_info)
        bi.callback = this
        db = DatabaseHelper(this)

        setupViews()
    }

    private fun setupViews() {

        motherList = ArrayList()
        womenList = ArrayList()
        val list: Collection<AreasContract> = db.getAllAreas(MainApp.ucCode)
        var areaNames = ArrayList<String>()
        areaNames.add("-Select Area-")
        val areaMap: HashMap<String, String> = HashMap()
        for (item in list) {
            areaNames.add(item.area)
            areaMap[item.area] = item.areacode
        }

        bi.areaSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, areaNames)
        bi.areaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (bi.areaSpinner.selectedItemPosition != 0) {
                    areaCode = areaMap[bi.areaSpinner.selectedItem.toString()]

                    villageList = db.getVillages(areaCode)
                    villageNames = ArrayList()
                    villageNames.add("-Select Village Name-")
                    villageMap = HashMap()

                    for (item in villageList) {
                        villageNames.add(item.villagename)
                        villageMap[item.villagename] = item.villagecode
                    }
                    bi.villageSpinner.adapter = ArrayAdapter(this@InfoActivity, android.R.layout.simple_list_item_1, villageNames)

                }

            }
        }

        bi.villageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (bi.villageSpinner.selectedItemPosition != 0) {
                    villageCode = villageMap[bi.villageSpinner.selectedItem.toString()]
                    bi.clusterNumber.text = villageCode
                }


            }

        }

        bi.hhName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (motherList.isNotEmpty()) {
                    motherList = emptyList()
                    setupRecyclerView()
                }
            }

        })

        bi.checkHH.setOnClickListener {
            if (formValidation()) {
                if (bi.hhName.text.toString() != "") {
                    motherList = db.getMotherList(bi.hhName.text.toString(), villageCode)
                    if (motherList.isNotEmpty()) {
                        Toast.makeText(this@InfoActivity, "Mother Found", Toast.LENGTH_SHORT).show()
                        setupRecyclerView()
                    } else {
                        Toast.makeText(this@InfoActivity, "Mother Not Found", Toast.LENGTH_SHORT).show()
                    }

                }
            }

        }

    }

    fun setupRecyclerView() {
        adapter = ChildListAdapter(this@InfoActivity, motherList, true)
        bi.motherList.layoutManager = LinearLayoutManager(this@InfoActivity)
        bi.motherList.adapter = adapter
        adapter?.setItemClicked { item, position, isMother, holder ->
            openDialog(this@InfoActivity, item, isMother)
            itemClick = OnItemClick {
                if (!flag) {
                    saveDraft(item)
                    if (!updateDB()) {
                        return@OnItemClick
                    }
                }
                womenList.add(Pair(item.serialno.toInt(), true))

                startActivity(Intent(this@InfoActivity, SectionBActivity::class.java).putExtra(motherInfo, item))
            }

        }
    }

    private fun saveDraft(item: FamilyMembersContract) {
        fc = FormsContract()
        val pref = getSharedPreferences("tagName", Context.MODE_PRIVATE)
        fc.devicetagID = pref.getString("tagName", null)
        fc.deviceID = deviceId
        fc.formDate = DateFormat.format("dd-MM-yyyy HH:mm", Date()).toString()
        fc.user = userName
        fc.talukdaCode = talukaCode.toString()
        fc.uc = ucCode.toString()
        fc.areaCode = areaCode
        fc.village = villageCode
        fc.appversion = appInfo.appVersion
        fc.clusterCode = bi.clusterNumber.text.toString()
        fc.hhno = bi.hhName.text.toString()

        val json = JSONObject()
        json.put("_luid", item.uuid)
        fc.setsA(json.toString())


        setGPS(this)

    }

    private fun formValidation(): Boolean {
        return ValidatorClass.EmptyCheckingContainer(this, bi.checkLayout1)
    }

    private fun updateDB(): Boolean {
        val rowId: Long
        val db = DatabaseHelper(this)
        rowId = db.addForm(fc)
        return if (rowId > 0) {
            fc._ID = rowId.toString()
            fc._UID = fc.deviceID + fc._ID
            db.updateFormID()
            true
        } else {
            false
        }
    }

    override fun onResume() {
        super.onResume()

        if (adapter == null) return

        if (womenList.size == motherList.size) {
            finish()
            startActivity(Intent(this, EndingActivity::class.java).putExtra("complete", true))
            return
        }

        flag = true

        bi.checkLayout1.visibility = View.GONE
        bi.checkLayout2.visibility = View.GONE

        adapter?.notifyDataSetChanged()

    }
}
