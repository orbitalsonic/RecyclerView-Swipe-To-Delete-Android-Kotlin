package com.orbitalsonic.recyclerviewswipetodelete

import android.graphics.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.orbitalsonic.recyclerviewswipetodelete.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var mAdapter: AdapterCountry
    private lateinit var mList:ArrayList<CountryItem>
    private val paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mList = dataProvider()

        createRecyclerView()
    }

    private fun createRecyclerView() {
        mAdapter = AdapterCountry()
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = GridLayoutManager(this, 1)
        mAdapter.submitList(mList)

        mAdapter.setOnItemClickListener(object : OnCountryItemClickListener {
            override fun onItemClick(position: Int) {
                showMessage(mAdapter.currentList[position].countryName)
            }

        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override  fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView: View = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    if (dX > 0) {
                        paint.color = Color.parseColor("#D32F2F")
                        val background = RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, paint)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
                        val iconDest = RectF(
                            itemView.left.toFloat() + width,
                            itemView.top.toFloat() + width,
                            itemView.left.toFloat() + 2 * width,
                            itemView.bottom.toFloat() - width
                        )
                        c.drawBitmap(icon, null, iconDest, paint)
                    } else {
                        paint.color = Color.parseColor("#388E3C")
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, paint)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
                        val iconDest = RectF(
                            itemView.right.toFloat() - 2 * width,
                            itemView.top.toFloat() + width,
                            itemView.right.toFloat() - width,
                            itemView.bottom.toFloat() - width
                        )
                        c.drawBitmap(icon, null, iconDest, paint)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT){
                    mList.remove(mAdapter.currentList[viewHolder.adapterPosition])
                    mAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                    showMessage("Item Deleted!")
                }


            }
        }).attachToRecyclerView(binding.recyclerview)


    }

    private fun dataProvider():ArrayList<CountryItem>{
        mList = ArrayList()
        mList.add(CountryItem("+61","Australia",R.drawable.australia_flag))
        mList.add(CountryItem("+55","Brazil",R.drawable.brazil_flag))
        mList.add(CountryItem("+1","Canada",R.drawable.canada_flag))
        mList.add(CountryItem("+86","China",R.drawable.china_flag))
        mList.add(CountryItem("+49","Germany",R.drawable.germany_flag))
        mList.add(CountryItem("+91","India",R.drawable.india_flag))
        mList.add(CountryItem("+39","Italy",R.drawable.italy_flag))
        mList.add(CountryItem("+52","Mexico",R.drawable.mexico_flag))
        mList.add(CountryItem("+31","Netherlands",R.drawable.netherlands_flag))
        mList.add(CountryItem("+47","Norway",R.drawable.norway_flag))
        mList.add(CountryItem("+92","Pakistan",R.drawable.pakistan_flag))
        mList.add(CountryItem("+34","Spain",R.drawable.spain_flag))
        mList.add(CountryItem("+41","Switzerland",R.drawable.switzerland_flag))
        mList.add(CountryItem("+90","Turkey",R.drawable.turkey_flag))
        mList.add(CountryItem("+44","United Kingdom",R.drawable.united_kingdom_flag))
        mList.add(CountryItem("+1","United States",R.drawable.united_states_flag))


        return mList

    }

    private fun showMessage(message:String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

}