package edu.aku.hassannaqvi.uen_sosas.ui

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import edu.aku.hassannaqvi.uen_sosas.R
import edu.aku.hassannaqvi.uen_sosas.adapter.ChildListAdapter
import edu.aku.hassannaqvi.uen_sosas.contracts.AreasContract
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract
import edu.aku.hassannaqvi.uen_sosas.contracts.FormsContract
import edu.aku.hassannaqvi.uen_sosas.contracts.VillagesContract
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper
import edu.aku.hassannaqvi.uen_sosas.core.MainApp
import edu.aku.hassannaqvi.uen_sosas.core.MainApp.fc
import edu.aku.hassannaqvi.uen_sosas.core.MainApp.setGPS
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivityInfoBinding
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
    lateinit var adapter: ChildListAdapter
    lateinit var villageList: Collection<VillagesContract>
    lateinit var villageNames: ArrayList<String>
    lateinit var villageMap: HashMap<String, String>
    lateinit var motherList: List<FamilyMembersContract>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bi = DataBindingUtil.setContentView(this, R.layout.activity_info)
        bi.callback = this

        db = DatabaseHelper(this)

        setupViews()
    }

    private fun setupViews() {

        motherList = ArrayList()
        var list: Collection<AreasContract> = db.getAllAreas(MainApp.ucCode)
        var areaNames: ArrayList<String> = ArrayList()
        areaNames.add("-Select Area-")
        var areaMap: HashMap<String, String> = HashMap()
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

        adapter.setItemClicked { item, position, isMother ->

            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@InfoActivity)
            val v = LayoutInflater.from(this).inflate(R.layout.alert_dialog_layout, null)
            dialogBuilder.setView(v)
            var dialog: AlertDialog = dialogBuilder.create()
            dialog.show()
            var noBtn: Button = v.findViewById(R.id.noBtn)
            var continueBtn: Button = v.findViewById(R.id.continueBtn)
            noBtn.setOnClickListener {
                dialog.dismiss()
            }
            continueBtn.setOnClickListener {
                saveDraft()
                if (updateDB()) {
                    finish()
                    startActivity(Intent(this@InfoActivity, if (isMother) SectionBActivity::class.java
                    else SectionCActivity::class.java).putExtra(MainApp.motherInfo, item))
//
                }
                dialog.dismiss()
            }

        }

    }

    private fun saveDraft() {
        fc = FormsContract()
        val pref = getSharedPreferences("tagName", Context.MODE_PRIVATE)
        fc.devicetagID = pref.getString("tagName", null)
        fc.deviceID = MainApp.deviceId
        fc.formDate = DateFormat.format("dd-MM-yyyy HH:mm", Date()).toString()
        fc.user = MainApp.userName
        fc.talukdaCode = MainApp.talukaCode.toString()
        fc.uc = MainApp.ucCode.toString()
        fc.areaCode = areaCode
        fc.village = villageCode
        fc.appversion = MainApp.versionName + "." + MainApp.versionCode
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


}
