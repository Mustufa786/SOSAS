package edu.aku.hassannaqvi.uen_sosas.ui

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import edu.aku.hassannaqvi.uen_sosas.R
import edu.aku.hassannaqvi.uen_sosas.adapter.ChildListAdapter
import edu.aku.hassannaqvi.uen_sosas.contracts.AreasContract
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract
import edu.aku.hassannaqvi.uen_sosas.contracts.VillagesContract
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper
import edu.aku.hassannaqvi.uen_sosas.core.MainApp
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {


    lateinit var bi: ActivityInfoBinding
    lateinit var db: DatabaseHelper
    var villageCode: String? = null
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

    fun setupViews() {
        var list: Collection<AreasContract> = db.getAllAreas(MainApp.ucCode)
        var areaNames: ArrayList<String> = ArrayList()
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

                val areaCode = areaMap[bi.areaSpinner.selectedItem.toString()]

                villageList = db.getVillages(areaCode)
                villageNames = ArrayList()
                villageMap = HashMap()

                for (item in villageList) {
                    villageNames.add(item.villagename)
                    villageMap[item.villagename] = item.villagecode
                }
                bi.villageSpinner.adapter = ArrayAdapter(this@InfoActivity, android.R.layout.simple_list_item_1, villageNames)

            }
        }

        bi.villageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                villageCode = villageMap[bi.villageSpinner.selectedItem.toString()]

            }

        }

        bi.hhName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                }
            }

        })

        bi.checkHH.setOnClickListener {
            if (!bi.hhName.text.toString().equals("")) {
                motherList = db.getMotherList(bi.hhName.text.toString(), villageCode)
                Toast.makeText(this@InfoActivity, "Mother Found", Toast.LENGTH_SHORT).show()
                adapter = ChildListAdapter(this@InfoActivity, motherList)
                bi.motherList.layoutManager = LinearLayoutManager(this@InfoActivity)
                bi.motherList.adapter = adapter

            }
        }


    }

    fun saveDraft() {


    }

    fun formValidation(): Boolean {


        return true
    }


    fun updateDB(): Boolean {


        return true
    }


}
