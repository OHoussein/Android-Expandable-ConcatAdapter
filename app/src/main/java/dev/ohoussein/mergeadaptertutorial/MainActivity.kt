package dev.ohoussein.mergeadaptertutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ohoussein.mergeadaptertutorial.data.DataProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemsGroupList = DataProvider.getRandomItemsGroupList(5)
        val adapters: List<ItemsExpandableAdapter> = itemsGroupList.map { itemsGroup ->
            ItemsExpandableAdapter(itemsGroup)
        }
        val mergeAdapterConfig = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val mergeAdapter = ConcatAdapter(mergeAdapterConfig, adapters)

        with(rvItems) {
            layoutManager = LinearLayoutManager(context)
            itemAnimator =
                ExpandableItemAnimator()
            adapter = mergeAdapter
        }

    }
}