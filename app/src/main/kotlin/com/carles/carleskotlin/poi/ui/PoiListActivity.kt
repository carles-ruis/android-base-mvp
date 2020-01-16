package com.carles.carleskotlin.poi.ui

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.carles.carleskotlin.R
import com.carles.carleskotlin.common.ui.BaseActivity
import com.carles.carleskotlin.poi.model.Poi
import kotlinx.android.synthetic.main.activity_poi_list.*
import kotlinx.android.synthetic.main.view_toolbar.toolbar
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PoiListActivity : BaseActivity<PoiListPresenter>(), PoiListView {

    override val layoutResourceId = R.layout.activity_poi_list
    private lateinit var adapter: PoiListAdapter
    override val presenter: PoiListPresenter by inject { parametersOf(this) }

    override fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.poilist_title)
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        adapter = PoiListAdapter { poi -> presenter.onPoiClicked(poi) }
        poilist_recyclerview.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        poilist_recyclerview.adapter = adapter
    }

    override fun displayPoiList(poiList: List<Poi>) {
        adapter.items = poiList
    }

    override fun navigateToPoiDetail(id: String) {
        startActivity(PoiDetailActivity.newIntent(this, id))
    }
}